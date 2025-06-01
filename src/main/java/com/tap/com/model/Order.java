package com.tap.com.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="`order`")
public class Order {
	
	@Id
	@Column(name="orderId")
	private String orderId;
	
	@Column(name="totalAmount")
	private float totalAmount;
	
	@Column(name="modeOfPayment")
	private String modeOfPayment;
	
	@Column(name="status")
	private String status;
	
	@Column(name="address")
	private String address;
	
	@Column(name="orderDateTime")
	private LocalDateTime orderDateTime;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//	@JsonManagedReference
	private List<OrderItems> orderItems;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//	@JsonManagedReference
	private List<OrderHistory> orderHistory;
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="restaurantId_och", nullable = false)
//	@JsonBackReference
	private Restaurant restaurant; 
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="userId_o", nullable = false)
//	@JsonBackReference
	private UserEntity user;
}
