package com.ghtk.auction.component.impl;

import com.ghtk.auction.component.ScheduleCalculator;
import com.ghtk.auction.entity.Auction;
import com.ghtk.auction.scheduler.AuctionSchedule;

import java.time.LocalDateTime;

public class DefaultScheduleCalculator implements ScheduleCalculator {
    @Override
    public AuctionSchedule getSchedule(Auction auction) {
      LocalDateTime confirmDate = auction.getConfirmDate();
      LocalDateTime endRegistration = confirmDate.plusDays(3);

      LocalDateTime startTime;
      if (endRegistration.getHour() >= 13) {
        startTime = endRegistration.plusDays(1).withHour(9).withMinute(0).withSecond(0);
      } else {
        startTime = endRegistration.withHour(15).withMinute(0).withSecond(0);
      }

      LocalDateTime preopenTime = startTime.minusMinutes(10);

      LocalDateTime endTime = startTime.plusMinutes(60);
		
      return AuctionSchedule.builder()
        .endRegistration(endRegistration)
        .preopenTime(preopenTime)
        .startTime(startTime)
        .endTime(endTime)
        .build();
    }
}
