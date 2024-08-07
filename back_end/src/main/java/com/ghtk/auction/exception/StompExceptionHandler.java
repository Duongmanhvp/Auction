package com.ghtk.auction.exception;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.socket.WebSocketSession;

import com.ghtk.auction.component.StompSessionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class StompExceptionHandler {
  private final ExceptionHandler<?>[] exceptionHandlers;

  public void handle(Throwable e) {
    for (ExceptionHandler<?> handler : exceptionHandlers) {
      if (handler.handle(e)) {
        return;
      }
    }
    log.error("Unhandled throwable:\n");
    e.printStackTrace();
  }

  @Bean
  public static ExceptionHandler<?> unauthenticatedHandler(
      StompSessionManager sessionManager) {
    return new ExceptionHandler<>(
        UnauthenticatedStompMessageException.class,
        e -> {
            log.error("Unauthenticated stomp message:\n", e.getMessage());
            /// to trigger websocket close
            // throw new RuntimeException("Unauthorized stomp message: ", e);
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
        });
  }

  @Bean
  public static ExceptionHandler<?> unauthorizedHandler() {
    return new ExceptionHandler<>(
        UnauthorizedStompMessageException.class,
        e -> {
            log.error("Unauthorized stomp message:\n", e.getMessage());
        });
  }

  @Bean
  public static ExceptionHandler<?> forbiddenHandler() {
    return new ExceptionHandler<>(
        ForbiddenException.class,
        e -> {
            log.error("Forbidden :\n", e.getMessage());
        });
  }
  
  @Bean
  public static ExceptionHandler<?> exceptionHandler() {
    return new ExceptionHandler<>(
        Exception.class,
        e -> {
            log.error("Unhandled exception:\n", e.getMessage());
            e.printStackTrace();
        });
  }


  @RequiredArgsConstructor
  public static class ExceptionHandler<T extends Throwable> {
    private final Class<T> exceptionClass;
    private final Consumer<? super T> handler;

    public boolean handle(Throwable e) {
      if (exceptionClass.isInstance(e)) {
        handler.accept(exceptionClass.cast(e));
        return true;
      }
      return false;
    }
  }
}
