package com.tap.com.dto;

import com.tap.com.model.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
	
	private Integer restaurantId;
	private String name;
	private String imagePath;
	private float rating;
	private int eta;
	private String cuisinType;
	private String address;
	private boolean active;
	
	public RestaurantDTO(Restaurant restaurant) {
		this.restaurantId = restaurant.getRestaurantId();
		this.name = restaurant.getName();
		this.imagePath = restaurant.getImagePath();
		this.rating = restaurant.getRating();
		this.eta = restaurant.getEta();
		this.cuisinType = restaurant.getCuisinType();
		this.address = restaurant.getAddress();
		this.active = restaurant.getActive();
	}
	
//	public DTO_ToRestaurant(RestaurantDTO restaurant) {
//		this.restaurantId = restaurant.getRestaurantId();
//		this.name = restaurant.getName();
//		this.imagePath = restaurant.getImagePath();
//		this.rating = restaurant.getRating();
//		this.eta = restaurant.getEta();
//		this.cuisinType = restaurant.getCuisinType();
//		this.address = restaurant.getAddress();
//		this.active = restaurant.getActive();
//	}

	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public int getEta() {
		return eta;
	}

	public void setEta(int eta) {
		this.eta = eta;
	}

	public String getCuisinType() {
		return cuisinType;
	}

	public void setCuisinType(String cuisinType) {
		this.cuisinType = cuisinType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
