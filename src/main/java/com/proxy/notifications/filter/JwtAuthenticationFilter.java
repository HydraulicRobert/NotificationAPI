package com.proxy.notifications.filter;

import java.io.IOException;

import org.hibernate.annotations.Filter;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.proxy.notifications.configuration.CfgInputOutput;
import com.proxy.notifications.jwt.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
private JwtUtils jwtUtils;
private UserDetailsService userDetailsService;
public JwtAuthenticationFilter(JwtUtils jwtUtils, @Lazy UserDetailsService userDetailsService) {
	this.jwtUtils = jwtUtils;
	this.userDetailsService = userDetailsService;
}
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
	// TODO Auto-generated method stub
	String header = request.getHeader("Authorization");
	String username = null;
	String jwt = null;
//	Authentication authentication;
	if(header != null && header.startsWith("Bearer ey"))
	{
		jwt = header.substring(7);
		username = jwtUtils.extractUsername(jwt);
	}
	if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
	{
		if(jwtUtils.validateToken(jwt, username))
		{
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(jwt, null, userDetails.getAuthorities());
			auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
	}
	filterChain.doFilter(request, response);
}


}
