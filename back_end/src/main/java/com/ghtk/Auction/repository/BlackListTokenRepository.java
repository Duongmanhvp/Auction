package com.ghtk.Auction.repository;

import com.ghtk.Auction.entity.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListTokenRepository extends JpaRepository<BlackListToken,Long> {
	boolean existsByToken(String token);
}
