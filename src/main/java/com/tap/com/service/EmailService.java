package com.tap.com.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
    public String sendOtp1(String toEmail) {
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
    
    public String sendOtp(String toEmail) {
        // Generate a random 6-digit OTP
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true = multipart (for HTML + image)

            helper.setTo(toEmail.trim());
            helper.setSubject("Your OTP Code for Sign Up");

            // HTML Body
            String htmlBody = "<html><body style='font-family: Arial, sans-serif;'>"
                    + "<div style='max-width: 500px; margin: auto; padding: 20px; border: 1px solid #eee; border-radius: 10px;'>"
                    + "<div style='text-align: center;'>"
                    + "<img src='cid:logoImage' alt='Tap Foods Logo' style='width: 150px; margin-bottom: 20px;'/>"
                    + "<h2>Welcome to Tap Foods</h2>"
                    + "<p>To register your account, please use the following OTP:</p>"
                    + "<h1 style='color: #4CAF50; letter-spacing: 5px;'>" + otp + "</h1>"
                    + "<p>This OTP is valid for 1 minutes.</p>"
                    + "<br/>"
                    + "<p style='font-size: 12px; color: gray;'>If you did not request this, please ignore this email.</p>"
                    + "</div></div></body></html>";

            helper.setText(htmlBody, true); // true = HTML content

            // Set custom "from" name and email
            helper.setFrom("aradya@example.com", "Tap Foods Project");

            // Add inline logo (put logo.png in src/main/resources/static/images/logo.png)
            ClassPathResource logo = new ClassPathResource("static/images/tapFoodImage.jpeg");
            helper.addInline("logoImage", logo);

            // Send
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
        }

        System.out.println("OTP Sent to: " + toEmail + ", OTP: " + otp);

        // Save to Redis
        redisTemplate.opsForValue().set(TOKEN_KEY_PREFIX + toEmail, otp, TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);

        return otp;
    }
    
    public String sendOtpForgetPassword(String toEmail) {
        // Generate a random 6-digit OTP
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true = multipart (for HTML + image)

            helper.setTo(toEmail.trim());
            helper.setSubject("Your OTP Code for Forget Password");

            // HTML Body
            String htmlBody = "<html><body style='font-family: Arial, sans-serif;'>"
                    + "<div style='max-width: 500px; margin: auto; padding: 20px; border: 1px solid #eee; border-radius: 10px;'>"
                    + "<div style='text-align: center;'>"
                    + "<img src='cid:logoImage' alt='Tap Foods Logo' style='width: 150px; margin-bottom: 20px;'/>"
                    + "<h2>Please use below OTP for reset new password</h2>"
                    + "<p>To reset your password, please use the following OTP:</p>"
                    + "<h1 style='color: #4CAF50; letter-spacing: 5px;'>" + otp + "</h1>"
                    + "<p>This OTP is valid for 1 minutes.</p>"
                    + "<br/>"
                    + "<p style='font-size: 12px; color: gray;'>If you did not request this, please ignore this email.</p>"
                    + "</div></div></body></html>";

            helper.setText(htmlBody, true); // true = HTML content

            // Set custom "from" name and email
            helper.setFrom("aradya@example.com", "Tap Foods Project");

            // Add inline logo (put logo.png in src/main/resources/static/images/logo.png)
            ClassPathResource logo = new ClassPathResource("static/images/tapFoodImage.jpeg");
            helper.addInline("logoImage", logo);

            // Send
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
        }

        System.out.println("OTP Sent to: " + toEmail + ", OTP: " + otp);

        // Save to Redis
        redisTemplate.opsForValue().set(TOKEN_KEY_PREFIX + toEmail, otp, TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);

        return otp;
    }
    
    public void sendWelcomeEmail(String toEmail, String name) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true = multipart (for HTML + images)

            helper.setTo(toEmail.trim());
            helper.setSubject("🎉 Welcome to Tap Foods - Registration Successful!");

            String htmlBody = "<html><body style='font-family: Arial, sans-serif;'>"
                    + "<div style='max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #f9f9f9;'>"
                    + "<div style='text-align: center;'>"
                    + "<img src='cid:logoImage' alt='Tap Foods Logo' style='width: 120px; margin-bottom: 20px;'/>"
                    + "<h2 style='color: #4CAF50;'>Welcome to Tap Foods!</h2>"
                    + "<p style='font-size: 16px;'>Hi <strong>" + name + "</strong>,</p>"
                    + "<p style='font-size: 15px;'>We're thrilled to have you on board. Your registration was successful 🎉</p>"
                    + "<p style='font-size: 14px;'>Start exploring delicious food and exclusive deals today!</p>"
                    + "<a href='https://tapfoods.example.com' style='display: inline-block; padding: 12px 20px; margin: 20px auto; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px;'>Visit Tap Foods</a>"
                    + "<p style='font-size: 12px; color: #888;'>If you did not register, please ignore this email.</p>"
                    + "</div></div></body></html>";

            helper.setText(htmlBody, true);
            helper.setFrom("aradya@example.com", "Tap Foods Project");

            // Logo image inline
            ClassPathResource logo = new ClassPathResource("static/images/tapFoodImage.jpeg");
            helper.addInline("logoImage", logo);

            mailSender.send(message);
            System.out.println("Welcome email sent to: " + toEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void forgetSuccess(String toEmail, String name) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true = multipart (for HTML + images)

            helper.setTo(toEmail.trim());
            helper.setSubject("🎉 Welcome to Tap Foods - forget password Successful!");

            String htmlBody = "<html><body style='font-family: Arial, sans-serif;'>"
                    + "<div style='max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #f9f9f9;'>"
                    + "<div style='text-align: center;'>"
                    + "<img src='cid:logoImage' alt='Tap Foods Logo' style='width: 120px; margin-bottom: 20px;'/>"
                    + "<h2 style='color: #4CAF50;'>Welcome to Tap Foods!</h2>"
                    + "<p style='font-size: 16px;'>Hi <strong>" + name + "</strong>,</p>"
                    + "<p style='font-size: 15px;'>Your forget password is successful 🎉</p>"
                    + "<p style='font-size: 14px;'>Start exploring delicious food and exclusive deals today!</p>"
                    + "<a href='https://tapfoods.example.com' style='display: inline-block; padding: 12px 20px; margin: 20px auto; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px;'>Visit Tap Foods</a>"
                    + "<p style='font-size: 12px; color: #888;'>If you did not forget password, please ignore this email.</p>"
                    + "</div></div></body></html>";

            helper.setText(htmlBody, true);
            helper.setFrom("aradya@example.com", "Tap Foods Project");

            // Logo image inline
            ClassPathResource logo = new ClassPathResource("static/images/tapFoodImage.jpeg");
            helper.addInline("logoImage", logo);

            mailSender.send(message);
            System.out.println("Welcome email sent to: " + toEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public boolean verifyOtp(String email, String otp) {
        System.out.println("Verifying for Email: " + email + ", OTP: " + otp);
        String storedOTP = redisTemplate.opsForValue().get(TOKEN_KEY_PREFIX + email);
        System.out.println("Stored OTP is: " + storedOTP);
        return storedOTP != null && storedOTP.equals(otp);
    }

    
}
