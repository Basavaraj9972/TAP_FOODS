package com.tap.com.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tap.com.dto.MenuDTO;
import com.tap.com.exception.ResourceNotFoundException;
import com.tap.com.model.Menu;
import com.tap.com.repository.MenuRepository;
import com.tap.com.serviceInf.MenuServiceDao;

@Service
public class MenuServiceImpl implements MenuServiceDao{
	
	@Autowired
	public MenuRepository menuRepository;
	
	@Override
	public List<MenuDTO> getAllmenus() {
		List<Menu> menu = null;
		menu = menuRepository.findAll();
		if(menu == null) {
			new ResourceNotFoundException("Menu are not avialable", "Menu", menu);
		}
		
		return menu.stream()
		        .map(MenuDTO::new)
		        .collect(Collectors.toList());
	}

	@Override
	public Menu getMenu(Integer menuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MenuDTO> getMenuDTo(Integer restaurantId) {
		List<Menu> menu = menuRepository.getMenu(restaurantId);
		return menu.stream()
		        .map(MenuDTO::new)
		        .collect(Collectors.toList());
	}
	
	/*
	 * @Override public RestaurantDTO getRestaurant(Integer restaurantId) {
	 * Optional<Restaurant> restaurant1 =
	 * restaurantRepository.findById(restaurantId); // Restaurant restaurant = null;
	 * // if(restaurant1.isPresent()) { // restaurant = restaurant1.get(); // } //
	 * return restaurant; // Optional<UserEntity> userEntityOptional =
	 * userRepository.findById(userId);
	 * System.out.println(restaurant1.get().getUser()); return
	 * restaurant1.map(RestaurantDTO::new).orElse(null); }
	 */
	
}
