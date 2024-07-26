package com.ghtk.auction.repository;

import com.ghtk.auction.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserAuctionRepository extends JpaRepository<UserAuction, Long> {
	
	
	boolean existsByUserAndAuction(User user, Auction auction);
	List<UserAuction> findAllByUser(User user);
}
