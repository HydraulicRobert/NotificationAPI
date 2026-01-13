package com.proxy.notifications.errorNotifications.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.proxy.notifications.errorNotifications.Controller.SettingsController;
import com.proxy.notifications.errorNotifications.entity.Notification;
import com.proxy.notifications.errorNotifications.repository.NotificationRepository;

@Service
public class NotificationService {
	NotificationRepository notRep;
	SettingsService sttSrvc;
	CacheManager cacheMgr;
	
	public NotificationService(NotificationRepository notRep, SettingsService sttSrvc, CacheManager cacheMgr) {
		super();
		this.notRep = notRep;
		this.sttSrvc = sttSrvc;
		this.cacheMgr = cacheMgr;
	}
	
	public List<Notification> getNotifications()
	{
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
			iterNotification = notRep.findAllByOrderByStartDateDesc();
			allNotifications = iteratorToListNotification(iterNotification,allNotifications);
			cacheMgr.getCache("notificationList").put("normalKey", allNotifications);
			bolChkNotAllBottom = false;
			cacheMgr.getCache("sqlChkNotAllBottom").put("normalKey",bolChkNotAllTop);
		}
		return allNotifications;
	}
	
	public String getLatestStartDateCompareCache() {
		if (cacheMgr.getCache("sqlChkNotTop1").get("normalKey",Boolean.class) == null)
		{
			cacheMgr.getCache("sqlChkNotTop1").put("normalKey",false);
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
		}
		if (!strCacheString.equals(strSQLDate))
		{
			strCacheString = strSQLDate;
			cacheMgr.getCache("mostCurrentNotificationTimestamp").put("normalKey",strCacheString);
		}else
		{
		}
		return cacheMgr.getCache("mostCurrentNotificationTimestamp").get("normalKey", String.class);
	}
	public List<Notification> iteratorToListNotification(Iterable<Notification> tmpIterNotification,List<Notification> tmpAllNotifications)
	{

		tmpAllNotifications = new ArrayList<>();
		for (Notification curNotification: tmpIterNotification) {
			tmpAllNotifications.add(curNotification);
		}
		return tmpAllNotifications;
	} 
}
