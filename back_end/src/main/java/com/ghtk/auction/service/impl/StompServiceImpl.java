package com.ghtk.auction.service.impl;

import org.springframework.stereotype.Service;

import com.ghtk.auction.dto.response.auction.BidResponse;
import com.ghtk.auction.service.StompService;

@Service
public class StompServiceImpl implements StompService {
  @Override
  public void notifyJoinableAuction(Long auctionId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'notifyActiveAuction'");
  }

  @Override
  public void broadcastAuctionNotification(Long auctionId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'broadcastAuctionNotification'");
  }

  @Override
  public void broadcastStartAuction(Long auctionId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'broadcastStartAuction'");
  }

  @Override
  public void broadcastEndAuction(Long auctionId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'broadcastEndAuction'");
  }

  @Override
  public void broadcastBid(BidResponse bidResponse) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'broadcastBid'");
  }

  @Override
  public void broadcastComment(BidResponse bidResponse) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'broadcastComment'");
  }
}
