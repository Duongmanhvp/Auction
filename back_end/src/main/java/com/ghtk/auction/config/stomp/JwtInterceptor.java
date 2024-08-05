package com.ghtk.auction.config.stomp;

import java.util.Optional;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;

import com.ghtk.auction.config.CustomJwtDecoder;
import com.ghtk.auction.exception.GlobalStompExceptionHandler;
import com.ghtk.auction.exception.UnauthenticatedStompMessageException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("jwtInterceptor")
@RequiredArgsConstructor
public class JwtInterceptor implements ChannelInterceptor {
    private final CustomJwtDecoder jwtDecoder;
    private final GlobalStompExceptionHandler globalStompExceptionHandler;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        try {
          StompHeaderAccessor headers = StompHeaderAccessor.wrap(message);
          Object payload = message.getPayload();
          extractUserId(headers);
          return MessageBuilder.createMessage(payload, headers.getMessageHeaders());
        } catch (UnauthenticatedStompMessageException e) {
          handleException(e);
          return null;
        }
    }

    private void handleException(UnauthenticatedStompMessageException ex) {
        globalStompExceptionHandler.handleUnauthenticated(ex);
    }

    private void extractUserId(StompHeaderAccessor headers) {
        String token = headers.getFirstNativeHeader("Authorization");
        System.out.println("token: " + token);
        if (token == null) {
            throw new UnauthenticatedStompMessageException(headers.getSessionId(), Optional.of(CloseStatus.NOT_ACCEPTABLE));
        }
        try {
            if (!token.startsWith("Bearer ")) {
                throw new UnauthenticatedStompMessageException(headers.getSessionId(), Optional.of(CloseStatus.NOT_ACCEPTABLE));
            }
            headers.setHeader("userId", jwtDecoder.decode(token.substring(7)).getClaims().get("id"));
        } catch (Exception e) {
            throw new UnauthenticatedStompMessageException(headers.getSessionId(), Optional.of(CloseStatus.NOT_ACCEPTABLE), e);
        }
    }
  
}
