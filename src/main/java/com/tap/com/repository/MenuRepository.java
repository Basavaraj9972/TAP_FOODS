package com.tap.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tap.com.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer>{
	 
	@Query("SELECT u FROM Menu u WHERE u.restaurant.id = :restaurantId")
	List<Menu> getMenu(@Param("restaurantId") Integer restaurantId);

}
