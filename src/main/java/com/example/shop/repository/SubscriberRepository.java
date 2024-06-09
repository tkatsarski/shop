package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.model.SubscriberEntity;
import java.util.Optional;


public interface SubscriberRepository extends JpaRepository<SubscriberEntity, Integer> {
    Optional<SubscriberEntity> findByFirstNameAndLastName(String firstName, String lastName);
}
