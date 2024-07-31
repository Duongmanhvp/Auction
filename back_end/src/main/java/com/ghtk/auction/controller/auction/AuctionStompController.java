package com.ghtk.auction.controller.auction;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;

import com.ghtk.auction.dto.request.auction.BidRequest;
import com.ghtk.auction.dto.response.auction.BidResponse;
import com.ghtk.auction.service.AuctionRealtimeService;
import com.ghtk.auction.service.AuctionService;
import com.ghtk.auction.service.StompService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuctionStompController {
  private final AuctionService auctionService;
  private final AuctionRealtimeService auctionRealtimeService;
  private final StompService stompService;

  // @SubscribeMapping("/auctions/{id}/comment")
  // @PreAuthorize("@auctionComponent.canParticipateAuction(#auctionId, principal)")
  // public void joinAuctionComment(
  //     @DestinationVariable Long auctionId, 
  //     @AuthenticationPrincipal Jwt principal) {
  //   auctionService.joinAuction(principal, auctionId);  
  // }

  @SubscribeMapping("/auctions/{id}/bid")
  @PreAuthorize("@auctionComponent.canParticipateAuction(#auctionId, principal)")
  public void placeBid(
      @DestinationVariable Long auctionId,
      @AuthenticationPrincipal Jwt principal) {
    // TODO:
     auctionRealtimeService.getCurrentPrice(principal, auctionId);
    stompService.broadcastBid(null);
  }

  @MessageMapping("/auctions/{id}/bid")
  @PreAuthorize("@auctionComponent.canParticipateAuction(#auctionId, principal)")
  public void placeBid(
      @DestinationVariable Long auctionId, 
      @Payload BidRequest bid, 
      @AuthenticationPrincipal Jwt principal) {
    BidResponse bidinfo = auctionRealtimeService.bid(principal, auctionId, bid.getBid());  
    stompService.broadcastBid(bidinfo);
  }
}
