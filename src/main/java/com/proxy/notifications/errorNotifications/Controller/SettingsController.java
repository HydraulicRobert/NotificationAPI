package com.proxy.notifications.errorNotifications.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.notifications.errorNotifications.entity.Settings;
import com.proxy.notifications.errorNotifications.repository.SettingsRepository;
import com.proxy.notifications.errorNotifications.service.SettingsService;


@RestController
public class SettingsController {

	SettingsService sttSrvc;
	public SettingsController(SettingsService sttSrvc) {
		super();
		this.sttSrvc = sttSrvc;
	}
	public Settings findTop1() {
		return sttSrvc.findTop1();
	}
	public Iterable<Settings> findAll() {
		return sttSrvc.findAll();
	}
	@GetMapping("/settings/last-modified")
	public ResponseEntity<String> checkSettingsTimestampEqualsCache()
	{
		return ResponseEntity.ok()
				.header("last modified", "one")
				.body(sttSrvc.checkSettingsTimestampEqualsCache());
	}
}
