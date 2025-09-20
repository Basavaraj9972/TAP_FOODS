package com.tap.com.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userId")  // Standard Hibernate naming
	private Integer userId;
	
	@Column(name="username",nullable = false,unique = true)
	private String username;
	
	@Column(name="email",unique = true)
	private String email;
	
	@Column(name="phone",unique=true)
	private String phone;
	
	@Column(name="password")
	private String password;
	
	@Column(name ="pin")
	private Integer pin;
	
    @Column(name="role")
    private String role;

    @Column(name="created_date")
    private LocalDateTime createdDate;

    @Column(name="last_login_date") // Fixed naming
    private LocalDateTime lastLoginDate;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
//    @JsonManagedReference
    private List<Restaurant> restaurant;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
//    @JsonManagedReference
    private List<Order> order;

    public UserEntity() {
		// TODO Auto-generated constructor stub
	}
    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPin() {
		return pin;
	}

	public void setPin(Integer pin) {
		this.pin = pin;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(LocalDateTime lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public List<Restaurant> getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(List<Restaurant> restaurant) {
		this.restaurant = restaurant;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	public List<OrderItems> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItems> orderItems) {
		this.orderItems = orderItems;
	}

	public List<OrderHistory> getOrderHistory() {
		return orderHistory;
	}

	public void setOrderHistory(List<OrderHistory> orderHistory) {
		this.orderHistory = orderHistory;
	}

	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
//    @JsonManagedReference
    private List<OrderItems> orderItems;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
//    @JsonManagedReference
    private List<OrderHistory> orderHistory;
    
    @Override
    public String toString() {
        return "UserEntity{id=" + userId + ", name='" + username + "'}";
    }

	public UserEntity(Integer userId, String username, String email, String phone, String password, Integer pin,
			String role, LocalDateTime createdDate, LocalDateTime lastLoginDate, List<Restaurant> restaurant,
			List<Order> order, List<OrderItems> orderItems, List<OrderHistory> orderHistory) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.pin = pin;
		this.role = role;
		this.createdDate = createdDate;
		this.lastLoginDate = lastLoginDate;
		this.restaurant = restaurant;
		this.order = order;
		this.orderItems = orderItems;
		this.orderHistory = orderHistory;
	}

	public UserEntity(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}
	public UserEntity(String username, String email, String password,LocalDateTime createdDate) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.createdDate = createdDate;
	}
    
    

}
