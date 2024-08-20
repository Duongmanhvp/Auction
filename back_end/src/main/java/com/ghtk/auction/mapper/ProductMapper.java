package com.ghtk.auction.mapper;

import com.ghtk.auction.dto.response.product.ProductResponse;
import com.ghtk.auction.entity.Product;
import com.ghtk.auction.entity.User;
import com.ghtk.auction.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "owner", expression = "java(getOwnerName(product.getOwnerId()))")
    public abstract ProductResponse toProductResponse(Product product);

    protected String getOwnerName(Long ownerId) {
        return userRepository.findById(ownerId)
                .map(User::getFullName)
                .orElse("Unknown");
    }
}
