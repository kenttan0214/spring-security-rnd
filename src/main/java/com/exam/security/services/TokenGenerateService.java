package com.exam.security.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.exam.security.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Service
public class TokenGenerateService {
	//Based 64 encoded secret
	private final String privateKey = "RXhAbUZ5MTYoUGFTc1dvUmQp";
	
	public Claims parseToken(String token) {
		Claims claims  = null;
		
		try {
			claims = Jwts.parser()
	                .setSigningKey(privateKey)
	                .parseClaimsJws(token)
	                .getBody();
		} catch (SignatureException e) {
			// don't trust the JWT!
		}
        
		/*String username = Jwts.parser()
                .setSigningKey(privateKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();*/
		
        return claims;
    }

	public String createToken(User userDetails) {
		
		return Jwts.builder()
				.setIssuedAt(new Date())
				.setSubject(userDetails.getUsername())
				.signWith(SignatureAlgorithm.HS512, privateKey)
				.compact();
	}
}
