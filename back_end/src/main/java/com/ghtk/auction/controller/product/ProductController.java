package com.ghtk.auction.controller.product;


import com.ghtk.auction.dto.request.product.ProductCreationRequest;
import com.ghtk.auction.dto.request.product.ProductFilterRequest;
import com.ghtk.auction.dto.response.ApiResponse;
import com.ghtk.auction.dto.response.product.ProductDeletedResponse;
import com.ghtk.auction.dto.response.product.ProductResponse;
import com.ghtk.auction.entity.Product;
import com.ghtk.auction.enums.ProductCategory;
import com.ghtk.auction.service.ProductService;
import com.ghtk.auction.service.impl.ProductServiceImpl;
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
@RequestMapping("/v1/products")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductController {
	
	final ProductService productService;
	private final ProductServiceImpl productServiceImpl;
	
	@PostMapping
	public ResponseEntity<Product> create(@RequestBody ProductCreationRequest request) {
		return ResponseEntity.ok(productService.createProduct(request));
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/get-my-all")
	public ResponseEntity<List<ProductResponse>> getAll() {
		return ResponseEntity.ok(productService.getAllMyProduct());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/get-my-by-category")
	public ApiResponse<List<ProductResponse>> getMyAllByCategory(
			@AuthenticationPrincipal Jwt principal,
			@RequestBody ProductFilterRequest request) {
		return ApiResponse.<List<ProductResponse>>builder()
				.success(true)
				.message("Lay thanh cong")
				.data(productService.getMyByCategory(principal,request))
				.build();
	}
	
	@DeleteMapping("/{id}")
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
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/getAllMyInterest")
	public ResponseEntity<List<ProductResponse>> getAllMyInterest(@AuthenticationPrincipal Jwt principal) {
		return ResponseEntity.ok(productService.getMyInterestProduct(principal));
	}
	
	@GetMapping("/")
	public ApiResponse<List<ProductResponse>> getAllProductByCategory(@RequestBody ProductFilterRequest category) {
		return ApiResponse.<List<ProductResponse>>builder()
				.success(true)
				.message("Lay thanh cong")
				.data(productService.searchProductbyCategory(category))
				.build();
	}
	
	@GetMapping("/{id}")
	public ApiResponse<Product> getProduct(@PathVariable Long id) {
		return ApiResponse.<Product>builder()
				.success(true)
				.message("Lay thanh cong")
				.data(productService.getById(id))
				.build();
	}
}
