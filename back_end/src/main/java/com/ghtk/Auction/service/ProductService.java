package com.ghtk.auction.service;


import com.ghtk.auction.dto.request.product.ProductCreationRequest;
import com.ghtk.auction.dto.response.product.ProductResponse;
import com.ghtk.auction.entity.Product;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface ProductService {
	
	Product createProduct(ProductCreationRequest request);
	
	List<ProductResponse> getAllMyProduct();
	
	ProductResponse deleteProduct(Jwt principal, Long id);
	
	void interestProduct(Jwt principal, Long id);
	
	Long getInterestProduct(Long id);
	
	List<ProductResponse> getMyInterestProduct(Jwt principal);
	
}