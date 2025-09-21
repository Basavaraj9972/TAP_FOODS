package com.tap.com.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RazorpayOrder {

    private String orderId;       // Razorpay order ID
    private int amount;           // Amount in paise
    private String currency;      // Currency, e.g., INR
    private String keyId;           // timestamp in seconds
}
