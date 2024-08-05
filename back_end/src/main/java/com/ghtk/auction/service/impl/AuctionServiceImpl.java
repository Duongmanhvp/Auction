package com.ghtk.auction.service.impl;

import com.ghtk.auction.dto.request.auction.AuctionCreationRequest;
import com.ghtk.auction.dto.request.auction.AuctionUpdateStatusRequest;
import com.ghtk.auction.dto.response.auction.AuctionCreationResponse;
import com.ghtk.auction.dto.response.auction.AuctionResponse;
import com.ghtk.auction.entity.*;
import com.ghtk.auction.enums.AuctionStatus;
import com.ghtk.auction.exception.NotFoundException;
import com.ghtk.auction.mapper.AuctionMapper;
import com.ghtk.auction.repository.*;
import com.ghtk.auction.service.AuctionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.coyote.BadRequestException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {
	
	final AuctionRepository auctionRepository;
	final ProductRepository productRepository;
	final UserAuctionRepository userAuctionRepository;
	final AuctionMapper auctionMapper;
	final UserRepository userRepository;
	final TimeHistoryRepository timeHistoryRepository;
	
	
	@PreAuthorize("@productComponent.isProductOwner(#request.productId, principal)")
	@Override
	public AuctionCreationResponse addAuction(Jwt principal, AuctionCreationRequest request) {
	
		Product product = productRepository.findById(request.getProductId())
				.orElseThrow(() ->
						new NotFoundException("San pham khong ton tai, khong the tao phien dau gia!"));
		Auction auction = new Auction();
		try {
			if (product.getBuyerId() == null ) {
				 auction = Auction.builder()
						.product(product)
						.title(request.getTitle())
						.description(request.getDescription())
						.startBid(request.getStartBid())
						.pricePerStep(request.getPricePerStep())
						.createdAt(LocalDateTime.now())
						.status(AuctionStatus.PENDING)
						.build();
				auctionRepository.save(auction);
				return auctionMapper.toAuctionCreationResponse(auction);
			}
			// TODO: throw ra neu san pham da co nguoi mua thi khong duoc tao dau gia
			throw new BadRequestException("!!!!");
		} catch (Exception e) {
			// TODO:
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public List<AuctionResponse> getMyCreatedAuction(Jwt principal) {
		
		Long userId = (Long)principal.getClaims().get("id");
		
		List<Object[]> auctions = auctionRepository.findMyByProductOwnerId(userId);
	
		return auctions.stream().map(
				auction -> new AuctionResponse(
						(Long) auction[0],
						(Long)auction[1],
						(String) auction[2],
						(String) auction[3],
						(Timestamp) auction[4],
						(LocalDateTime) auction[5],
						(LocalDateTime) auction[6],
						(LocalDateTime) auction[7],
						(LocalDateTime) auction[8],
						(Long) auction[9],
						(Long) auction[10],
						(Long) auction[11],
						(AuctionStatus.valueOf((String) auction[12]))
				)).collect(Collectors.toList());
		
	}
	
	@PreAuthorize("@auctionComponent.isAuctionOwner(#auctionId, principal)")
	@Override
	public Auction deleteAuction(Jwt principal, Long auctionId) {
		
		Auction auction = auctionRepository.findById(auctionId).orElseThrow(
				() -> new NotFoundException("Khong tim thay phien dau gia hop le")
		);
		auctionRepository.delete(auction);
		return auction;
	}
	
	// TODO : meo hieu doan nay the nao nua ??
	@Override
	public List<Auction> getMyJoinedAuction(Jwt principal) {
		
		Long userId = (Long)principal.getClaims().get("id");

		User user = userRepository.findById(userId).orElseThrow(
				() -> new NotFoundException("Khong thay nguoi dung")
		);
		
		List<UserAuction> results = userAuctionRepository.findAllByUser(user);
		
		if (results.isEmpty()) {
			throw new NotFoundException("Nguoi dung chua dang ky tham gia bat ky phien nao!");
		}
		List<UserAuction> myJoined = new ArrayList<>();
		
		results.forEach(
				result -> {
					if(timeHistoryRepository.existsByUserAuctionId(result))
					{
						myJoined.add(result);
					}
				}
		);
		if (myJoined.isEmpty()) {
			throw new NotFoundException("Nguoi dung chua truc tiep tham gia dau gia!");
		}
		return myJoined.stream().map(UserAuction::getAuction).toList();
	}
	
	@Override
	public Auction getAuctionById(Long auctionId) {
		
		return  auctionRepository.findById(auctionId).orElseThrow(
				() -> new NotFoundException("Khong tim thay phien dau gia hop le")
		);
		
	}
	
	@PreAuthorize("@auctionComponent.isAuctionOpening(#auctionId)")
	@Override
	public UserAuction registerJoinAuction(Jwt principal, Long auctionId) {
		
		Long userId = (Long)principal.getClaims().get("id");
		User user = userRepository.findById(userId).orElseThrow(
				() ->  new NotFoundException("Khong tim thay user hop le")
		);
		Auction auction = auctionRepository.findById(auctionId).orElseThrow(
				() -> new NotFoundException("Khong tim thay product hop le")
		);
		UserAuction userAuction = new UserAuction();
		
		userAuction.setUser(user);
		userAuction.setAuction(auction);
		
		userAuctionRepository.save(userAuction);
		return userAuction;
	}
	
	@PreAuthorize("@auctionComponent.canJoinAuction(#auctionId,principal)")
	@Override
	public void joinAuction(Jwt principal, Long auctionId) {
		
		TimeHistory timeHistory = new TimeHistory();
		
	}
	
	@Override
	public void bid(Long auctionId, Long bid) {
	
	}
	
	
	// ADMIN
	@Override
	public List<Auction> getAllList() {
		return List.of();
	}
	
	@Override
	public Auction confirmAuction(Long auctionId) {
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime endRegistration = now.plusDays(3);
		
		LocalDateTime startTime;
		if (endRegistration.getHour() >= 13 && endRegistration.getHour() < 24) {
			startTime = endRegistration.plusDays(1).withHour(9).withMinute(0).withSecond(0);
		} else {
			startTime = endRegistration.withHour(15).withMinute(0).withSecond(0);
		}
		
		LocalDateTime endTime = startTime.plusMinutes(60);
		
		return Auction.builder()
				.confirmDate(now)
				.endRegistration(endRegistration)
				.startTime(startTime)
				.endTime(endTime)
				.status(AuctionStatus.OPENING)
				.build();
	}
	
	@Override
	public void updateStatus(AuctionUpdateStatusRequest request) {
	
		Auction auction = auctionRepository.findById(request.getAuctionId()).orElseThrow(
				() -> new NotFoundException("Khong tim thay phien dau gia nao trung voi Id")
		);
		
		Auction.builder()
				.status(request.getAuctionStatus())
				.build();
		
	}
}
