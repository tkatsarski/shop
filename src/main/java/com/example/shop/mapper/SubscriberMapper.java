package com.example.shop.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;

import com.example.shop.dto.SubscriberDTO;
import com.example.shop.model.SubscriberEntity;

@Mapper(componentModel = "spring")
public interface SubscriberMapper {
    SubscriberDTO entityToDto(SubscriberEntity entity);

    SubscriberEntity dtoToEntity(SubscriberDTO dto);

    @BeforeMapping
    default void productDTOListToProductEntityList(SubscriberEntity subscriberEntity){
        if(subscriberEntity.getProducts() != null) {
            subscriberEntity.getProducts().forEach(s -> s.setSubscribers(null));
        }
        
    }
}