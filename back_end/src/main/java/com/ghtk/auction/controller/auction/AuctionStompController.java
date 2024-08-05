package com.ghtk.auction.controller.auction;

import java.time.LocalDateTime;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.ghtk.auction.dto.request.auction.BidRequest;
import com.ghtk.auction.dto.stomp.BidMessage;
import com.ghtk.auction.dto.stomp.NotificationMessage;
import com.ghtk.auction.service.AuctionRealtimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuctionStompController {
  private final AuctionRealtimeService auctionRealtimeService;

  // @SubscribeMapping("/auctions/{id}/comment")
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

  // @SubscribeMapping("/auctions/{id}/notification")
  // public void subscribeNotificationChannel(
  //     @DestinationVariable Long auctionId) {
  //   // auctionRealtimeService.checkNotifJoin(auctionId);
  // }
  
  // @SubscribeMapping("/auctions/{id}/bid")
  // @SendTo("/topic/auctions/{id}/bid")
  // public void subscribeBidChannel(@DestinationVariable Long auctionId) {
  //   // auctionRealtimeService.checkBidJoin(principal, auctionId);
  //   // long lastPrice = auctionRealtimeService.getCurrentPrice(principal, auctionId);
  //   // stompService.sendAuctionLastPrice(principal, auctionId, lastPrice);
  // }
  
  // @SubscribeMapping("/auctions/{id}/comment")
  // @PreAuthorize("@auctionComponent.isRegisteredAuction(#auctionId, principal)")
  // public void subscribeCommentChannel(
  //     @DestinationVariable Long auctionId,
  //     Jwt principal) {
  //   // auctionRealtimeService.checkCommentJoin(principal, auctionId);
  // }

  @MessageMapping("/auctions/{id}/notify")
  @SendTo("/topic/auctions/{id}/notification")
  public NotificationMessage publishNotification(
      @DestinationVariable("id") Long auctionId, 
      @Header("userId") Long userId,
      @Payload String message) {
    auctionRealtimeService.checkNotifying(userId, auctionId);  
    return new NotificationMessage(message, LocalDateTime.now());
  }

  @MessageMapping("/auctions/{id}/bid")
  @SendTo("/topic/auctions/{id}/bid")
  public BidMessage placeBid(
      @DestinationVariable("id") Long auctionId, 
      @Header("userId") Long userId,
      @Payload @Valid BidRequest bid) {
    return auctionRealtimeService.bid(userId, auctionId, bid.getBid());  
  }
  
}
