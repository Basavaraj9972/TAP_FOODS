package com.tap.com.serviceInf;

import java.util.List;

import com.tap.com.dto.MenuDTO;
import com.tap.com.model.Menu;

public interface MenuServiceDao {
	List<MenuDTO> getAllmenus();
	Menu getMenu(Integer menuId);
	List<MenuDTO> getMenuDTo(Integer restaurantId);
}
