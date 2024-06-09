package com.example.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.shop.dto.ProductDTO;
import com.example.shop.errors.ResourceNotFoundException;
import com.example.shop.mapper.ProductMapper;
import com.example.shop.model.ProductEntity;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.ProductService;
import com.example.shop.service.SubscriberService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final SubscriberService subscriberService;

    ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, @Lazy SubscriberService subscriberService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.subscriberService = subscriberService;
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        return productMapper.entityToDto(productRepository.save(productMapper.dtoToEntity(productDTO)));
    }

    @Override
    public List<ProductDTO> getProductsBySubscriberId(Integer subscriberId) {
        return subscriberService.getById(subscriberId).getProducts();
    }

    @Override
    public List<ProductDTO> getAll() {
        return productRepository.findAll().stream()
            .map(productMapper::entityToDto)
            .toList();
    }

    @Override
    public ProductDTO getById(@PathVariable("id") Integer id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        return productMapper.entityToDto(productEntity);
    }

    @Override
    public ProductDTO update(@PathVariable("id") Integer id, @RequestBody ProductDTO productDTO) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);

        if(!productEntity.isPresent()) {
            throw new ResourceNotFoundException();
        }
        
        return productMapper.entityToDto(productRepository.save(productMapper.dtoToEntity(productDTO)));
    }

    @Override
    public void delete(Integer id) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);

        if(!productEntity.isPresent()) {
            throw new ResourceNotFoundException();
        }
        
        productRepository.deleteById(id);
    }
}
