package com.ghtk.auction.repository.Custom.impl;

import com.ghtk.auction.dto.response.product.ProductResponse;
import com.ghtk.auction.dto.response.product.ProductSearchResponse;
import com.ghtk.auction.entity.Product;
import com.ghtk.auction.enums.ProductCategory;
import com.ghtk.auction.repository.Custom.ProductRepositoryCustom;
import com.ghtk.auction.service.ImageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductSearchResponse> findProduct(String key, Pageable pageable, ProductCategory category) {
        StringBuilder sql = new StringBuilder("SELECT p.id, u.full_name, p.name, p.category, p.description, p.image FROM product p ");
        StringBuilder where = new StringBuilder("WHERE 1=1 ");
        sql.append("join user u on u.id = p.owner_id ");
        if(key != null && !key.equals("")) {
            where.append("AND p.name like " + "'" + key + "%' ");
        }
        if(category != null) {
            where.append("AND p.category = " + "'" + category.toString() + "' ");
        }
        where.append("ORDER BY id DESC ");
        sql.append(where);
        sql.append(" LIMIT ").append(pageable.getPageSize()).append("\n");
        sql.append(" OFFSET ").append(pageable.getOffset());
        Query query = entityManager.createNativeQuery(sql.toString());
        List<Object[]> results = query.getResultList();
        List<ProductSearchResponse> responses = new ArrayList<>();

        for (Object[] result : results) {
            ProductSearchResponse response = new ProductSearchResponse();
            response.setId((Long) result[0]);
            response.setOwner((String) result[1]);
            response.setName((String) result[2]);
            response.setCategory(ProductCategory.valueOf((String) result[3]));
            response.setDescription((String) result[4]);
            response.setImage((String) result[5]);
            responses.add(response);
        }

        return responses;
    }
}
