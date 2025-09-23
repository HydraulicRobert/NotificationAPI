package com.proxy.notifications.filter;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.proxy.notifications.configuration.CfgInputOutput;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PostFilterLogFilter extends OncePerRequestFilter{
	@Order(Ordered.LOWEST_PRECEDENCE)
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		CfgInputOutput.logRequests(request, SecurityContextHolder.getContext().getAuthentication());
		filterChain.doFilter(request, response);
	}
	

}
