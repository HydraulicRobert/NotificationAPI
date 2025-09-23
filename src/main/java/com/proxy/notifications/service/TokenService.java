package com.proxy.notifications.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.proxy.notifications.jwt.JwtUtils;

import io.jsonwebtoken.Jwts;

@Service
public class TokenService {
	
	
	public TokenService() {
	}


	public String generateJwtToken(String name)
	{
		String token = Jwts.builder()
				.header()
				.add("loginType", "OAuth")
				.and()
			.issuedAt(Date.from(Instant.ofEpochSecond(1466796822L)))
			.issuer("notificationProxy")
			.subject(name)
			.compact();
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
}
