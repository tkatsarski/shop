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
import com.example.shop.dto.SubscriberDTO;
import com.example.shop.service.SubscriberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subscriber")
class SubscriberController {

    private final SubscriberService subscriberService;

    @PostMapping
    public ResponseEntity<SubscriberDTO> create(@RequestBody @Valid SubscriberDTO subscriberDTO) {
        return new ResponseEntity<>(subscriberService.create(subscriberDTO), HttpStatus.CREATED);
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<ProductDTO> addProductSubscriber(
            @PathVariable Integer productId, 
            @RequestBody @Valid SubscriberDTO subscriberDTO) {

        return new ResponseEntity<>(subscriberService.addProductSubscriber(productId, subscriberDTO), HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<SubscriberDTO>> getProductSubscriber(@PathVariable Integer productId) {
        return new ResponseEntity<>(subscriberService.getSubscribersByProductId(productId), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<SubscriberDTO> getById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(subscriberService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SubscriberDTO>> getAll() {
        return new ResponseEntity<>(subscriberService.getAll(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<SubscriberDTO> update(@PathVariable("id") Integer id, @RequestBody SubscriberDTO subscriberDTO) {
        return new ResponseEntity<>(subscriberService.update(id, subscriberDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        subscriberService.delete(id);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
