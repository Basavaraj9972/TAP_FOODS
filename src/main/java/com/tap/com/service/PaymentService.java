package com.tap.com.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final RazorpayClient razorpayClient;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    public PaymentService(@Value("${razorpay.key_id}") String keyId,
                          @Value("${razorpay.key_secret}") String keySecret) throws RazorpayException {
        this.razorpayClient = new RazorpayClient(keyId, keySecret);
    }

    public Order createOrder(int amount) throws RazorpayException {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100); // amount in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);
        return razorpayClient.orders.create(orderRequest);
    }

    /** verify signature coming from frontend after payment */
    public boolean verifyPaymentSignature(String orderId, String paymentId, String signature) throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", orderId);
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_signature", signature);

        return Utils.verifyPaymentSignature(options, keySecret);
    }

    /** verify signature of Razorpay webhooks */
    public boolean verifyWebhookSignature(String payload, String signature, String webhookSecret) throws RazorpayException {
        return Utils.verifyWebhookSignature(payload, signature, webhookSecret);
    }
}
