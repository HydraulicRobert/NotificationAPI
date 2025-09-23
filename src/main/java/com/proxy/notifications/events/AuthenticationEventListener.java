package com.proxy.notifications.events;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.proxy.notifications.configuration.CfgInputOutput;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {

	@Autowired
    HttpServletRequest httpReq;
   @Override
   public void onApplicationEvent(AbstractAuthenticationEvent authenticationEvent) {
	  if (authenticationEvent instanceof InteractiveAuthenticationSuccessEvent) {
         return;
      }
      Authentication authentication = authenticationEvent.getAuthentication();
      try 
      {
    	 CfgInputOutput.logRequests(httpReq, authentication);
	      
	      
      }catch (Exception e) {
		// TODO: handle exception
	}
   }

}
