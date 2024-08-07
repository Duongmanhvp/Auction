package com.ghtk.auction.config.stomp;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
// import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
// @RequiredArgsConstructor
public class StompConfig implements WebSocketMessageBrokerConfigurer {
    // @Value("${websocket.endpoint}") 
    private final String stompEndpoint; 
    // @Value("${allowed-origins}")
    private final String[] allowedOrigins;
    // private final CustomHandshakeHandler customHandshakeHandler;
    // private final HandshakeInterceptor handshakeInterceptor;
    private final ChannelInterceptor jwtInterceptor;
    private final ChannelInterceptor authInterceptor;
    private final WebSocketHandlerDecoratorFactory sessionStoringWebDecorFactory;

    public StompConfig(@Value("${websocket.endpoint}") String stompEndpoint, 
                       @Value("${allowed-origins}") String[] allowedOrigins,
                      //  CustomHandshakeHandler customHandshakeHandler,
                      //  HandshakeInterceptor handshakeInterceptor,
                       @Qualifier("jwtInterceptor") ChannelInterceptor jwtInterceptor,
                       @Qualifier("authInterceptor") ChannelInterceptor authInterceptor,
                       WebSocketHandlerDecoratorFactory sessionStoringWebDecorFactory) {
      this.stompEndpoint = stompEndpoint;
      this.allowedOrigins = allowedOrigins;
      // this.customHandshakeHandler = customHandshakeHandler;
      // this.handshakeInterceptor = handshakeInterceptor;
      this.jwtInterceptor = jwtInterceptor;
      this.authInterceptor = authInterceptor;
      this.sessionStoringWebDecorFactory = sessionStoringWebDecorFactory;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
      registry.addEndpoint(stompEndpoint)
              .setAllowedOrigins(allowedOrigins)
              // .setHandshakeHandler(customHandshakeHandler)
              // .addInterceptors(handshakeInterceptor)
              ;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
      registry.enableSimpleBroker("/topic", "/user")
          .setHeartbeatValue(new long[] {30000, 0}).setTaskScheduler(taskScheduler());
      registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
      return true;
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
      registry.addDecoratorFactory(sessionStoringWebDecorFactory);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
      registration.interceptors(jwtInterceptor);
      registration.interceptors(authInterceptor);
    }

    @Bean
    public AuthorizationManager<Message<?>> messageAuthorizationManager() {
      var authBuilder = new MessageMatcherDelegatingAuthorizationManager.Builder();
      authBuilder.nullDestMatcher().authenticated() 
                  .simpSubscribeDestMatchers("/topic/errors").permitAll() 
                  .simpSubscribeDestMatchers("/user/*/queue/control").authenticated() 
                  .simpSubscribeDestMatchers("/user/*/queue/notifications").authenticated() 
                  .simpSubscribeDestMatchers("/topic/auction/*/control").authenticated()
                  .simpSubscribeDestMatchers("/topic/auction/*/notifications").authenticated()
                  .simpSubscribeDestMatchers("/topic/auction/*/bids").authenticated()
                  .simpSubscribeDestMatchers("/topic/auction/*/comments").authenticated()
                  .simpDestMatchers("/app/auction/**").hasRole("USER")
                  //.simpTypeMatchers(MessageType.MESSAGE).denyAll()
                  .anyMessage().denyAll(); 

      // authBuilder.anyMessage().permitAll();

      return authBuilder.build();
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("wss-heartbeat-thread-");
        scheduler.initialize();
        return scheduler;
    }
}
