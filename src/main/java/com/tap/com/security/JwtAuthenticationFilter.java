package com.tap.com.security;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tap.com.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            chain.doFilter(request, response);
//            return;
//        }

//        String token = authHeader.substring(7);
    	  String token = extractTokenFromCookie(request);
    	  System.err.println(token);
    	  if (token == null || token.trim().isEmpty()) {
    		  chain.doFilter(request, response);
    		    return;
    		}
    	  String username = null;
          username = jwtUtil.extractUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("UserDetails :"+userDetails);
            if (jwtUtil.validateToken(token, userDetails)) {
                SecurityContextHolder.getContext().setAuthentication(jwtUtil.getAuthentication(token, userDetails));
            }
        }
        chain.doFilter(request, response);
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
