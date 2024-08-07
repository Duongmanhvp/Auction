package com.ghtk.auction.service.impl;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ghtk.auction.dto.stomp.AuctionLastPrice;
import com.ghtk.auction.dto.stomp.BidMessage;
import com.ghtk.auction.dto.stomp.CommentMessage;
import com.ghtk.auction.dto.stomp.ControlMessage;
import com.ghtk.auction.dto.stomp.NotifyMessage;
import com.ghtk.auction.service.StompService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StompServiceImpl implements StompService {
  private final SimpMessagingTemplate messagingTemplate;

  @Override
  public void notifyJoinableAuction(long userId, long auctionId) {
    messagingTemplate.convertAndSend(
        "/user/" + userId + "/queue/control", 
        new ControlMessage<>("joinable", auctionId));
  }

  @Override
  public void broadcastNotification(long auctionId, NotifyMessage message) {
    messagingTemplate.convertAndSend("/topic/auction/" + auctionId + "/notifications", message);
  }

  @Override
  public void broadcastStartAuction(long auctionId) {
    messagingTemplate.convertAndSend(
        "/topic/auction/" + auctionId + "/control",
        new ControlMessage<>("start", (Void) null));
  }

  @Override
  public void broadcastEndAuction(long auctionId) {
    messagingTemplate.convertAndSend(
        "/topic/auction/" + auctionId + "/control",
        new ControlMessage<>("end", (Void) null));
  }

  @Override
  public void broadcastBid(long auctionId, BidMessage bid) {
    messagingTemplate.convertAndSend("/topic/auction/" + auctionId + "/bids", bid);
  }

  @Override
  public void broadcastComment(long auctionId, CommentMessage comment) {
    messagingTemplate.convertAndSend("/topic/auction/" + auctionId + "/comments", comment);
  }

  @Override
  public void sendAuctionLastPrice(Long userId, long auctionId, long lastPrice) {
    messagingTemplate.convertAndSend(
        "/user/" + userId + "/queue/control",
        new ControlMessage<>("auction_last_price", new AuctionLastPrice(auctionId, lastPrice)));
  }

  @Override
  public void broadcastError(String message) {
    messagingTemplate.convertAndSend("/topic/error", message);
  }
}
