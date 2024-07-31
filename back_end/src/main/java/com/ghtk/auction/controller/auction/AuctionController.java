package com.ghtk.auction.controller.auction;


import com.ghtk.auction.dto.request.auction.AuctionCreationRequest;
import com.ghtk.auction.dto.response.ApiResponse;
import com.ghtk.auction.dto.response.auction.AuctionCreationResponse;
import com.ghtk.auction.dto.response.auction.AuctionResponse;
import com.ghtk.auction.entity.Auction;
import com.ghtk.auction.entity.UserAuction;
import com.ghtk.auction.service.AuctionRealtimeService;
import com.ghtk.auction.service.AuctionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/auctions")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuctionController {
	final AuctionService auctionService;
  final AuctionRealtimeService auctionRealtimeService;
	
	@PostMapping("/")
	public ResponseEntity<ApiResponse<AuctionCreationResponse>> createAuction(
			@AuthenticationPrincipal Jwt jwt,
			@RequestBody @Valid AuctionCreationRequest auctionCreationRequest) {
		return ResponseEntity.ok(ApiResponse.success(auctionService.addAuction(jwt, auctionCreationRequest)));
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/get-my-created")
	public ResponseEntity<ApiResponse<List<AuctionResponse>>> getMyCreatedAuctions(@AuthenticationPrincipal Jwt jwt) {
		return ResponseEntity.ok(ApiResponse.success(auctionService.getMyCreatedAuction(jwt)));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Auction>> getAuctionById(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.success(auctionService.getAuctionById(id)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Auction>> deleteAuction(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.success(auctionService.deleteAuction(jwt,id)));
	}
	
	@GetMapping("/get-my-joined")
	public ResponseEntity<ApiResponse<List<Auction>>> getMyJoined(@AuthenticationPrincipal Jwt jwt) {
		return ResponseEntity.ok(ApiResponse.success(auctionService.getMyJoinedAuction(jwt)));
	}
	
	@PostMapping("/{id}/regis-join")
  @PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponse<UserAuction>> regisJoinAuction(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable Long id
	) {
		return ResponseEntity.ok(ApiResponse.success(auctionService.registerJoinAuction(jwt, id)));
	}

  @GetMapping("/active")
  @PreAuthorize("isAuthenticated()")
  public ApiResponse<List<AuctionResponse>> getRegisActiveAuctions(
    @AuthenticationPrincipal Jwt jwt
  ) {
    return ApiResponse.success(auctionService.getRegisActiveAuctions(jwt));
  }

  @GetMapping("/joinable")
  @PreAuthorize("isAuthenticated()")
  public ApiResponse<List<AuctionResponse>> getJoinableAuctions(
    @AuthenticationPrincipal Jwt jwt
  ) {
    return ApiResponse.success(auctionService.getRegisActiveAuctions(jwt));
  }

  @PostMapping("/{id}/join")
  @PreAuthorize("@auctionComponent.isRegisteredAuction(#auctionId, principal)")
  public ApiResponse<Void> joinAuction(
      @PathVariable Long auctionId,
      @AuthenticationPrincipal Jwt jwt
  ) {
    auctionRealtimeService.joinAuction(jwt, auctionId);
    return ApiResponse.success(null);
  }

  @PostMapping("/{id}/leave")
  public ApiResponse<Void> leaveAuction(
      @PathVariable Long auctionId,
      @AuthenticationPrincipal Jwt jwt
  ) {
    auctionRealtimeService.leaveAuction(jwt, auctionId);
    return ApiResponse.success(null);
  }

  // @GetMapping("/{id}/current-price")
  // public ApiResponse<Long> getCurrentPrice(
  //     @PathVariable Long auctionId,
  //     @AuthenticationPrincipal Jwt jwt
  // ) {
  //   return ApiResponse.success(auctionService.getCurrentPrice(jwt, auctionId));
  // }

  // @GetMapping("/{id}/bids")
  // @PreAuthorize("@auctionComponent.canParticipateAuction(#auctionId, principal)")
  // public ApiResponse<List<BidResponse>> getBids(
  //     @PathVariable Long auctionId,
  //     BidFilter filter,
  //     @AuthenticationPrincipal Jwt jwt
  // ) {
  //   return ApiResponse.success(auctionService.getBids(jwt, auctionId, filter));
  // }

}
