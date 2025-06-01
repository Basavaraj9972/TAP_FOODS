package com.tap.com;

import io.jsonwebtoken.Claims;

public class TokenTesting {

	public static void main(String[] args) {
		JwtUtil jwtUtil = new JwtUtil();
		String token = 	jwtUtil.generateToken("Basavaraj");
		Claims claims = jwtUtil.verifyToken(token);
         String chekingToken = claims != null ? "Valid Token for " + claims.getSubject()+" " +claims.getExpiration()+" "+ claims.getIssuedAt() : "Invalid or Expired Token!";
         System.out.println(chekingToken);
	}

}
