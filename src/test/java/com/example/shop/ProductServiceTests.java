package com.example.shop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.shop.dto.ProductDTO;
import com.example.shop.errors.ResourceNotFoundException;
import com.example.shop.mapper.ProductMapper;
import com.example.shop.model.ProductEntity;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.SubscriberRepository;
import com.example.shop.service.impl.ProductServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    public void setup(){
    }

    // JUnit test for saveProduct method
    @DisplayName("JUnit test for createProduct method")
    @Test
    void whenCreateProduct_thenReturnProductEntity(){
        when(productMapper.entityToDto(productRepository.save(buildProductEntity()))).thenReturn(buildProductDTO(buildProductEntity()));
        ProductDTO createdProduct = productService.create(buildProductDTO(buildProductEntity()));

        // then - verify the output
        assertThat(createdProduct).isNotNull();
        assertEquals(createdProduct.getId(), buildProductDTO(buildProductEntity()).getId());
        assertEquals(createdProduct.getName(), buildProductDTO(buildProductEntity()).getName());
    }

    // JUnit test for getAllEmployees method
    @Test
    void whenGetAllProduct_thenReturnProductList() {
        // given - precondition or setup

        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setId(2);
        productEntity2.setName("Product 2");
        productEntity2.setAvailable(false);
        productEntity2.setForSale(true);

        List<ProductDTO> productDTOs = new ArrayList<>();
        productDTOs.add(productMapper.entityToDto(buildProductEntity()));
        productDTOs.add(productMapper.entityToDto(buildProductEntity()));
        
        List<ProductEntity> entities = List.of(buildProductEntity(), productEntity2);
        when(productRepository.findAll()).thenReturn(entities);

        // when -  action or the behaviour that we are going test
        List<ProductDTO> productDTOList = productService.getAll();

        // then - verify the output
        assertThat(productDTOList)
            .isNotNull()
            .hasSize(2);
    }

    // JUnit test for GetAllProductsBySubscriber_thenReturnProductList method
    // @Test
    // void whenGetAllProductsBySubscriber_thenReturnProductList() {
    //     Integer subscriberId = 1;
    //     SubscriberEntity subscriber = new SubscriberEntity();
    //     subscriber.setId(subscriberId);
    //     subscriber.setFirstName("First Name");
    //     subscriber.setLastName("Last Name");
    //     subscriber.setProducts(Set.of(buildProductEntity()));

    //     SubscriberDTO subscriberDTO = new SubscriberDTO();
    //     subscriberDTO.setId(subscriber.getId());
    //     subscriberDTO.setFirstName(subscriber.getFirstName());
    //     subscriberDTO.setLastName(subscriber.getLastName());
    //     subscriberDTO.setProducts(subscriber.getProducts().stream().map(productMapper::entityToDto).toList());

    //     when(subscriberRepository.findById(subscriberId)).thenReturn(Optional.of(subscriber));
    //     // when(subscriberMapper.entityToDto(subscriber)).thenReturn(subscriberDTO);
    //     // when(subscriberService.getById(subscriberId)).thenReturn(subscriberDTO);

    //     // List<ProductDTO> productDTOs = productService.getProductsBySubscriberId(subscriberId);
        
    //     // assertThat(productDTOs)
    //     //     .isNotNull()
    //     //     .hasSize(1);
    // }

    // JUnit negative test for getAllProducts method
    @Test
    void whenGetAllProducts_thenReturnEmptyProducList(){
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<ProductDTO> productList = productService.getAll();

        assertThat(productList).isEmpty();
        assertThat(productList.size()).isEqualTo(0);
    }

    
    @Test
    void whenGetProductById_thenReturnProductObject(){

        when(productRepository.findById(buildProductEntity().getId())).thenReturn(Optional.of(buildProductEntity()));
        when(productMapper.entityToDto(any())).thenReturn(buildProductDTO(buildProductEntity()));
        

        // when
        ProductDTO savedProductDTO = productService.getById(buildProductEntity().getId());

        // then
        assertThat(savedProductDTO).isNotNull();
    }

    @Test
    void whenGetProductById_thenReturnResourceNotFoundException(){

        when(productRepository.findById(any()))
            .thenReturn(Optional.empty());


        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.getById(buildProductEntity().getId());
        });

        verify(productRepository).findById(any());
    }

    // JUnit test for updateProduct method
    @Test
    void whenUpdateProduct_thenReturnUpdatedProduct(){
        ProductEntity productEntity = buildProductEntity();
        
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productRepository.findById(productEntity.getId())).thenReturn(Optional.of(productEntity));
        when(productRepository.findById(productEntity.getId())).thenReturn(Optional.of(productEntity));
        productEntity.setForSale(false);
        ProductDTO productDTO = buildProductDTO(productEntity);
        when(productMapper.dtoToEntity(any())).thenReturn(productEntity);
        when(productMapper.entityToDto(any())).thenReturn(productDTO);

        
        ProductDTO updatedProductDTO = productService.update(productDTO.getId(), productDTO);

        assertThat(updatedProductDTO.getName()).isEqualTo(productEntity.getName());
        assertThat(updatedProductDTO.isForSale()).isEqualTo(productEntity.isForSale());
    }

    // JUnit newgative test for updateProduct method
    @Test
    void whenUpdateProduct_thenReturnResourceNotFoundException() {
        when(productRepository.findById(buildProductEntity().getId())).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.update(buildProductEntity().getId(), buildProductDTO(buildProductEntity()));
        });

        verify(productRepository, never()).save(any(ProductEntity.class));
    }

    // JUnit test for deleteProduct method
    @Test
    void whenDeleteProduct_thenNothing() {
        willDoNothing().given(productRepository).deleteById(buildProductEntity().getId());
        when(productRepository.findById(buildProductEntity().getId())).thenReturn(Optional.of(buildProductEntity()));
        
        productService.delete(buildProductEntity().getId());

        verify(productRepository, times(1)).deleteById(buildProductEntity().getId());
    }

    // // JUnit negative test for deleteProduct method
    @Test
    void whenDeleteProduct_thenThrowsException() {
        when(productRepository.findById(buildProductEntity().getId())).thenReturn(Optional.empty());
        
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.delete(buildProductEntity().getId());
        });

        verify(productRepository, never()).deleteById(any());
    }

    private ProductEntity buildProductEntity(){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1);
        productEntity.setName("Product 1");
        productEntity.setForSale(true);
        productEntity.setAvailable(true);

        return productEntity;
    }

    private ProductDTO buildProductDTO(ProductEntity productEntity){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setName(productEntity.getName());
        productDTO.setForSale(productEntity.isForSale());
        productDTO.setAvailable(true);

        return productDTO;
    }
}
