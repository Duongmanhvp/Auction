package com.ghtk.auction.controller.auction;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.ghtk.auction.dto.request.auction.BidRequest;
import com.ghtk.auction.exception.StompExceptionHandler;
import com.ghtk.auction.service.AuctionRealtimeService;
import com.ghtk.auction.service.StompService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuctionStompController {
  private final AuctionRealtimeService auctionRealtimeService;
  private final StompExceptionHandler stompExceptionHandler;
  private final StompService stompService;

  // @SubscribeMapping("/auction/{id}/comments")
  // @PreAuthorize("@auctionComponent.canParticipateAuction(#auctionId, principal)")
  // public void joinAuctionComment(
  //     @DestinationVariable Long auctionId, 
  //     @AuthenticationPrincipal Jwt principal) {
  //   auctionService.joinAuction(principal, auctionId);  
  // }

  @MessageMapping("/echo")
  public Object echo(@Payload long bid, 
      @Headers MessageHeaders headers,
      SimpMessageHeaderAccessor accessor) {
    return accessor.getMessageHeaders();
    // return headers;//.<String, Object> map = new HashMap<>();
  }

  // @SubscribeMapping("/auction/{id}/notifications")
  // public void subscribeNotificationChannel(
  //     @DestinationVariable Long auctionId) {
  //   // auctionRealtimeService.checkNotifJoin(auctionId);
  // }
  
  @SubscribeMapping("/auction/{auctionId}/bids")
  public void subscribeBidChannel(
      @DestinationVariable Long auctionId,
      @Header("userId") Long userId) {
    Long lastPrice = auctionRealtimeService.getCurrentPrice(userId, auctionId);
    stompService.sendAuctionLastPrice(userId, auctionId, lastPrice);
  }
  
  // @SubscribeMapping("/auction/{id}/comments")
  // @PreAuthorize("@auctionComponent.isRegisteredAuction(#auctionId, principal)")
  // public void subscribeCommentChannel(
  //     @DestinationVariable Long auctionId,
  //     Jwt principal) {
  //   // auctionRealtimeService.checkCommentJoin(principal, auctionId);
  // }

  @MessageMapping("/auction/{id}/bid")
  // @SendTo("/topic/auction/{id}/bids")
  public void placeBid(
      @DestinationVariable("id") Long auctionId, 
      @Header("userId") Long userId,
      @Payload @Valid BidRequest bid) {
    auctionRealtimeService.bid(userId, auctionId, bid.getBid());  
  }

  @MessageMapping("/auction/{id}/comment")
  public void sendComment(
      @Header("userId") Long userId,
      @DestinationVariable("id") Long auctionId, 
      @Payload String message) {
    auctionRealtimeService.comment(userId, auctionId, message);
  }

  @MessageMapping("/auction/{id}/notify")
  // @SendTo("/topic/auction/{id}/notifications")
  public void publishNotification(
      @DestinationVariable("id") Long auctionId, 
      @Header("userId") Long userId,
      @Payload String message) {
    auctionRealtimeService.makeNotification(userId, auctionId, message);
  }
 
  @MessageExceptionHandler(Exception.class)
  public void handleException(Exception e) {
    stompExceptionHandler.handle(e);
  }

}
