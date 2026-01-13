package com.proxy.notifications.errorNotifications.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.proxy.notifications.errorNotifications.entity.AuthRequest;
import com.proxy.notifications.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {
	private AuthenticationManager authenticationManager;
	private JwtUtils jwtUtils;
	private UserDetailsService userDetailsService;
	
	public AuthService(AuthenticationManager authenticationManager,JwtUtils jwtUtils, UserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
	}
	
	public String getRefreshToken(AuthRequest authRequest, HttpServletResponse response)
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
			return jwtCookie.toString();
		}else
		{
			return null;
		}
	}
	public String getAccessToken(String refreshToken){
		if(refreshToken != null)
		{
		UserDetails userDetails =  userDetailsService.loadUserByUsername(jwtUtils.extractUsername(refreshToken));
		String jwt = jwtUtils.createJwt(userDetails.getUsername(), 's');
		ResponseCookie jwtCookie = jwtUtils.setJwtCookie("accessJwt", jwt, 's');
		return jwtCookie.toString();
		}else
		{
			return null;
		}
	}
}
