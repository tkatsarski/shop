package com.example.shop.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.dto.ProductDTO;
import com.example.shop.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO productDTO) {
        return new ResponseEntity<>(productService.create(productDTO), HttpStatus.CREATED);
    }

    @GetMapping("/subscriber/{subscriberId}")
    public ResponseEntity<List<ProductDTO>> getProductSubscriber(@PathVariable Integer subscriberId) {
        return new ResponseEntity<>(productService.getProductsBySubscriberId(subscriberId), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable("id") Integer id, @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.update(id, productDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        productService.delete(id);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
