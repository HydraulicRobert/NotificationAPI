package com.proxy.notifications.errorNotifications.Controller;


import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.notifications.configuration.CfgInputOutput;
import com.proxy.notifications.errorNotifications.entity.Notification;
import com.proxy.notifications.errorNotifications.repository.NotificationRepository;
import com.proxy.notifications.errorNotifications.service.NotificationService;

@RestController
public class NotificationController {
	NotificationService notSrvc;
	
	public NotificationController(NotificationService notSrvc) {
		super();
		this.notSrvc = notSrvc;
	}

	@GetMapping("/notifications/latest-startdate")
	public ResponseEntity<String> checkMostCurrentNotificationTimestampEqualsCache() 
	{
		return ResponseEntity.ok()
				.header("latest-startdate", "one")
				.body(notSrvc.getLatestStartDateCompareCache());
	}
		
	@GetMapping("/string")
	public String getString()
	{
		return "hello";
	}
	public ResponseEntity<List<Notification>> getnotificationsJWT()
	{
		return getNotifications(null, null);
	}

	@GetMapping("/notifications")
	public ResponseEntity<List<Notification>> getNotifications(@RequestHeader(value = "username", required = false) String strUsername, @RequestHeader(value = "password", required = false) String strPassword) 
	{
		return ResponseEntity.ok()
				.header("notifications", "all")
				.body(
				notSrvc.getNotifications());
	}
	
}
