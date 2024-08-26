package com.ghtk.auction.component;

import com.ghtk.auction.entity.Auction;
import com.ghtk.auction.scheduler.AuctionSchedule;

public interface ScheduleCalculator {
    AuctionSchedule getSchedule(Auction auction);
}
