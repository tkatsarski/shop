package com.example.shop.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import com.example.shop.dto.ProductDTO;
import com.example.shop.model.ProductEntity;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO entityToDto(ProductEntity entity);

    ProductEntity dtoToEntity(ProductDTO dto);

    @BeforeMapping
    default void subscriberEntityListToSubscriberDTOList(ProductEntity productEntity){
        if(productEntity.getSubscribers() != null) {
            productEntity.getSubscribers().forEach(s -> s.setProducts(null));
        }
        
    }
}