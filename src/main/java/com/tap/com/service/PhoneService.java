package com.tap.com.service;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class PhoneService {

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public String sendOtp(String toPhoneNumber) {
        String otp = generateOtp();
        System.out.println(otp);
        String formattedNumber = toPhoneNumber.startsWith("+") ? toPhoneNumber : "+91" + toPhoneNumber; // Example for India
        System.out.println("User Phone number is :"+toPhoneNumber);
        System.out.println("Twillo Phone number is :"+twilioPhoneNumber);
        String messageBody = "Your OTP is: " + otp;
        System.out.println("formattedNumber:+ "+formattedNumber);
        Message message = Message.creator(
                new PhoneNumber(formattedNumber),
                new PhoneNumber(twilioPhoneNumber),
                messageBody
        ).create();

        System.out.println("OTP Sent: " + message.getSid());
        return otp;  // Store this OTP in Redis or Database for verification
    }

    private String generateOtp() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000)); // Generates a 6-digit OTP
    }
}
