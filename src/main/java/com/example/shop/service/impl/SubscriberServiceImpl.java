package com.example.shop.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.shop.dto.ProductDTO;
import com.example.shop.dto.SubscriberDTO;
import com.example.shop.errors.ResourceNotFoundException;
import com.example.shop.mapper.ProductMapper;
import com.example.shop.mapper.SubscriberMapper;
import com.example.shop.model.ProductEntity;
import com.example.shop.model.SubscriberEntity;
import com.example.shop.repository.SubscriberRepository;
import com.example.shop.service.ProductService;
import com.example.shop.service.SubscriberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final SubscriberMapper subscriberMapper;
    private final ProductService productService;
    private final ProductMapper productMapper;
    
    @Override
    public SubscriberDTO create(SubscriberDTO subscriberDTO) {
        Optional<SubscriberEntity> subscriberOpt
            = subscriberRepository.findByFirstNameAndLastName(subscriberDTO.getFirstName(), subscriberDTO.getLastName());
        
        if(subscriberOpt.isPresent()){
            subscriberDTO.setId(subscriberOpt.get().getId());
        }

        return subscriberMapper.entityToDto(subscriberRepository.save(subscriberMapper.dtoToEntity(subscriberDTO)));
    }

    @Override
    public ProductDTO addProductSubscriber(Integer productId, SubscriberDTO subscriberDTO) {
        ProductDTO productDTO = productService.getById(productId);
        Optional<SubscriberEntity> subscriberOpt
            = subscriberRepository.findByFirstNameAndLastName(subscriberDTO.getFirstName(), subscriberDTO.getLastName());
        
        if(subscriberOpt.isPresent()){
            subscriberDTO.setId(subscriberOpt.get().getId());
        }

        Set<SubscriberEntity> subscriberSet 
            = new HashSet<>(productDTO.getSubscribers().stream()
                .map(subscriberMapper::dtoToEntity)
                .toList());
        subscriberSet.add(subscriberMapper.dtoToEntity(subscriberDTO));

        ProductEntity product = productMapper.dtoToEntity(productDTO);
        product.setSubscribers(subscriberSet);

        return productService.create(productMapper.entityToDto(product));
    }

    @Override
    public List<SubscriberDTO> getAll() {
        return subscriberRepository.findAll().stream()
            .map(subscriberMapper::entityToDto)
            .toList();
    }

    @Override
    public SubscriberDTO getById(@PathVariable("id") Integer id) {
        SubscriberEntity subscriberEntity = subscriberRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        return subscriberMapper.entityToDto(subscriberEntity);
    }

    @Override
    public List<SubscriberDTO> getSubscribersByProductId(Integer productId) {
        return productService.getById(productId).getSubscribers();
    }

    @Override
    public SubscriberDTO update(@PathVariable("id") Integer id, @RequestBody SubscriberDTO subscriberDTO) {
        Optional<SubscriberEntity> subscriberEntity = subscriberRepository.findById(id);

        if(!subscriberEntity.isPresent()) {
            throw new ResourceNotFoundException();
        }
        
        return subscriberMapper.entityToDto(subscriberRepository.save(subscriberMapper.dtoToEntity(subscriberDTO)));
    }

    @Override
    public void delete(Integer id) {
        if(!subscriberRepository.existsById(id)){
            throw new ResourceNotFoundException();
        }

        subscriberRepository.deleteById(id);
    }
}
