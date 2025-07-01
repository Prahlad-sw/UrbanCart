package com.DiffyStore.MyDiffyStore.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.DiffyStore.MyDiffyStore.config.UserInfoUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	public static final String SECRET="ThisIsASecretKeyThisIsASecretKeyThisIsASecretKeyThisIsASecretKey";
	public static final long JWT_TOKEN_VALIDITY=900000;
	
	public String extractUsername(String token) {
		Claims claims=extractAllClaims(token);
		return claims.getSubject();
	}
	
	public Date extractExpiration(String token) {
		Claims claims=extractAllClaims(token);
		return claims.getExpiration();
	}
	
	public <T> T extractClaim(String token,Function<Claims,T> claimResolver) {
		final Claims claims=extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	

	@SuppressWarnings("deprecation")
	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		return null;	
	}
	
	public Boolean validateToken(String token,UserInfoUserDetails user) {
		String username=extractUsername(token);
		Date expiry=extractExpiration(token);
		return (username.equals(user.getUsername()) && expiry.after(new Date(System.currentTimeMillis())));
	}
	//test
	   public String generateToken(String username) {
	        Map<String, Object> claims = new HashMap<>();
	        return createToken(claims, username);
	    }

	    @SuppressWarnings("deprecation")
		private String createToken(Map<String, Object> claims, String subject) {
	        return Jwts.builder().signWith(SignatureAlgorithm.HS256,getSigningKey())
	        		.addClaims(new HashMap<>())
	        		.setSubject(subject)
	        		.setIssuedAt(new Date(System.currentTimeMillis()))
	        		.setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
	        		.compact();
	    }
	    private Key getSigningKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }
	//test
//	private String createToken(Map<String , Object> claims , String userName) {
//		return null;
//	}
	
	
	   
	
}
