package com.proxy.notifications.errorNotifications.Controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.http.ResponseEntity;

import com.nimbusds.jose.proc.SecurityContext;
import com.proxy.notifications.configuration.CfgInputOutput;
import com.proxy.notifications.errorNotifications.entity.AuthRequest;
import com.proxy.notifications.errorNotifications.entity.Notification;
import com.proxy.notifications.jwt.JwtUtils;
import com.proxy.notifications.service.TokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthController {
	
	private AuthenticationManager authenticationManager;
	private JwtUtils jwtUtils;
	private UserDetailsService userDetailsService;
	
	public AuthController(AuthenticationManager authenticationManager,JwtUtils jwtUtils, UserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
	}
	@PostMapping("/auth/login")
	public ResponseEntity<String> jwtRefreshTokenReturn(@RequestBody(required = false) AuthRequest authRequest, HttpServletResponse response)
	{
		if(authRequest != null)
		{
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							authRequest.getUserName(),
							authRequest.getPassword()
							)
					);
			UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUserName());
			String jwt = jwtUtils.createJwt(userDetails.getUsername(), 'l');
			ResponseCookie jwtCookie = jwtUtils.setJwtCookie("refreshJwt", jwt, 'l');
			return ResponseEntity.ok()
					.header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
					.body("ok");
		}else
		{
			return null;
		}
	}
	@PostMapping("/auth/refresh")
	public ResponseEntity<String> jwtAccessTokenReturn(@CookieValue(name = "refreshJwt", required = false) String refreshJwt)
	{
		if(refreshJwt != null)
		{
		UserDetails userDetails =  userDetailsService.loadUserByUsername(jwtUtils.extractUsername(refreshJwt));
		String jwt = jwtUtils.createJwt(userDetails.getUsername(), 's');
		ResponseCookie jwtCookie = jwtUtils.setJwtCookie("accessJwt", jwt, 's');
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.body("ok");
		}else
		{
			return null;
		}
	}
}

