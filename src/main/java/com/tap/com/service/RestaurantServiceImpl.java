package com.tap.com.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tap.com.dto.RestaurantDTO;
import com.tap.com.model.Restaurant;
import com.tap.com.repository.RestaurantRepository;
import com.tap.com.serviceInf.RestaurantServiceDao;

@Service
public class RestaurantServiceImpl implements RestaurantServiceDao {
	
	@Autowired
	public RestaurantRepository restaurantRepository;

//	@Override
//	public List<Restaurant> getAllRestaurants() {
//		List<Restaurant> res = null;
//		res = 	restaurantRepository.findAll();
//		System.out.println(res);
////		res.map(UserDTO::new).orElse(null);
//		return res;
//	}

//	@Override
//	public List<RestaurantDTO> getAllRestaurants() {
//	    List<Restaurant> restaurants = restaurantRepository.findAll();
//	    
//	    // Convert List<Restaurant> to List<RestaurantDTO>
//	    List<RestaurantDTO> restaurantDTOs = restaurants.stream()
//	            .map(RestaurantDTO::new)
//	            .collect(Collectors.toList());
//
//	    System.out.println(restaurantDTOs);
//	    return restaurantDTOs;
//	}
	
	@Override
	public List<RestaurantDTO> getAllRestaurants() {
	    List<Restaurant> restaurants = restaurantRepository.findAll();
	    // Convert List<Restaurant> to List<RestaurantDTO>
	    return restaurants.stream()
	            .map(RestaurantDTO::new)
	            .collect(Collectors.toList());
	}

	@Override
	public Restaurant addRestaurants(Restaurant restaurant) {
		return restaurantRepository.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurants(Integer restaurantId, Restaurant restaurant) {
		Optional<Restaurant> restaurant1 = restaurantRepository.findById(restaurantId);
		System.out.println("Optional Restaurants "+restaurant1);

		if (restaurant1.isPresent()) {  // Check if the restaurant exists
		    Restaurant existingRestaurant = restaurant1.get();

		    System.out.println(existingRestaurant.getRestaurantId());
		    System.out.println(existingRestaurant.getName());

		    if (restaurant.getName() != null) {
		        existingRestaurant.setName(restaurant.getName());
		    }
		    if (restaurant.getImagePath() != null) {
		        existingRestaurant.setImagePath(restaurant.getImagePath());
		    }
		    if (restaurant.getRating() != 0) {
		        existingRestaurant.setRating(restaurant.getRating());
		    }
		    if (restaurant.getEta() != 0) {
		        existingRestaurant.setEta(restaurant.getEta());
		    }
		    if (restaurant.getCuisinType() != null) {
		        existingRestaurant.setCuisinType(restaurant.getCuisinType());
		    }
		    if (restaurant.getAddress() != null) {
		        existingRestaurant.setAddress(restaurant.getAddress());
		    }
		    
		    existingRestaurant.setActive(true);

		    // Save the updated restaurant
		    Restaurant updatedRestaurant = restaurantRepository.save(existingRestaurant);
		    System.out.println(updatedRestaurant);
		    
		    return updatedRestaurant;
		} else {
		    System.out.println("Restaurant not found with ID: " + restaurantId);
		    return null;  // Or throw an exception
		}
	}

	@Override
	public void deleteRestaurants(Integer restaurantId) {
		 restaurantRepository.deleteById(restaurantId);
	}

	@Override
	public RestaurantDTO getRestaurant(Integer restaurantId) {
		Optional<Restaurant> restaurant1 = restaurantRepository.findById(restaurantId);
//		Restaurant restaurant = null;
//		if(restaurant1.isPresent()) {
//			restaurant = restaurant1.get();
//		}
//		return restaurant;
//		Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
	    System.out.println(restaurant1.get().getUser());
		return restaurant1.map(RestaurantDTO::new).orElse(null);
	}
	
}
