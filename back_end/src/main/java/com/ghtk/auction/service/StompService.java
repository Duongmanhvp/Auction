package com.ghtk.auction.service;

import com.ghtk.auction.dto.response.auction.BidResponse;

public interface StompService {
  public void notifyOpenAuction(Long auctionId);

  public void broadcastAuctionNotification(Long auctionId);

  public void broadcastStartAuction(Long auctionId);

  public void broadcastEndAuction(Long auctionId);

  public void broadcastBid(BidResponse bidResponse);

  public void broadcastComment(BidResponse bidResponse);
}
