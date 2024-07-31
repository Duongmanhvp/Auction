package com.ghtk.auction.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ghtk.auction.dto.redis.AuctionRedisResponse;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.ghtk.auction.dto.request.auction.BidFilter;
import com.ghtk.auction.dto.response.auction.AuctionResponse;
import com.ghtk.auction.dto.response.auction.BidResponse;
import com.ghtk.auction.entity.Auction;
import com.ghtk.auction.entity.TimeHistory;
import com.ghtk.auction.entity.User;
import com.ghtk.auction.entity.UserAuction;
import com.ghtk.auction.enums.AuctionStatus;
import com.ghtk.auction.exception.NotFoundException;
import com.ghtk.auction.repository.AuctionRepository;
import com.ghtk.auction.repository.TimeHistoryRepository;
import com.ghtk.auction.repository.UserAuctionRepository;
import com.ghtk.auction.repository.UserRepository;
import com.ghtk.auction.service.AuctionRealtimeService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuctionRealtimeServiceImpl implements AuctionRealtimeService {
  final UserRepository userRepository;
  final UserAuctionRepository userAuctionRepository;
  final TimeHistoryRepository timeHistoryRepository;
  final AuctionRepository auctionRepository;

  final RedisTemplate<String, String> redisTemplate;
  final RedisTemplate<String, Object> objectRedisTemplate;

  
  @Override
  public List<AuctionResponse> getJoinableNotis(Jwt principal) {
    // TODO move this
    //throw new UnsupportedOperationException("Unimplemented method 'getRegisActiveAuctions'");
    Long userId = (Long)principal.getClaims().get("id");
    User user = userRepository.findById(userId).orElseThrow(
        () -> new NotFoundException("Khong tim thay nguoi dung")
    );
    List<UserAuction> userAuctions 
        = userAuctionRepository.findAllByUserAndAuctionStatus(user, AuctionStatus.IN_PROGRESS);
    
    return null;
  }

	@Override
	public void joinAuction(Jwt principal, Long auctionId) {
    // TODO: move this
    Long userId = (Long)principal.getClaims().get("id");
    UserAuction ua = userAuctionRepository.findByUserIdAndAuctionId(userId, auctionId);
    if (!getUserLastJoinAuction(userId, auctionId).isEmpty()) {
      // TODO: nem loi hop le
      throw new RuntimeException("da tham gia dau gia");
    }
    LocalDateTime joinTime = LocalDateTime.now();
    setUserLastJoinAuction(userId, auctionId, joinTime);
	}

  @Override
  public void leaveAuction(Jwt principal, Long auctionId) {
    // TODO: move this
    Long userId = (Long)principal.getClaims().get("id");
    UserAuction ua = userAuctionRepository.findByUserIdAndAuctionId(userId, auctionId);
    if (!getUserLastJoinAuction(userId, auctionId).isEmpty()) {
      // TODO: nem loi hop le
      throw new RuntimeException("da tham gia dau gia");
    }
    LocalDateTime joinTime = getUserLastJoinAuction(userId, auctionId).orElseThrow(
        () -> new RuntimeException("Chua tham gia dau gia")
    );
    LocalDateTime leaveTime = LocalDateTime.now();
    TimeHistory entry = TimeHistory.builder()
        .userAuction(ua)
        .joinTime(joinTime)
        .outTime(leaveTime)
        .build();
    timeHistoryRepository.save(entry);
    deleteUserLastJoinAuction(userId, auctionId);
  }

  @Override
  public Long getCurrentPrice(Jwt principal, Long auctionId) {
    // TODO: move this
    Auction auction = auctionRepository.findById(auctionId).orElseThrow(
        () -> new NotFoundException("Khong tim thay phien dau gia nao trung voi Id")
    );
    if (auction.getStatus() != AuctionStatus.IN_PROGRESS) {
        // TODO: nem loi hop le
        throw new RuntimeException("Phien dau gia chua bat dau");
    }
    Long result = getAuctionLastPrice(auctionId).orElse(0L);
    return result;
  }
	
	@Override
	public BidResponse bid(Jwt principal, Long auctionId, Long bid) {
    // TODO: move this
    Long userId = (Long)principal.getClaims().get("id");
    
    return null;
	}

  // @Override
  // public List<BidResponse> getBids(Jwt principal, Long auctionId, BidFilter filter) {
  //   // TODO: move this
  //   return null;
  // }

  @Override
  public void openAuctionRoom(Long auctionId) {
    setAuctionPrepareActive(auctionId);
  }

  @Override
  public void startAuction(Long auctionId) {
    //  thêm các dữ liệu auction vào redis
   setAuctionActive(auctionId);
  }

  @Override
  public void endAuction(Long auctionId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'endAuction'");
  }
  
  private void setAuctionPrepareActive(Long auctionId) {
    String key = String.format("auction:%d:prepare_active", auctionId);
    redisTemplate.opsForValue().set(key,"prepare",Duration.ofMinutes(10));
    
  }
  private boolean getAuctionPrepareActive(Long auctionId) {
    String key = String.format("auction:%d:prepare_active", auctionId);
    return Boolean.TRUE.equals(redisTemplate.hasKey(key));
  }
  
  private void setAuctionActive(Long auctionId) {
    Auction auction = auctionRepository.findById(auctionId).orElseThrow(
          () -> new NotFoundException("Khong tim thay auction hop le voi Id")
    );
    AuctionRedisResponse value = AuctionRedisResponse.builder()
          .startBid(auction.getStartBid())
          .pricePerStep(auction.getPricePerStep())
          .build();
    String key = String.format("auction:%d:active", auctionId);
    objectRedisTemplate.opsForValue().set(key,value);
    
  }

  private Optional<AuctionResponse> getAuctionActive(Long auctionId) {
    String key = String.format("auction:%d:active", auctionId);
    return Optional.ofNullable((AuctionResponse) objectRedisTemplate.opsForValue().get(key));
  }
  
  private Optional<LocalDateTime> getUserLastJoinAuction(Long userId, Long auctionId) {
    String key = String.format("user:%d:auction:%d:last_join", userId, auctionId);
    return Optional.ofNullable(redisTemplate.opsForValue().get(key))
        .map(LocalDateTime::parse);
  }

  private void setUserLastJoinAuction(Long userId, Long auctionId, LocalDateTime time) {
    String key = String.format("user:%d:auction:%d:last_join", userId, auctionId);
    redisTemplate.opsForValue().set(key, time.toString());
  }

  private void deleteUserLastJoinAuction(Long userId, Long auctionId) {
    String key = String.format("user:%d:auction:%d:last_join", userId, auctionId);
    redisTemplate.delete(key);
  }

  private Optional<Long> getAuctionLastPrice(long auctionId) {
    String key = String.format("auction:%d:last_bid", auctionId);
    return Optional.ofNullable(redisTemplate.opsForValue().get(key))
        .map(Long::parseLong);
  }

  private void setAuctionLastPrice(long auctionId, Long bid) {
    String key = String.format("auction:%d:last_bid", auctionId);
    redisTemplate.opsForValue().set(key, bid.toString());
  }

  private void deleteAuctionLastPrice(Long auctionId) {
    String key = String.format("auction:%d:last_bid", auctionId);
    redisTemplate.delete(key);
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private static class AuctionBid {
    long auctionId;
    long userId;
    long bid;
  }

  
}
