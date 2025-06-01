package com.tap.com.serviceInf;

import java.util.List;

import com.tap.com.dto.RestaurantDTO;
import com.tap.com.model.Restaurant;

public interface RestaurantServiceDao {
	List<RestaurantDTO> getAllRestaurants();
	RestaurantDTO getRestaurant(Integer restaurantId);
	Restaurant addRestaurants(Restaurant restaurant);
	Restaurant updateRestaurants(Integer restaurantId ,Restaurant restaurant);
	void deleteRestaurants(Integer restaurantId);
	
}
