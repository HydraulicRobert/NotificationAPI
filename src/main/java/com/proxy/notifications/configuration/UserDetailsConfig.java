package com.proxy.notifications.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import com.proxy.notifications.configuration.variable.Global;

@Configuration
public class UserDetailsConfig {
	@Bean
	public UserDetailsService users() {
		String strCfgPath = Global.getGstrcfgpath();
		String strFileName = Global.getGstruserlist();
		List<String[]> userListString = CfgInputOutput.getUserList(strCfgPath,strFileName);
		UserBuilder users = User.builder();
		List<UserDetails> userList = new ArrayList<>();
		for(int i = 0;i<userListString.size();i++)
		{
			userList.add(users
					.username(userListString.get(i)[0])
					.password(userListString.get(i)[1])
					.roles("USER")
					.build());
		}
		
		return new InMemoryUserDetailsManager(userList);
	}
}
