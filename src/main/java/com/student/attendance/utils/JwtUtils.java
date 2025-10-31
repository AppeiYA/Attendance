package com.student.attendance.utils;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.student.attendance.dtos.VerifiedUserDto;
import com.student.attendance.models.UsersEntity.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtils {
	@Value("${app.jwt.secret}")
	private String SECRET_KEY;
	@Value("${app.jwt.expiration}")
	private long EXPIRATION_TIME;
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}
	
	public String generateToken(String email, UUID user_id, UserRole role) {
		return Jwts.builder()
				.setSubject("user-auth")
				.claim("role", role.name())
				.claim("email", email)
				.claim("user_id", user_id.toString())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public VerifiedUserDto extractUser(String token) {
		Claims claim = Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return new VerifiedUserDto(
				UserRole.valueOf(claim.get("role", String.class)),
//				claim.get("role", UserRole.class), 
				claim.get("email", String.class), 
				UUID.fromString(claim.get("user_id", String.class))
				);
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
			return true;
		}catch(JwtException err) {
			return false;
		}
	}
}
