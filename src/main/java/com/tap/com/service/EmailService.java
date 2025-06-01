package com.tap.com.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private StringRedisTemplate redisTemplate; 

//    public String sendOtp(String toEmail) {
//        // Generate a random 6-digit OTP
//        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
//
//        // Create email message
//        SimpleMailMessage message = new SimpleMailMessage();
//        System.out.println("Sending email to: [" + toEmail + "]");  // Debugging
//        message.setTo(toEmail.trim());  // Trim to remove extra spaces
////        message.setTo(toEmail);
//        message.setSubject("Your OTP Code");
//        message.setText("Your OTP code is: " + otp);
//        
//        
//        // Send email
//        mailSender.send(message);
//      return otp; // I
//    }
    
//    @Autowired
//    private JavaMailSender mailSender;

    private static final long TOKEN_EXPIRATION_TIME = 60000; // 1 minute (in ms)
    private static final String TOKEN_KEY_PREFIX = "Verifiy_OTP";
    public String sendOtp(String toEmail) {
        // Generate a random 6-digit OTP
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setTo(toEmail.trim());
            helper.setSubject("Your OTP Code for Sign Up");
            helper.setText(" To Register \n an Account Please Enter\n Your OTP code is: " + otp, false); // false = plain text
            
            

            // Set custom "from" name and email
            helper.setFrom("Aradya", "Tap Foods Project"); // <-- Change here

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace(); // You can handle the exception as needed
        }
        System.out.println("OTP Sent to: " + toEmail + ", OTP: " + otp);
//        redisTemplate.opsForValue().set("Online Swiggy project Otp", "", TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set(TOKEN_KEY_PREFIX + toEmail, otp, TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);
        
        return otp;
        
    }
    
    public boolean verifyOtp(String email, String otp) {
        System.out.println("Verifying for Email: " + email + ", OTP: " + otp);
        String storedOTP = redisTemplate.opsForValue().get(TOKEN_KEY_PREFIX + email);
        System.out.println("Stored OTP is: " + storedOTP);
        return storedOTP != null && storedOTP.equals(otp);
    }

    
}
