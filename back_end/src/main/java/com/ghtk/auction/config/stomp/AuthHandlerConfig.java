package com.ghtk.auction.config.stomp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompCommand;
import com.ghtk.auction.service.AuctionRealtimeService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AuthHandlerConfig {
  AuctionRealtimeService auctionRealtimeService;

  @Bean
  public DispatchAuthHandler dispatchAuthHandler(
        MatcherHandler[] matchHandlers) {
    return new DispatchAuthHandler(matchHandlers);
  }

  @Bean
  public MatcherHandler subscribeAuctionControlHandler() {
    return new MatcherHandler(
        "/topic/auctions/{auctionId}/control", StompCommand.SUBSCRIBE,
        (headers, payload) -> {
            long userId = (Long) headers.getHeader("userId"); 
            long auctionId = (Long) headers.getHeader("auctionId");
            auctionRealtimeService.checkControlJoin(userId, auctionId);
        });
  } 

  @Bean
  public MatcherHandler subscribeAuctionNotificationHandler() {
    return new MatcherHandler(
        "/topic/auctions/{auctionId}/notification", StompCommand.SUBSCRIBE,
        (headers, payload) -> {
            long userId = (Long) headers.getHeader("userId"); 
            long auctionId = (Long) headers.getHeader("auctionId");
            auctionRealtimeService.checkNotifJoin(userId, auctionId);
        });
  } 

  @Bean
  public MatcherHandler subscribeAuctionBidHandler() {
    return new MatcherHandler(
        "/topic/auctions/{auctionId}/bid", StompCommand.SUBSCRIBE,
        (headers, payload) -> {
            long userId = (Long) headers.getHeader("userId"); 
            long auctionId = (Long) headers.getHeader("auctionId");
            auctionRealtimeService.checkBidJoin(userId, auctionId);
        });
  }

  @Bean
  public MatcherHandler subscribeAuctionCommentHandler() {
    return new MatcherHandler(
        "/topic/auctions/{auctionId}/comment", StompCommand.SUBSCRIBE,
        (headers, payload) -> {
            long userId = (Long) headers.getHeader("userId"); 
            long auctionId = (Long) headers.getHeader("auctionId");
            auctionRealtimeService.checkCommentJoin(userId, auctionId);
        });
  }

  @Bean
  public MatcherHandler bidHandler() {
    return new MatcherHandler(
        "/app/auctions/{auctionId}/bid", StompCommand.SEND,
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
        "/app/auctions/{auctionId}/comment", StompCommand.SEND,
        (headers, payload) -> {
            long userId = (Long) headers.getHeader("userId"); 
            long auctionId = (Long) headers.getHeader("auctionId");
            auctionRealtimeService.checkCommenting(userId, auctionId);
        });
  }

}
