package com.tap.com.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayException;
import com.tap.com.model.CreateOrderRequest;
import com.tap.com.model.CreateOrderResponse;
import com.tap.com.model.VerifyPaymentRequest;
import com.tap.com.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.webhook.secret}")
    private String webhookSecret;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) throws RazorpayException {
        Order order = paymentService.createOrder(request.getAmount());

        CreateOrderResponse response = new CreateOrderResponse();
        response.setOrderId(order.get("id"));
        response.setAmount(request.getAmount());
        response.setCurrency("INR");
        response.setKeyId(keyId);

        return ResponseEntity.ok(response);
    }

    /** verify payment from frontend */
    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestBody VerifyPaymentRequest request) {
        try {
            boolean isValid = paymentService.verifyPaymentSignature(
                    request.getRazorpayOrderId(),
                    request.getRazorpayPaymentId(),
                    request.getRazorpaySignature());

            if (isValid) {
                return ResponseEntity.ok("Payment verified");
            } else {
                return ResponseEntity.status(400).body("Invalid signature");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error verifying signature");
        }
    }

    /** webhook from Razorpay dashboard */
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        String payload = sb.toString();
        String signature = request.getHeader("X-Razorpay-Signature");

        try {
            if (!paymentService.verifyWebhookSignature(payload, signature, webhookSecret)) {
                return ResponseEntity.status(400).body("Invalid signature");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error verifying signature");
        }

        return ResponseEntity.ok("Webhook processed");
    }
}
