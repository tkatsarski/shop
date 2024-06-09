package com.example.shop.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.shop.dto.ProductDTO;

public interface ProductService {
    ProductDTO create(@RequestBody ProductDTO productDTO);
    List<ProductDTO> getProductsBySubscriberId(Integer subscriberId);
    List<ProductDTO> getAll();
    ProductDTO getById(@PathVariable("id") Integer id);
    ProductDTO update(@PathVariable("id") Integer id, @RequestBody ProductDTO productDTO);
    void delete(@PathVariable("id") Integer id);
}
