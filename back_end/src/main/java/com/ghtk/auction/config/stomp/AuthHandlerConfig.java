package com.ghtk.auction.config.stomp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompCommand;
import com.ghtk.auction.service.AuctionRealtimeService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AuthHandlerConfig {
  private final AuctionRealtimeService auctionRealtimeService;

  @Bean
  public DispatchAuthHandler dispatchAuthHandler(
        MatcherHandler[] matchHandlers) {
    return new DispatchAuthHandler(matchHandlers);
  }

  @Bean
  public MatcherHandler subscribeAuctionControlHandler() {
    return new MatcherHandler(
        "/topic/auction/{auctionId:d}/control", StompCommand.SUBSCRIBE,
        (headers, payload) -> {
            long userId = (Long) headers.getHeader("userId"); 
            long auctionId = (Long) headers.getHeader("auctionId");
            auctionRealtimeService.checkControlJoin(userId, auctionId);
        });
  } 

  @Bean
  public MatcherHandler subscribeAuctionNotificationHandler() {
    return new MatcherHandler(
        "/topic/auction/{auctionId:d}/notifications", StompCommand.SUBSCRIBE,
        (headers, payload) -> {
            long userId = (Long) headers.getHeader("userId"); 
            long auctionId = (Long) headers.getHeader("auctionId");
            auctionRealtimeService.checkNotifJoin(userId, auctionId);
        });
  } 

  @Bean
  public MatcherHandler subscribeAuctionBidHandler() {
    return new MatcherHandler(
        "/topic/auction/{auctionId:d}/bids", StompCommand.SUBSCRIBE,
        (headers, payload) -> {
            System.out.println("subscribeAuctionBidHandler");
            long userId = (Long) headers.getHeader("userId"); 
            long auctionId = (Long) headers.getHeader("auctionId");
            auctionRealtimeService.checkBidJoin(userId, auctionId);
        });
  }

  @Bean
  public MatcherHandler subscribeAuctionCommentHandler() {
    return new MatcherHandler(
        "/topic/auction/{auctionId:d}/comments", StompCommand.SUBSCRIBE,
        (headers, payload) -> {
            long userId = (Long) headers.getHeader("userId"); 
            long auctionId = (Long) headers.getHeader("auctionId");
            auctionRealtimeService.checkCommentJoin(userId, auctionId);
        });
  }

  @Bean
  public MatcherHandler bidHandler() {
    return new MatcherHandler(
        "/app/auction/{auctionId:d}/bid", StompCommand.SEND,
        (headers, payload) -> {
            System.out.println("bidHandler");
            long userId = (Long) headers.getHeader("userId"); 
            long auctionId = (Long) headers.getHeader("auctionId");
            auctionRealtimeService.checkBidding(userId, auctionId);
        });
  }

  @Bean
  public MatcherHandler commentHandler() {
    return new MatcherHandler(
        "/app/auction/{auctionId:d}/comment", StompCommand.SEND,
        (headers, payload) -> {
            long userId = (Long) headers.getHeader("userId"); 
            long auctionId = (Long) headers.getHeader("auctionId");
            auctionRealtimeService.checkCommenting(userId, auctionId);
        });
  }

  @Bean
  public MatcherHandler notifyHandler() {
    return new MatcherHandler(
        "/app/auction/{auctionId:d}/notify", StompCommand.SEND,
        (headers, payload) -> {
            long userId = (Long) headers.getHeader("userId"); 
            long auctionId = (Long) headers.getHeader("auctionId");
            auctionRealtimeService.checkNotifying(userId, auctionId);
        });
  }
}
