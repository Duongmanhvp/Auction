package com.ghtk.auction.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {
    private final String stompEndpoint; 

    public StompConfig(@Value("${websocket.endpoint}") String stompEndpoint) {
      this.stompEndpoint = stompEndpoint;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
      registry.addEndpoint(stompEndpoint)
              .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
      registry.enableSimpleBroker("/topic");
      registry.setApplicationDestinationPrefixes("/app");
    }

    // @Override
    // public void configureClientInboundChannel(ChannelRegistration registration) {
    //   registration.interceptors(new Interceptor());
    // }

    
    @Bean
    public AuthorizationManager<Message<?>> messageAuthorizationManager() {
      var authBuilder = new MessageMatcherDelegatingAuthorizationManager.Builder();
      authBuilder.nullDestMatcher().authenticated() 
                  .simpSubscribeDestMatchers("/topic/errors").permitAll() 
                  .simpSubscribeDestMatchers("/topic/auction/**").authenticated()
                  .simpDestMatchers("/app/auction/**").hasRole("USER")
                  //.simpTypeMatchers(MessageType.MESSAGE).denyAll()
                  .anyMessage().denyAll(); 

      return authBuilder.build();
    }
}
