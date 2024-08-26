package com.ghtk.auction.scheduler.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.ghtk.auction.dto.request.auction.AuctionUpdateStatusRequest;
import com.ghtk.auction.enums.AuctionStatus;
import com.ghtk.auction.service.AuctionRealtimeService;
import com.ghtk.auction.service.AuctionService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EndAuction implements Job {
  private final AuctionService auctionService;
  private final AuctionRealtimeService auctionRealtimeService;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    System.out.println("EndAuction");
    JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
    Long auctionId = jobDataMap.getLong("auctionId");
    auctionService.updateStatus(new AuctionUpdateStatusRequest(auctionId, AuctionStatus.FINISHED));
    auctionRealtimeService.endAuction(auctionId);
  }
}
