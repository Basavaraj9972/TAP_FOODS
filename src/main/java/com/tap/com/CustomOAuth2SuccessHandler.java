package com.tap.com;

import java.io.IOException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.tap.com.model.UserEntity;
import com.tap.com.serviceInf.UserServiceDao;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserServiceDao userServiceDao;
    private final JwtUtil jwtUtil;

    public CustomOAuth2SuccessHandler(UserServiceDao userServiceDao, JwtUtil jwtUtil) {
        this.userServiceDao = userServiceDao;
        this.jwtUtil = jwtUtil;
    }
    @Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        // Extract user details from OAuth2 authentication token
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String email = oauthToken.getPrincipal().getAttribute("email");
        String name = oauthToken.getPrincipal().getAttribute("name");
        String picture = oauthToken.getPrincipal().getAttribute("picture");
        
        // Optionally, log the details
        System.out.println("Google email: " + email);
        System.out.println("Google name: " + name);
        System.out.println("Google picture: " + picture);
        
        String source = null;

        // Read cookie to detect source
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("oauth_source".equals(cookie.getName())) {
                    source = cookie.getValue();
                    break;
                }
            }
        }
        
        // Check if the user exists in your database
        UserEntity user  = null;
        user = userServiceDao.findByEmail(email);


        // ==== Handle SignUp ====
        if ("signup".equals(source)) {
        	if (user == null) {
        	    user = new UserEntity(name, email);
        	    userServiceDao.saveUser(user);
        	} else {
        	    // Remove the OAuth2 client
//        	    if (authentication instanceof OAuth2AuthenticationToken oauthToken1) {
//        	        authorizedClientService.removeAuthorizedClient(
//        	            oauthToken1.getAuthorizedClientRegistrationId(), 
//        	            oauthToken1.getName()
//        	        );
//        	    }

        	    // Invalidate session
//        	    request.getSession().invalidate();

        	    // Clear security context
//        	    SecurityContextHolder.clearContext();

        	    // Clear JSESSIONID cookie
//        	    Cookie jsessionCookie = new Cookie("JSESSIONID", null);
//        	    jsessionCookie.setPath("/");
//        	    jsessionCookie.setHttpOnly(true);
//        	    jsessionCookie.setMaxAge(0);
//        	    response.addCookie(jsessionCookie);

        	    // Clear oauth_source cookie too (optional but better)
//        	    Cookie sourceCookie = new Cookie("oauth_source", null);
//        	    sourceCookie.setPath("/");
//        	    sourceCookie.setMaxAge(0);
//        	    response.addCookie(sourceCookie);

        	    // Nullify authentication
//        	    authentication = null;
//        	    oauthToken = null;

        	    // Set failure message and redirect
        	    request.getSession().setAttribute("auth_message", "UserAlreadyExists");
        	    getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/signin");
        	    return;
        	}

            
//            Cookie messageCookie = new Cookie("auth_message", "registration successfull");
//            messageCookie.setPath("/");
//            messageCookie.setMaxAge(60); // seconds
//            response.addCookie(messageCookie);
//            authentication = null;
//            oauthToken = null;
            request.getSession().setAttribute("auth_message", "registration successfull");
            getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/signin");


            // After successful sign-up, redirect to sign-in
//            getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/signin?success=registered");
            return;
        } else  if ("signin".equals(source)) {
        	  // ==== Handle SignIn ====
            if (user == null) {
                // User not found
//            	Cookie messageCookie = new Cookie("auth_message", "InvalidUser");
//            	messageCookie.setPath("/");
//            	messageCookie.setMaxAge(60); // seconds
//            	response.addCookie(messageCookie);
            	request.getSession().setAttribute("auth_message", "InvalidUser");
            	getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/signin");

//                getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/signin?error=InvalidUser");
                return;
            } else {
            	
            	if (user != null) {
            		// User is valid, generate JWT token using user's username (or any unique identifier)
            		String token = jwtUtil.generateToken(user.getUsername());
            		
            		// Create a secure HTTP-only cookie to store the JWT token
            		ResponseCookie jwtCookie = ResponseCookie.from("JWT_TOKEN", token)
            				.httpOnly(true)                    // Prevent JavaScript access
            				.secure(true)                      // Only send over HTTPS
            				.path("/")                         // Available to all endpoints
            				.maxAge(Duration.ofMinutes(60))    // Set expiry (adjust as needed)
            				.sameSite("Strict")                // Mitigate CSRF
            				.build();
            		
            		// Add the cookie to the response
            		response.addHeader("Set-Cookie", jwtCookie.toString());
            	}
//            	oauthToken = null;
//            	authentication = null;
            	// Redirect to your dashboard after login
            	getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/dashboard");
              }
            }
        }
}
