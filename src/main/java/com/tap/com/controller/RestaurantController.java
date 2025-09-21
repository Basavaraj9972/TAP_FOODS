package com.tap.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tap.com.dto.RestaurantDTO;
import com.tap.com.model.Restaurant;
import com.tap.com.service.RestaurantServiceImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
	
	@Autowired
	public RestaurantServiceImpl restaurantServiceImpl;
	
	@GetMapping("/allrestuarant")
	public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
		return new  ResponseEntity<List<RestaurantDTO>>(restaurantServiceImpl.getAllRestaurants(),HttpStatus.OK);
	}

	@PostMapping("/saveRestaurants")
	public ResponseEntity<Restaurant> addRestaurants(@RequestBody Restaurant restaurant){
		return new ResponseEntity<Restaurant>(restaurantServiceImpl.addRestaurants(restaurant),HttpStatus.OK);
	}
	
	@PutMapping("/updateRestaurants")
	public  ResponseEntity<Restaurant> updateRestaurants(@RequestParam Integer restaurantId , @RequestBody Restaurant restaurant){
		System.out.println("update Restaurants calling :");
		return new ResponseEntity<Restaurant>(restaurantServiceImpl.updateRestaurants(restaurantId,restaurant),HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteRestaurants/{id}")
	public void deleteRestaurants(@PathVariable("id") Integer restaurantId){
		restaurantServiceImpl.deleteRestaurants(restaurantId);
	}
	
	@GetMapping("/getRestaurant")
	public ResponseEntity<RestaurantDTO> getRestaurant(@RequestParam Integer restaurantId) {
		return new ResponseEntity<RestaurantDTO>(restaurantServiceImpl.getRestaurant(restaurantId),HttpStatus.OK);
	}
}
