package com.proxy.notifications.errorNotifications.Controller;


import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.notifications.configuration.CfgInputOutput;
import com.proxy.notifications.errorNotifications.entity.Notification;
import com.proxy.notifications.errorNotifications.repository.NotificationRepository;

@RestController
public class NotificationController {
	NotificationRepository notRep;
	SettingsController sttCtr;
	CacheManager cacheMgr;
	
	public NotificationController(NotificationRepository notRep, SettingsController sttCtr, CacheManager cacheMgr) {
		super();
		this.notRep = notRep;
		this.sttCtr = sttCtr;
		this.cacheMgr = cacheMgr;
	}

	public List<Notification> iteratorToListNotification(Iterable<Notification> tmpIterNotification,List<Notification> tmpAllNotifications)
	{

		tmpAllNotifications = new ArrayList<>();
		for (Notification curNotification: tmpIterNotification) {
			tmpAllNotifications.add(curNotification);
		}
		return tmpAllNotifications;
	} 
	
	@GetMapping("/set")
	public String checkSettingsTimestampEqualsCache(@RequestHeader(value = "username", required = false) String strUsername, @RequestHeader(value = "password", required = false) String strPassword) 
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
					strSQLDate = sttCtr.findTop1().getLastChangeOn();
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
			return cacheMgr.getCache("settingsTimestamp").get("normalKey", String.class);
	}
	
	@GetMapping("/notTop1")
	public String checkMostCurrentNotificationTimestampEqualsCache(@RequestHeader(value = "username", required = false) String strUsername, @RequestHeader(value = "password", required = false) String strPassword) 
	{
			if (cacheMgr.getCache("sqlChkNotTop1").get("normalKey",Boolean.class) == null)
			{
				cacheMgr.getCache("sqlChkNotTop1").put("normalKey",false);
				//cfgInputOutput.log(LocalDateTime.now(), 0, "cache top 1 boolean null. set to false");				
			}
			boolean bolChkNotTop1 = cacheMgr.getCache("sqlChkNotTop1").get("normalKey",Boolean.class);
			String strCacheString = cacheMgr.getCache("mostCurrentNotificationTimestamp").get("normalKey", String.class);
			String strSQLDate = "";
			if (bolChkNotTop1 == false)
			{
				bolChkNotTop1 = true;
				cacheMgr.getCache("sqlChkSet").put("normalKey",bolChkNotTop1);
				strSQLDate = notRep.findTop1ByOrderByStartDateDesc().getStartDate();
				bolChkNotTop1 = false;
				cacheMgr.getCache("sqlChkSet").put("normalKey",bolChkNotTop1);
			}
			if (strCacheString == null)
			{
				strCacheString = "placeholder";
				//System.out.println("notification timestamp is null. placeholder added: "+strCacheString);
			}
			if (!strCacheString.equals(strSQLDate))
			{
				//cfgInputOutput.log(LocalDateTime.now(), 0,strSQLDate+" N1 has been put into cache. doesn not equal "+strCacheString);				
				strCacheString = strSQLDate;
				//System.out.println("unequal settings dates. putting new into cache");
				cacheMgr.getCache("mostCurrentNotificationTimestamp").put("normalKey",strCacheString);
				//System.out.println("settings timestamp is: "+tmpString);
			}else
			{
				//cfgInputOutput.log(LocalDateTime.now(), 0,strSQLDate+" N1 has not been put into cache. does equal "+strCacheString);				
			}
			//checkTimestampBigger6();
	//		System.out.println(System.getProperty("user.dir"));
			return cacheMgr.getCache("mostCurrentNotificationTimestamp").get("normalKey", String.class);
		/*}else
		{
			return null;
		}*/
	}
	@GetMapping("/notFin")
	public List<Notification> getNotificationsWhenFinished()
	{
		return cacheMgr.getCache("notificationList").get("normalKey", ArrayList.class);
	}
	

	
	@GetMapping("/string")
	public String getString()
	{
		return "hello";
	}
	public List<Notification> getnotificationsJWT()
	{
		return getNotifications(null, null);
	}

	@GetMapping("/notAll")
	public List<Notification> getNotifications(@RequestHeader(value = "username", required = false) String strUsername, @RequestHeader(value = "password", required = false) String strPassword) 
	{
		/*if ((strUsername == null) ||(strPassword == null)) {
			return null;
		}
		if (checkUserExistsCache(strUsername, strPassword))
		{*/
			if (cacheMgr.getCache("sqlChkNotAllTop").get("normalKey",Boolean.class) == null)
			{
				cacheMgr.getCache("sqlChkNotAllTop").put("normalKey",false);
				//cfgInputOutput.log(LocalDateTime.now(), 0,"cache all Not top boolean null. set to false");				
			}
			if (cacheMgr.getCache("sqlChkNotAllBottom").get("normalKey",Boolean.class) == null)
			{
				cacheMgr.getCache("sqlChkNotAllBottom").put("normalKey",false);
				//cfgInputOutput.log(LocalDateTime.now(), 0,"cache all Not bottom boolean null. set to false");
			}
			boolean bolChkNotAllTop = cacheMgr.getCache("sqlChkNotAllTop").get("normalKey",Boolean.class);
			boolean bolChkNotAllBottom = cacheMgr.getCache("sqlChkNotAllBottom").get("normalKey",Boolean.class);
			Iterable<Notification> iterNotification;
			List<Notification> allNotifications = cacheMgr.getCache("notificationList").get("normalKey", ArrayList.class);
			if ((allNotifications == null) && (!bolChkNotAllTop)) {
				//System.out.println("nots null. filling");
				bolChkNotAllTop = true;
				cacheMgr.getCache("sqlChkNotAllTop").put("normalKey",bolChkNotAllTop);
				iterNotification = notRep.findAllByOrderByStartDateDesc();
				allNotifications = iteratorToListNotification(iterNotification,allNotifications);
				cacheMgr.getCache("notificationList").put("normalKey", allNotifications);
				bolChkNotAllTop = false;
				cacheMgr.getCache("sqlChkNotAllTop").put("normalKey",bolChkNotAllTop);
			}else {
				allNotifications = cacheMgr.getCache("notificationList").get("normalKey", ArrayList.class);
			}
			String strMostCurr = cacheMgr.getCache("mostCurrentNotificationTimestamp").get("normalKey", String.class) == null? "a":cacheMgr.getCache("mostCurrentNotificationTimestamp").get("normalKey", String.class).toString();
			String strMostCurrPrev = cacheMgr.getCache("mostCurrentNotificationTimestampAll").get("normalKey", String.class);// == null? "b":cacheMgr.getCache("mostCurrentNotificationTimestampAll").get("normalKey", String.class).toString();
			if((!bolChkNotAllBottom) &&
				(!strMostCurr.equals(strMostCurrPrev) &&
				(!bolChkNotAllBottom)))
			{
				cacheMgr.getCache("mostCurrentNotificationTimestampAll").put("normalKey", strMostCurr);
				bolChkNotAllBottom = true;
				cacheMgr.getCache("sqlChkNotAllBottom").put("normalKey",bolChkNotAllTop);
				//cfgInputOutput.log(LocalDateTime.now(), 0,"cache noti not equal db");
				iterNotification = notRep.findAllByOrderByStartDateDesc();
				//allNotifications.clear();
				allNotifications = iteratorToListNotification(iterNotification,allNotifications);
				cacheMgr.getCache("notificationList").put("normalKey", allNotifications);
				bolChkNotAllBottom = false;
				cacheMgr.getCache("sqlChkNotAllBottom").put("normalKey",bolChkNotAllTop);
			}
			return allNotifications;
		/*}else {
			return null;
		}*/
	}
	@GetMapping("/chk6")
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
	public boolean checkUserListExistsCache() {
		try {
			if (cacheMgr.getCache("userList").get("normalKey") == null) {
				cacheMgr.getCache("userList").put("normalKey",CfgInputOutput.getUserList(Paths.get(System.getProperty("user.dir"),"configuration").toString(),"userlist.csv"));
				return true;
			}else
			{
				return true;
			}
		}catch(Exception E) {
			return false;
		}
	}
	//public boolean checkUserExistsCache(String username, String password) {
	@GetMapping("/check")
	public boolean checkUserExistsCache(@RequestHeader(value = "username", required = false) String username, @RequestHeader(value = "password", required = false) String password) {	
		if (checkUserListExistsCache())
		{
			List<String[]> stlUserList = cacheMgr.getCache("userList").get("normalKey",ArrayList.class);
			if (stlUserList == null) {
				return false;
			}
			String[] strUserList; 
			for(int i = 0;i<stlUserList.size();i++)
			{
				strUserList = stlUserList.get(i);
				if (strUserList[0].equals(username))
				{
					
					if (BCrypt.checkpw(password, strUserList[1]))
					{
						//cfgInputOutput.log(LocalDateTime.now(), 0,"successful login detected");
						return true;
					}
					//return true;
				}
			}
		}else
		{
			//cfgInputOutput.log(LocalDateTime.now(), 0,"bad login detected");
		}
		return false;
	}
	
}
