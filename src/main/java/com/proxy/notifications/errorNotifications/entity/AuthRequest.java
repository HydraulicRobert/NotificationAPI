package com.proxy.notifications.errorNotifications.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthRequest {
	private String username;
    private String password;
    
    public AuthRequest() {
		super();
	}

	public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUserName() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    @JsonProperty(value = "username")
    public void setUserName(String username) {
        this.username = username;
    }
    @JsonProperty(value = "password")
    public void setPassword(String password) {
        this.password = password;
    }
}
