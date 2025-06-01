package com.tap.com;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtUtil {

    private static final String SECRET_KEY_PROPERTY = "JWT_SECRET";
    private static final String TOKEN_KEY_PREFIX = "JWT_TOKEN:"; // Key prefix for Redis
    private static final long TOKEN_EXPIRATION_TIME = 1800000; // 1 minute (in ms)

    @Autowired
    private StringRedisTemplate redisTemplate; // Redis integration

    /**
     * Generates a JWT token and stores it in Redis.
     * @param subject The subject (username or user ID).
     * @return JWT token string.
     */
    public String generateToken(String subject) {
        String storedKey = redisTemplate.opsForValue().get(SECRET_KEY_PROPERTY);
        System.out.println(TOKEN_KEY_PREFIX + subject);
        String storedToken = redisTemplate.opsForValue().get(TOKEN_KEY_PREFIX + subject);
        System.out.println("storedKey : "+storedKey);
        if(storedToken == null) {
        	System.out.println("storedToken expired : ");
        }
        else {
        	System.out.println("storedToken : "+ storedToken);
        }
        // If a valid token already exists, return it
        if (storedToken != null && verifyToken(storedToken) != null) {
            System.out.println("Returning existing valid token.");
            return storedToken;
        }

        // Generate a new secret key if not already set
        if (storedKey == null) {
            Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            storedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

            // Store secret key in Redis (without expiration)
            redisTemplate.opsForValue().set(SECRET_KEY_PROPERTY, storedKey);
        }
        
        storedKey = redisTemplate.opsForValue().get(SECRET_KEY_PROPERTY);
        // Retrieve the secret key
        Key signingKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(storedKey));

        // Generate new token
        storedToken = Jwts.builder()
                .setSubject(subject)  // Set subject (user info)
                .setIssuer("MyApp")  // Token issuer
                .setIssuedAt(new Date())  // Issue time
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME)) // Expiration
                .signWith(signingKey, SignatureAlgorithm.HS256)  // Sign token
                .compact();

        // Store the token in Redis with expiration time
        redisTemplate.opsForValue().set(TOKEN_KEY_PREFIX + subject, storedToken, TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);
        System.out.println(TOKEN_KEY_PREFIX + subject);
        System.out.println("creating new token.");

        return storedToken;
    } 

    /**
     * Validates and parses a JWT token.
     * @param token The JWT token.
     * @return Claims if valid, null if invalid or expired.
     */
    public Claims verifyToken(String token) {
        String storedKey = redisTemplate.opsForValue().get(SECRET_KEY_PROPERTY);

        if (storedKey == null) {
            throw new IllegalStateException("JWT secret key is not set.");
        }

        Key signingKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(storedKey));
        Jws<Claims> parsedToken = null;
        try {
        	 if (token == null || token.trim().isEmpty()) {
        	        return null; // Skip verification for empty token
        	    }
             parsedToken = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
             System.out.println(parsedToken.getBody());
            return parsedToken.getBody(); // Return claims if valid
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
//            redisTemplate.delete(TOKEN_KEY_PREFIX + parsedToken.getBody().getSubject()); // Remove expired token
        } catch (JwtException e) {
            System.out.println("Invalid token: " + e.getMessage());
        } 
        return null; // Return null if token is invalid or expired
    }
    
    public boolean validateToken(String token, UserDetails userDetails) {
        Claims claims = verifyToken(token);
 
        if (claims == null) {
            return false; // Token is invalid or expired
        }

        String usernameFromToken = claims.getSubject();
        return usernameFromToken.equals(userDetails.getUsername());
    }
    
    public String extractUsername(String token) {
        Claims claims = verifyToken(token);
        return (claims != null) ? claims.getSubject() : null;
    }

	public Authentication getAuthentication(String token, UserDetails userDetails) {
	    return new UsernamePasswordAuthenticationToken(
	        userDetails, null, userDetails.getAuthorities()
	    );
	}
	public void deleteTokenFromRequest(HttpServletRequest request, HttpServletResponse response) {
	    String token = extractTokenFromCookie(request);
	    System.out.println("in delete section token is :"+token);
	    if (token != null) {
	        String username = extractUsername(token);
	        if (username != null) {
	            redisTemplate.delete(TOKEN_KEY_PREFIX + username);
	            System.out.println("Token deleted for user: " + username);
	        }
	    }

	    // Expire the cookies in the response
	    ResponseCookie expiredJwtCookie = ResponseCookie.from("JWT_TOKEN", "")
	            .httpOnly(true)
	            .secure(true)
	            .path("/")
	            .maxAge(0) // Expire immediately
	            .sameSite("Strict")
	            .build();

//	    ResponseCookie expiredUserCookie = ResponseCookie.from("USERNAME", "")
//	            .httpOnly(true)
//	            .secure(true)
//	            .path("/")
//	            .maxAge(0) // Expire immediately
//	            .sameSite("Strict")
//	            .build();

	    response.addHeader("Set-Cookie", expiredJwtCookie.toString());
//	    response.addHeader("Set-Cookie", expiredUserCookie.toString());
	}
	
	// Extract JWT token from cookies
		 private String extractTokenFromCookie(HttpServletRequest request) {
		     if (request.getCookies() != null) {
		         for (Cookie cookie : request.getCookies()) {
		        	 System.out.println("Store cookie name :"+cookie.getName());
		             if ("JWT_TOKEN".equals(cookie.getName())) {
		                 return cookie.getValue();
		             }
		         }
		     }
		     return null;
		 }


}
