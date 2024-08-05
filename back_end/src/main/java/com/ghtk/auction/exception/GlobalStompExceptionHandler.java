package com.ghtk.auction.exception;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.socket.WebSocketSession;

import com.ghtk.auction.component.StompSessionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@Controller
@RequiredArgsConstructor
public class GlobalStompExceptionHandler {
  private final StompSessionManager sessionManager;

  @MessageExceptionHandler(UnauthenticatedStompMessageException.class)
  public void handleUnauthenticated(UnauthenticatedStompMessageException e) {
    log.error("Unauthenticated message:\n");
    try {
      WebSocketSession session = sessionManager.getSession(e.getSessionId());
      if (session == null) {
        return;
      }
      if (e.getCloseStatus() != null) {
        session.close(e.getCloseStatus());
      } else {
        session.close();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
