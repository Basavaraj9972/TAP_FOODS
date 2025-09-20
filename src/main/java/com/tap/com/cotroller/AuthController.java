package com.tap.com.cotroller;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tap.com.JwtUtil;
import com.tap.com.model.UserEntity;
import com.tap.com.service.EmailService;
import com.tap.com.serviceInf.UserServiceDao;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
    private final JwtUtil jwtUtil;
    private final UserServiceDao userServiceDao;
    
    @Autowired
    private RestTemplate restTemplate; // add this as a bean or manually create
   
    @Autowired
    EmailService emailService;
    
    public AuthController(JwtUtil jwtUtil,UserServiceDao userServiceDao) {
        this.jwtUtil = jwtUtil;
        this.userServiceDao = userServiceDao;
    }
    

    @GetMapping("/message")
    public ResponseEntity<String> getAuthMessage(HttpSession session) {
        String message = (String) session.getAttribute("auth_message");
        session.removeAttribute("auth_message"); // Clear after read (flash behavior)
        return ResponseEntity.ok(message != null ? message : "");
    }

//
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestParam  String username, @RequestParam String password, HttpServletResponse response) {
//		System.out.println(username);
//		System.out.println(password);
//		//		String username = loginRequest.get("username");
////		String password = loginRequest.get("password");
//		User user = null;
//		user = userServiceDao.findByUserNamePassword(username, password);
//		// Validate user (replace with actual authentication logic)
//		if (user != null) {
//			String token = jwtUtil.generateToken(username);
//			
//			// Create an HTTP-only, Secure cookie for the JWT token
//			ResponseCookie jwtCookie = ResponseCookie.from("JWT_TOKEN", token)
//					.httpOnly(true)        // Prevent access from JavaScript
//					.secure(true)          // Send only over HTTPS
//					.path("/")             // Available for all endpoints
//	//				.maxAge(Duration.ofHours(2)) // Cookie expiry
//					.maxAge(Duration.ofMinutes(1)) // Cookie expiry
//					.sameSite("Strict")    // Prevent CSRF attacks
//					.build();
//			// Add cookie to response header
//			response.addHeader("Set-Cookie", jwtCookie.toString());
//			
//			return ResponseEntity.ok(Collections.singletonMap("message", "Login successful"));
//		}
//		return ResponseEntity.ok(Collections.singletonMap("message", "Login Failed"));
//	}
    

	
	@PostMapping("/loginByuserNamePassword")
	public ResponseEntity<?> loginByuserNamePassword(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
		String username = loginRequest.get("username");
		String password = loginRequest.get("password");
		
		System.err.println(username);
		System.err.println(password);
		UserEntity user = null;
		user = userServiceDao.findByUserNamePassword(username, password);
		System.out.println(user);
		// Validate user (replace with actual authentication logic)
		if (user != null) {
			String token = jwtUtil.generateToken(username);
			// Create an HTTP-only, Secure cookie for the JWT token
			ResponseCookie jwtCookie = ResponseCookie.from("JWT_TOKEN", token)
					.httpOnly(true)        // Prevent access from JavaScript
					.secure(true)          // Send only over HTTPS
					.path("/")             // Available for all endpoints
	//				.maxAge(Duration.ofHours(2)) // Cookie expiry
					.maxAge(Duration.ofMinutes(30)) // Cookie expiry
					.sameSite("Strict")    // Prevent CSRF attacks
					.build();
			

	        // Create an HTTP-only, Secure cookie for the Username
//	        ResponseCookie userCookie = ResponseCookie.from("USERNAME", username)
//	                .httpOnly(true)
//	                .secure(true)
//	                .path("/")
//	                .maxAge(Duration.ofMinutes(30))
//	                .sameSite("Strict")
//	                .build();
			// Add cookie to response header
			response.addHeader("Set-Cookie", jwtCookie.toString());
//			response.addHeader("Set-Cookie", userCookie.toString());
			
			return ResponseEntity.ok(Collections.singletonMap("message", "Login successful"));
		}
		return ResponseEntity.ok(Collections.singletonMap("message", "Login Failed"));
	}
	
	@PostMapping("/loginByuserNameEmailPassword")
	public ResponseEntity<?> loginByuserNameOrEmailPassword(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
		System.out.println("API is calling");
		String emailOrUsername = loginRequest.get("emailOrUsername");
		String password = loginRequest.get("password");
//		String email = loginRequest.get("email");
		System.err.println(emailOrUsername);
		System.err.println(password);
		UserEntity user = null;
		user = userServiceDao.findByUserNamePassword(emailOrUsername, password);
		System.out.println(user);
		if(user == null) {
			user = userServiceDao.findByEmailPassword(emailOrUsername, password);
		}
		// Validate user (replace with actual authentication logic)
		if (user != null) {
			String token = jwtUtil.generateToken(user.getUsername());
			// Create an HTTP-only, Secure cookie for the JWT token
			ResponseCookie jwtCookie = ResponseCookie.from("JWT_TOKEN", token)
					.httpOnly(true)        // Prevent access from JavaScript
					.secure(true)          // Send only over HTTPS
					.path("/")             // Available for all endpoints
					//				.maxAge(Duration.ofHours(2)) // Cookie expiry
					.maxAge(Duration.ofMinutes(30)) // Cookie expiry
					.sameSite("Strict")    // Prevent CSRF attacks
					.build();
			LocalDateTime date = LocalDateTime.now();
			user.setLastLoginDate(date);
			userServiceDao.saveUser(user);
			
			// Create an HTTP-only, Secure cookie for the Username
//	        ResponseCookie userCookie = ResponseCookie.from("USERNAME", username)
//	                .httpOnly(true)
//	                .secure(true)
//	                .path("/")
//	                .maxAge(Duration.ofMinutes(30))
//	                .sameSite("Strict")
//	                .build();
			// Add cookie to response header
			response.addHeader("Set-Cookie", jwtCookie.toString());
//			response.addHeader("Set-Cookie", userCookie.toString());
			
			return ResponseEntity.ok(Collections.singletonMap("message", "Login successful"));
		}
		return ResponseEntity.ok(Collections.singletonMap("message", "Invalid User"));
	}
	
	@PostMapping("/forget-password-send-otp")
	public ResponseEntity<?> checkUserSendOTP(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
//	    String username = loginRequest.get("username");
		System.out.println("API is calling");
	    String email = loginRequest.get("email");
	    System.out.println("User email is :" +email);
//	    loginRequest.get("email");

//	    UserEntity user = userServiceDao.findByEmailUserName(email, username);

//	    if (user == null) {
	    UserEntity user = userServiceDao.findByEmail(email);
	    	if(user != null) {
	        // Call OTP send API
	        try {
	            String otpApiUrl = "http://localhost:8080/api/user/send-otp-forgetPassword?email=" + email;
	            ResponseEntity<String> otpResponse = restTemplate.postForEntity(otpApiUrl, null, String.class);
	            System.out.println("Otp Respone : "+otpResponse);
	            if (otpResponse.getStatusCode().is2xxSuccessful()) {
	            	System.out.println("OTP send successfully ");
	                return ResponseEntity.ok(Collections.singletonMap("message", "OTP sent to email."));
	            } else {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                        .body(Collections.singletonMap("message", "Failed to send OTP"));
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(Collections.singletonMap("message", "Error calling OTP service: " + e.getMessage()));
	        }
	    }
	    return ResponseEntity.ok(Collections.singletonMap("message", "User not exists"));
	}

	@PostMapping("/SignUp_Existing_User_Checking")
	public ResponseEntity<?> signUp(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
	    String username = loginRequest.get("username");
	    String email = loginRequest.get("email");
//	    loginRequest.get("email");

	    UserEntity user = userServiceDao.findByEmailUserName(email, username);

	    if (user == null) {
	    	user = userServiceDao.findByEmail(email);
	    	if(user == null) {
	        // Call OTP send API
	        try {
	            String otpApiUrl = "http://localhost:8080/api/user/send-otp?email=" + email;
	            ResponseEntity<String> otpResponse = restTemplate.postForEntity(otpApiUrl, null, String.class);
	            System.out.println("Otp Respone : "+otpResponse);
	            if (otpResponse.getStatusCode().is2xxSuccessful()) {
	            	System.out.println("OTP send successfully ");
//	                String token = jwtUtil.generateToken(username);
//
//	                ResponseCookie jwtCookie = ResponseCookie.from("JWT_TOKEN", token)
//	                        .httpOnly(true)
//	                        .secure(true)
//	                        .path("/")
//	                        .maxAge(Duration.ofMinutes(30))
//	                        .sameSite("Strict")
//	                        .build();
//
//	                response.addHeader("Set-Cookie", jwtCookie.toString());
//
	                return ResponseEntity.ok(Collections.singletonMap("message", "OTP sent to email."));
	            } else {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                        .body(Collections.singletonMap("message", "Failed to send OTP"));
	            }
	        
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(Collections.singletonMap("message", "Error calling OTP service: " + e.getMessage()));
	        }
	    }
	    }
	    return ResponseEntity.ok(Collections.singletonMap("message", "User already exists"));
	}

	@PostMapping("/RegisterByUserNameEmail")
	public ResponseEntity<?> Registration(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
	    String username = loginRequest.get("username");
	    String email = loginRequest.get("email");
	    String password = loginRequest.get("password");
//	    Integer pin = (Integer.valueOf(loginRequest.get("pin")));

	    UserEntity user = userServiceDao.findByEmailUserName(email, username);

	    if (user == null) {
	    	user = userServiceDao.findByEmail(email);
	    	if(user == null) {
	    		LocalDateTime date = LocalDateTime.now();
	    		UserEntity userEntity = new UserEntity(username,email,password,date);
	    		userServiceDao.saveUser(userEntity);
	    		emailService.sendWelcomeEmail(email, username);
	    		return ResponseEntity.ok(Collections.singletonMap("message","Registration Successfull."));
	    	}
	    }
	    
	        // Call OTP send API
			/*
			 * try { String otpApiUrl = "http://localhost:8080/api/user/send-otp?email=" +
			 * email; ResponseEntity<String> otpResponse =
			 * restTemplate.postForEntity(otpApiUrl, null, String.class);
			 * System.out.println("Otp Respone : "+otpResponse); if
			 * (otpResponse.getStatusCode().is2xxSuccessful()) {
			 * System.out.println("OTP send successfully "); // String token =
			 * jwtUtil.generateToken(username); // // ResponseCookie jwtCookie =
			 * ResponseCookie.from("JWT_TOKEN", token) // .httpOnly(true) // .secure(true)
			 * // .path("/") // .maxAge(Duration.ofMinutes(30)) // .sameSite("Strict") //
			 * .build(); // // response.addHeader("Set-Cookie", jwtCookie.toString()); //
			 * return ResponseEntity.ok(Collections.singletonMap("message",
			 * "OTP sent to email.")); } else { return
			 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			 * .body(Collections.singletonMap("message", "Failed to send OTP")); }
			 * 
			 * } catch (Exception e) { return
			 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			 * .body(Collections.singletonMap("message", "Error calling OTP service: " +
			 * e.getMessage())); }
			 */
//	    }
//	    }
	    return ResponseEntity.ok(Collections.singletonMap("message", "User already exists"));
	}

	
	@PostMapping("/loginByEmailPassword")
	public ResponseEntity<?> loginByEmailPassword(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
		String email = loginRequest.get("email");
		String password = loginRequest.get("password");
		
		System.err.println(email);
		System.err.println(password);
		UserEntity user = null;
		user = userServiceDao.findByEmailPassword(email, password);
		System.out.println(user);
		// Validate user (replace with actual authentication logic)
		if (user != null) {
			String token = jwtUtil.generateToken(user.getUsername());
//			String username = user.getUsername();
			// Create an HTTP-only, Secure cookie for the JWT token
			ResponseCookie jwtCookie = ResponseCookie.from("JWT_TOKEN", token)
					.httpOnly(true)        // Prevent access from JavaScript
					.secure(true)          // Send only over HTTPS
					.path("/")             // Available for all endpoints
					//				.maxAge(Duration.ofHours(2)) // Cookie expiry
					.maxAge(Duration.ofMinutes(30)) // Cookie expiry
					.sameSite("Strict")    // Prevent CSRF attacks
					.build();
			

//	        // Create an HTTP-only, Secure cookie for the Username
//	        ResponseCookie userCookie = ResponseCookie.from("USERNAME", username)
//	                .httpOnly(true)
//	                .secure(true)
//	                .path("/")
//	                .maxAge(Duration.ofMinutes(30))
//	                .sameSite("Strict")
//	                .build();
	        
			// Add cookie to response header
			response.addHeader("Set-Cookie", jwtCookie.toString());
//			response.addHeader("Set-Cookie", userCookie.toString());
			
			return ResponseEntity.ok(Collections.singletonMap("message", "Login successful"));
		}
		return ResponseEntity.ok(Collections.singletonMap("message", "Login Failed"));
	}
	
	@PostMapping("/loginByEmailPin")
	public ResponseEntity<?> loginByEmailPin(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
		String email = loginRequest.get("email");
		Integer pin = Integer.parseInt(loginRequest.get("pin"));
		
		System.err.println(email);
		System.err.println(pin);
		UserEntity user = null;
		user = userServiceDao.findByEmailPin(email, pin);
		System.out.println(user);
		// Validate user (replace with actual authentication logic)
		if (user != null) {
			String token = jwtUtil.generateToken(user.getUsername());
//			String username = user.getUsername();
			// Create an HTTP-only, Secure cookie for the JWT token
			ResponseCookie jwtCookie = ResponseCookie.from("JWT_TOKEN", token)
					.httpOnly(true)        // Prevent access from JavaScript
					.secure(true)          // Send only over HTTPS
					.path("/")             // Available for all endpoints
					//.maxAge(Duration.ofHours(2)) // Cookie expiry
					.maxAge(Duration.ofMinutes(30)) // Cookie expiry
					.sameSite("Strict")    // Prevent CSRF attacks
					.build();
	        // Create an HTTP-only, Secure cookie for the Username
//	        ResponseCookie userCookie = ResponseCookie.from("USERNAME", username)
//	                .httpOnly(true)
//	                .secure(true)
//	                .path("/")
//	                .maxAge(Duration.ofMinutes(30))
//	                .sameSite("Strict")
//	                .build();
			// Add cookie to response header
			response.addHeader("Set-Cookie", jwtCookie.toString());
//			response.addHeader("Set-Cookie", userCookie.toString());
			
			return ResponseEntity.ok(Collections.singletonMap("message", "Login successful"));
		}
		return ResponseEntity.ok(Collections.singletonMap("message", "Login Failed"));
	}
	
	@PostMapping("/loginByEmail")
	public ResponseEntity<?> loginByEmail(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
		String email = loginRequest.get("email");
		
		System.err.println(email);
		UserEntity user = null;
		user = userServiceDao.findByEmail(email);
		System.out.println(user);
		// Validate user (replace with actual authentication logic)
		if (user != null) {
			String token = jwtUtil.generateToken(user.getUsername());
//			String username = user.getUsername();
			// Create an HTTP-only, Secure cookie for the JWT token
			ResponseCookie jwtCookie = ResponseCookie.from("JWT_TOKEN", token)
					.httpOnly(true)        // Prevent access from JavaScript
					.secure(true)          // Send only over HTTPS
					.path("/")             // Available for all endpoints
					//				.maxAge(Duration.ofHours(2)) // Cookie expiry
					.maxAge(Duration.ofMinutes(30)) // Cookie expiry
					.sameSite("Strict")    // Prevent CSRF attacks
					.build();
			

	        // Create an HTTP-only, Secure cookie for the Username
//	        ResponseCookie userCookie = ResponseCookie.from("USERNAME", username)
//	                .httpOnly(true)
//	                .secure(true)
//	                .path("/")
//	                .maxAge(Duration.ofMinutes(30))
//	                .sameSite("Strict")
//	                .build();
	        
			// Add cookie to response header
			response.addHeader("Set-Cookie", jwtCookie.toString());
//			response.addHeader("Set-Cookie", userCookie.toString());
			
			return ResponseEntity.ok(Collections.singletonMap("message", "Login successful"));
		}
		return ResponseEntity.ok(Collections.singletonMap("message", "Login Failed"));
	}
	
	
	@PostMapping("/loginByPhonePin")
	public ResponseEntity<?> loginByPhonePin(@RequestBody Map<String, String> loginRequest, HttpServletResponse response) {
	    String phone = loginRequest.get("phone");
	    Integer pin = Integer.parseInt(loginRequest.get("pin"));

	    System.err.println(phone);
	    System.err.println(pin);
	    UserEntity user = userServiceDao.findByPhonePin(phone, pin);
	    System.out.println(user);

	    // Validate user (replace with actual authentication logic)
	    if (user != null) {
	        String token = jwtUtil.generateToken(user.getUsername());
//	        String username = user.getUsername();

	        // Create an HTTP-only, Secure cookie for the JWT token
	        ResponseCookie jwtCookie = ResponseCookie.from("JWT_TOKEN", token)
	                .httpOnly(true)
	                .secure(true)
	                .path("/")
	                .maxAge(Duration.ofMinutes(30))
	                .sameSite("Strict")
	                .build();
//
//	        // Create an HTTP-only, Secure cookie for the Username
//	        ResponseCookie userCookie = ResponseCookie.from("USERNAME", username)
//	                .httpOnly(true)
//	                .secure(true)
//	                .path("/")
//	                .maxAge(Duration.ofMinutes(30))
//	                .sameSite("Strict")
//	                .build();

	        // Add cookies to response header
	        response.addHeader("Set-Cookie", jwtCookie.toString());
//	        response.addHeader("Set-Cookie", userCookie.toString());

	        return ResponseEntity.ok(Collections.singletonMap("message", "Login successful"));
	    }
	    return ResponseEntity.ok(Collections.singletonMap("message", "Login Failed"));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
	    jwtUtil.deleteTokenFromRequest(request, response);
	    System.out.println("logout is calling");
	    ResponseCookie clearCookie = ResponseCookie.from("JWT_TOKEN", "")
	            .httpOnly(true)
	            .secure(true)
	            .path("/")
	            .maxAge(Duration.ZERO) // Expire the cookie immediately
	            .sameSite("Strict")
	            .build();
	    ResponseCookie expiredSessionCookie = ResponseCookie.from("JSESSIONID", "")
	            .path("/")
	            .maxAge(0)
	            .httpOnly(true)
	            .secure(true)
	            .sameSite("Strict")
	            .build();

	    response.addHeader("Set-Cookie", expiredSessionCookie.toString());
	    response.addHeader("Set-Cookie", clearCookie.toString());

	    return ResponseEntity.ok(Collections.singletonMap("message", "Logged out successfully"));
	}

//	
//	@PostMapping("/logout")
//	public ResponseEntity<?> logout(HttpServletResponse response) {
//		System.out.println("logout is calling");
//	    ResponseCookie clearCookie = ResponseCookie	.from("JWT_TOKEN", "")
//	            .httpOnly(true)
//	            .secure(true)
//	            .path("/")
//	            .maxAge(Duration.ZERO) // Expire the cookie immediately
//	            .sameSite("Strict")
//	            .build();
//	    
//	    response.addHeader("Set-Cookie", clearCookie.toString());
//
//	    return ResponseEntity.ok(Collections.singletonMap("message", "Logged out successfully"));
//	}
	
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@PostMapping("/logoutGoogle")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
	    System.out.println("logout google  is calling");
	    jwtUtil.deleteTokenFromRequest(request, response);
	    if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
	        authorizedClientService.removeAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
	    }
	    
	    request.getSession().invalidate();
	    SecurityContextHolder.clearContext();

	    Cookie cookie = new Cookie("JSESSIONID", null);
	    cookie.setPath("/");
	    cookie.setHttpOnly(true);
	    cookie.setMaxAge(0);
	    response.addCookie(cookie);
	    return ResponseEntity.ok("Logged out successfully");
	}
	
	@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
	@PostMapping("/logoutGoogle_Auth")
	public ResponseEntity<String> logout_google(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		System.out.println("logout google Auth  is calling");
//		jwtUtil.deleteTokenFromRequest(request, response);
		if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
			authorizedClientService.removeAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
		}
		
		request.getSession().invalidate();
		SecurityContextHolder.clearContext();
		
		Cookie cookie = new Cookie("JSESSIONID", null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return ResponseEntity.ok("Logged out successfully");
	}




//    @PostMapping("/login")
//    public Map<String, String> login(@RequestBody Map<String, String> loginRequest) {
//    	String username = loginRequest.get("username");
//    	String password = loginRequest.get("password");
//    	User user = null;
//    	user = userServiceDao.findByUserNamePassword(username, password);
//    	// Validate user (replace with actual authentication logic)
//    	if (user != null) {
//    		String token = jwtUtil.generateToken(username);
//    		Map<String, String> response = new HashMap<>();
//    		response.put("token", token);
//    		return response;
//    	} else {
//    		throw new RuntimeException("Invalid credentials");
//    	}
//    }
}
