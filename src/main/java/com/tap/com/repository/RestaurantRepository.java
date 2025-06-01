package com.tap.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tap.com.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
	
}
