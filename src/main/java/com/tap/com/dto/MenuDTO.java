package com.tap.com.dto;

import com.tap.com.model.Menu;

public class MenuDTO {
	private Integer menuId;
	private String name;
	private float price;
	private String imagepath;
	private boolean isAvailable;
	private float rating;
	private String description;
	
	public MenuDTO() {

	}
	
	public MenuDTO(Menu menu) {
		super();
		this.menuId = menu.getMenuId();
		this.name = menu.getName();
		this.price = menu.getPrice();
		this.imagepath = menu.getImagepath();
		this.isAvailable = menu.isAvailable();
		this.rating = menu.getRating();
		this.description = menu.getDescription();
	}


	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
