package com.proxy.notifications.errorNotifications.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.notifications.errorNotifications.entity.Settings;
import com.proxy.notifications.errorNotifications.repository.SettingsRepository;


@RestController
public class SettingsController {

	SettingsRepository sttRep;
	CacheManager cacheMgr;
	
	public SettingsController(SettingsRepository sttRep, CacheManager cacheMgr) {
		super();
		this.sttRep = sttRep;
		this.cacheMgr = cacheMgr;
	}
	public Settings findTop1() {
		return sttRep.findTop1By();
	}
	public Iterable<Settings> findAll() {
		//cacheMgr.getCache("settingsList").put("normalKey", sttRep.findAll()); 
		return sttRep.findAll();
	}
	
}
