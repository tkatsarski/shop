package com.example.shop.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.shop.dto.ProductDTO;
import com.example.shop.dto.SubscriberDTO;

public interface SubscriberService {
    SubscriberDTO create(@RequestBody SubscriberDTO subscriberDTO);
    ProductDTO addProductSubscriber(Integer productId, SubscriberDTO subscriberDTO);
    List<SubscriberDTO> getSubscribersByProductId(Integer productId);
    List<SubscriberDTO> getAll();
    SubscriberDTO getById(@PathVariable("id") Integer id);
    SubscriberDTO update(@PathVariable("id") Integer id, @RequestBody SubscriberDTO subscriberDTO);
    void delete(@PathVariable("id") Integer id);
}
