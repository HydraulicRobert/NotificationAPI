package com.proxy.notifications.errorNotifications.service;

import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.proxy.notifications.errorNotifications.entity.Settings;
import com.proxy.notifications.errorNotifications.repository.SettingsRepository;

@Service
public class SettingsService {

	SettingsRepository sttRep;
	CacheManager cacheMgr;
	
	public SettingsService(SettingsRepository sttRep, CacheManager cacheMgr) {
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
	
	public String checkSettingsTimestampEqualsCache() 
	{
			if(checkTimestampBigger6())
			{
				if (cacheMgr.getCache("sqlChkSet").get("normalKey",Boolean.class) == null)
				{
					cacheMgr.getCache("sqlChkSet").put("normalKey",false);
				}
				boolean bolChkSet = (cacheMgr.getCache("sqlChkSet").get("normalKey",Boolean.class));
				String strCacheString = cacheMgr.getCache("settingsTimestamp").get("normalKey", String.class);
				String strSQLDate = "";
				if (bolChkSet == false)
				{
					bolChkSet = true;
					cacheMgr.getCache("sqlChkSet").put("normalKey",bolChkSet);
					strSQLDate = findTop1().getLastChangeOn();
					bolChkSet = false;
					cacheMgr.getCache("sqlChkSet").put("normalKey",bolChkSet);
				}
				if ((strCacheString == null))
				{
					strCacheString = "placeholder";
					//cfgInputOutput.log(LocalDateTime.now(), 0, "settings timestamp is null. placeholder added: "+strCacheString);
				}
				if (!strCacheString.equals(strSQLDate))
				{
					//cfgInputOutput.log(LocalDateTime.now(), 0, strSQLDate+" S has been put into cache. doesn not equal "+strCacheString);
					strCacheString = strSQLDate;
					cacheMgr.getCache("settingsTimestamp").put("normalKey",strCacheString);
				}else 
				{
					//cfgInputOutput.log(LocalDateTime.now(), 0, strSQLDate+" S has not been put into cache. does equal "+strCacheString);
				}
			}
			return 
					cacheMgr.getCache("settingsTimestamp").get("normalKey", String.class);
	}
	
	public boolean checkTimestampBigger6() 
	{
		if ( cacheMgr.getCache("sixSecondTimestamp").get("longValue", Long.class) == null) {
			cacheMgr.getCache("sixSecondTimestamp").put("longValue", System.currentTimeMillis()/1000L);
			return true;
		}
		long lngTmp = cacheMgr.getCache("sixSecondTimestamp").get("longValue", Long.class);
		if (Long.toString(lngTmp).trim().isEmpty()) {
			lngTmp = System.currentTimeMillis()/1000L;
		}
		long i = System.currentTimeMillis()/1000L-lngTmp;
		//cfgInputOutput.log(LocalDateTime.now(), 0,"checkTimestampSmallerthan "+i);
		if (i >= 6) {
			cacheMgr.getCache("sixSecondTimestamp").clear();
			cacheMgr.getCache("sixSecondTimestamp").put("longValue", System.currentTimeMillis()/1000L);
			//cfgInputOutput.log(LocalDateTime.now(), 0,"refresh timestamp refreshed");
			//System.out.println(cacheMgr.getCache("sixSecondTimestamp").get("longValue", Long.class));
			return true;
		}
		return false;
	}
	
}
