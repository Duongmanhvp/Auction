package com.ghtk.auction.config.stomp;

import java.util.Optional;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;

import com.ghtk.auction.config.CustomJwtDecoder;
import com.ghtk.auction.exception.StompExceptionHandler;
import com.ghtk.auction.exception.UnauthenticatedStompMessageException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("jwtInterceptor")
@RequiredArgsConstructor
public class JwtInterceptor implements ChannelInterceptor {
    private final CustomJwtDecoder jwtDecoder;
    private final StompExceptionHandler stompExceptionHandler;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        try {
          StompHeaderAccessor headers = StompHeaderAccessor.wrap(message);
          Object payload = message.getPayload();
          var command = headers.getCommand();
          if (command != StompCommand.CONNECT 
           && command != StompCommand.SUBSCRIBE 
           && command != StompCommand.SEND
           && command != StompCommand.MESSAGE
           && command != StompCommand.BEGIN
           && command != StompCommand.COMMIT) {
            return message;
          }
          extractUserId(headers);
          return MessageBuilder.createMessage(payload, headers.getMessageHeaders());
        } catch (UnauthenticatedStompMessageException e) {
          stompExceptionHandler.handle(e);
          return null;
        }
    }

    private void extractUserId(StompHeaderAccessor headers) {
        String token = headers.getFirstNativeHeader("Authorization");
        System.out.println(headers);
        System.out.println(token);
        if (token == null) {
            throw new UnauthenticatedStompMessageException(headers.getSessionId(), Optional.of(CloseStatus.NOT_ACCEPTABLE));
        }
        try {
            if (!token.startsWith("Bearer ")) {
                throw new UnauthenticatedStompMessageException(headers.getSessionId(), Optional.of(CloseStatus.NOT_ACCEPTABLE));
            }
            System.out.println("OK");
            headers.setHeader("userId", jwtDecoder.decode(token.substring(7)).getClaims().get("id"));
        } catch (Exception e) {
            throw new UnauthenticatedStompMessageException(headers.getSessionId(), Optional.of(CloseStatus.NOT_ACCEPTABLE), e);
        }
    }
  
}
