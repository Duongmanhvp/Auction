package com.ghtk.auction.repository.Custom;

import com.ghtk.auction.dto.response.product.ProductResponse;
import com.ghtk.auction.dto.response.product.ProductSearchResponse;
import com.ghtk.auction.entity.Product;
import com.ghtk.auction.enums.ProductCategory;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {

    List<ProductSearchResponse> findProduct(String key, Pageable pageable, ProductCategory category);
}
