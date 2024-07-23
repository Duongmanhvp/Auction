package com.ghtk.auction.controller.product;


import com.ghtk.auction.dto.request.product.ProductCreationRequest;
import com.ghtk.auction.dto.response.ApiResponse;
import com.ghtk.auction.dto.response.product.ProductDeletedResponse;
import com.ghtk.auction.dto.response.product.ProductResponse;
import com.ghtk.auction.entity.Product;
import com.ghtk.auction.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductController {
	
	final ProductService productService;
	
	@PostMapping
	public ResponseEntity<Product> create(@RequestBody ProductCreationRequest request) {
		return ResponseEntity.ok(productService.createProduct(request));
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<ProductResponse>> getAll() {
		return ResponseEntity.ok(productService.getAllMyProduct());
	}
	
	@PostMapping("/delete/{id}")
	public ResponseEntity<?> delete(
			@AuthenticationPrincipal Jwt principal,
			@PathVariable Long id) {
		System.out.println(principal);
		ProductResponse deleted = productService.deleteProduct(principal, id);
		return ResponseEntity.ok().body(new ProductDeletedResponse("Product was deleted!", deleted));
	}
	
	@PostMapping("/interest/{id}")
	public ApiResponse<?> interest(@AuthenticationPrincipal Jwt principal , @PathVariable Long id) {
		productService.interestProduct(principal, id);
		return ApiResponse.builder()
				.success(true)
				.message("Da thich")
				.build();
	}
	
	@GetMapping("/interest/{id}")
	public ResponseEntity<Long> getAllInterest(@PathVariable Long id) {
		return ResponseEntity.ok(productService.getInterestProduct(id));
	}
	
	@GetMapping("/getAllMyInterest")
	public ResponseEntity<List<ProductResponse>> getAllMyInterest(@AuthenticationPrincipal Jwt principal) {
		return ResponseEntity.ok(productService.getMyInterestProduct(principal));
	}
}
