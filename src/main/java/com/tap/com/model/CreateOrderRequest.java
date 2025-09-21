package com.tap.com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
	 private int amount; // amount in paise
	// Default constructor
	    public CreateOrderRequest() {}

	    // Constructor with fields
	    public CreateOrderRequest(int amount) {
	        this.amount = amount;
	    }

	    // Getter
	    public int getAmount() {
	        return amount;
	    }

	    // Setter
	    public void setAmount(int amount) {
	        this.amount = amount;
	    }
}
