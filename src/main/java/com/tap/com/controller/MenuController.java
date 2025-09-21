package com.tap.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tap.com.dto.MenuDTO;
import com.tap.com.service.MenuServiceImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/menus")
public class MenuController {
	
	@Autowired
	public MenuServiceImpl menuServiceImpl;
	
	@GetMapping("/getAllMenu")
	public ResponseEntity<List<MenuDTO>> getAllMenus(){
		System.out.println("getting menus");
		return new ResponseEntity<List<MenuDTO>>(menuServiceImpl.getAllmenus(),HttpStatus.OK);
	}
	
	@GetMapping("/getMenu")
	public ResponseEntity<List<MenuDTO>> getMenu(@RequestParam Integer restaurantId){
		System.out.println("getting menus");
		System.out.println(restaurantId+"getting menus");
		return new ResponseEntity<List<MenuDTO>>(menuServiceImpl.getMenuDTo(restaurantId),HttpStatus.OK);
	}
	
	
}
