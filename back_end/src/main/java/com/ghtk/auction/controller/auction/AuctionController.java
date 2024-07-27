package com.ghtk.auction.controller.auction;


import com.ghtk.auction.dto.request.auction.AuctionCreationRequest;
import com.ghtk.auction.dto.request.product.ProductCreationRequest;
import com.ghtk.auction.dto.request.product.ProductFilterRequest;
import com.ghtk.auction.dto.response.ApiResponse;
import com.ghtk.auction.dto.response.auction.AuctionCreationResponse;
import com.ghtk.auction.dto.response.auction.AuctionResponse;
import com.ghtk.auction.dto.response.product.ProductDeletedResponse;
import com.ghtk.auction.dto.response.product.ProductResponse;
import com.ghtk.auction.entity.Auction;
import com.ghtk.auction.entity.Product;
import com.ghtk.auction.entity.UserAuction;
import com.ghtk.auction.service.AuctionService;
import com.ghtk.auction.service.ProductService;
import com.ghtk.auction.service.impl.ProductServiceImpl;
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
	
	@PostMapping("/")
	public ApiResponse<AuctionCreationResponse> createAuction(
			@AuthenticationPrincipal Jwt jwt,
			@RequestBody @Valid AuctionCreationRequest auctionCreationRequest) {
		return ApiResponse.<AuctionCreationResponse>builder()
				.success(true)
				.message("Tao thanh cong")
				.data(auctionService.addAuction(jwt, auctionCreationRequest))
				.build();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/get-my-created")
	public ApiResponse<List<AuctionResponse>> getMyCreatedAuctions(@AuthenticationPrincipal Jwt jwt) {
		return ApiResponse.<List<AuctionResponse>>builder()
				.success(true)
				.message("Lay thanh cong")
				.data(auctionService.getMyCreatedAuction(jwt))
				.build();
	}
	
	@GetMapping("/{id}")
	public ApiResponse<Auction> getAuctionById(@PathVariable Long id) {
		return ApiResponse.<Auction>builder()
				.success(true)
				.message("Lay thanh cong")
				.data(auctionService.getAuctionById(id))
				.build();
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse<Auction> deleteAuction(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable Long id) {
		return ApiResponse.<Auction>builder()
				.success(true)
				.message("Xoa thanh cong")
				.data(auctionService.deleteAuction(jwt,id))
				.build();
	}
	
	@GetMapping("/get-my-joined")
	public ApiResponse<List<Auction>> getMyJoined(@AuthenticationPrincipal Jwt jwt) {
		return ApiResponse.<List<Auction>>builder()
				.success(true)
				.message("Lay thanh cong")
				.data(auctionService.getMyJoinedAuction(jwt))
				.build();
	}
	
	@PostMapping("/regis-join/{id}")
	public ApiResponse<UserAuction> regisJoinAuction(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable Long id
	) {
		return ApiResponse.<UserAuction>builder()
				.success(true)
				.message("Ban da dang ky tham gia buoi dau gia thanh cong")
				.data(auctionService.registerJoinAuction(jwt, id))
				.build();
	}
}
