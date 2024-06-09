package com.example.shop.dto;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@MappedSuperclass
public abstract class BaseDTO {
    private Integer id;
    
    private LocalDateTime createdAt;
}
