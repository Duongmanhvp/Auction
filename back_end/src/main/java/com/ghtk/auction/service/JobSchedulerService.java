package com.ghtk.auction.service;

import com.ghtk.auction.entity.Auction;
import com.ghtk.auction.scheduler.AuctionSchedule;

import org.quartz.SchedulerException;

public interface JobSchedulerService {
	
//	public void updateClosedAuctionStatus(AuctionUpdateStatusRequest request) throws SchedulerException;
//
//	public void updateInProgressAuctionStatus(AuctionUpdateStatusRequest request);
//
//	public void updateFinishedAuctionStatus(AuctionUpdateStatusRequest request);
	
//	public void updateAuctionStatus(AuctionUpdateStatusRequest request) throws SchedulerException;
	
	public void scheduleAuction(Auction auction, AuctionSchedule schedule) throws SchedulerException;
}
