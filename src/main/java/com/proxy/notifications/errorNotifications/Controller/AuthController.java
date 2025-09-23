package com.proxy.notifications.errorNotifications.Controller;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	@PostMapping("/login")
	public ResponseEntity<String> jwtTokenReturn(@RequestBody(required = false) AuthRequest authRequest)
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
			String jwt = jwtUtils.createJwt(userDetails.getUsername());
			return ResponseEntity.ok(jwt);
		}else
		{
			return null;
		}
	}
}
