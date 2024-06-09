package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.shop.model.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> { 
    
}
