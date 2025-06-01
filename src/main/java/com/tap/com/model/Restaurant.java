package com.tap.com.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="restaurant")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "restaurantId")
public class Restaurant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="restaurant_id")
	private Integer restaurantId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="image_path")
	private String imagePath;
	
	@Column(name="rating")
	private float rating;
	
	@Column(name="eta")
	private int eta;
	
	@Column(name="cuisin_type")
	private String cuisinType;
	
	@Column(name="address")
	private String address;
	
	@Column(name="is_active")
	private boolean active;

	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="restaurantOwnId",nullable = false)
//	@JsonBackReference
//	@JsonIgnore
	private UserEntity user;
	
	@OneToMany(mappedBy ="restaurant", cascade = CascadeType.ALL)
//	 @JsonManagedReference
	private List<Menu> menu;
	
	@OneToMany(mappedBy = "restaurant" , cascade = CascadeType.ALL)
//	 @JsonManagedReference
	private List<Order> orders;
	
	@Override
	public String toString() {
	    return "Restaurant{id=" + restaurantId + ", name='" + name + "'}";
	}
	
	public boolean getActive() {
		return this.active;
	}

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

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<Menu> getMenu() {
		return menu;
	}

	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	

}