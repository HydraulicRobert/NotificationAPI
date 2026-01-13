package com.proxy.notifications.errorNotifications.Controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.cache.CacheManager;

import com.proxy.notifications.errorNotifications.entity.Notification;
import com.proxy.notifications.errorNotifications.repository.NotificationRepository;
import com.proxy.notifications.errorNotifications.service.NotificationService;

class notificationControllerTest {

	NotificationService ntSrvc;
	
	@Mock
	CacheManager cachMgr;
	
	@Mock
	NotificationRepository ntRep;
	
	@Mock
	Notification nt;
	
	@Mock
	Notification nt2;
	
	@BeforeEach
	void setUp() throws Exception {
		ntRep 	= mock(NotificationRepository.class, RETURNS_SMART_NULLS);
		nt		= mock(Notification.class, RETURNS_SMART_NULLS);
		nt2		= mock(Notification.class, RETURNS_SMART_NULLS);
		cachMgr	= mock(CacheManager.class,RETURNS_SMART_NULLS);
		ntSrvc 	= new NotificationService(ntRep, null, cachMgr);
	}

	@Test
	void testIteratorToListNotification() {
		when(nt.getAffected()).thenReturn("a");
		when(nt.getEndDate()).thenReturn("2025");
		when(nt.getProblem()).thenReturn("prob");
		when(nt.getSeverity()).thenReturn(2);
		when(nt.getStartDate()).thenReturn("2024");
		when(nt2.getAffected()).thenReturn("b");
		when(nt2.getEndDate()).thenReturn("2025b");
		when(nt2.getProblem()).thenReturn("probb");
		when(nt2.getSeverity()).thenReturn(3);
		when(nt2.getStartDate()).thenReturn("2024b");
		List<Notification> lstNots = new ArrayList<>();
		List<Notification> lstNotsTRS = new ArrayList<>();
		lstNots.add(nt);
		lstNots.add(nt2);
		Iterable<Notification> objectList = (List<Notification>)lstNots;
		//when(ntCon.iteratorToListNotification(objectList, lstNotsTRS)).thenReturn(lstNots);
		assertThat(ntSrvc.iteratorToListNotification(objectList, lstNotsTRS).get(0).equals(nt));
	}

	/*@Test
	void testCheckSettingsTimestampEqualsCache() {
		//fail("no cache test yet");
	}

	@Test
	void testCheckMostCurrentNotificationTimestampEqualsCache() {
		//fail("no cache test yet");
	}

	@Test
	void testGetNotificationsWhenFinished() {
		//fail("no cache test yet");
	}

	@Test
	void testGetString() {
		//fail("no cache test yet");
	}

	@Test
	void testGetNotifications() {
		//fail("no cache test yet");
	}

	@Test
	void testCheckTimestampBigger6() {
		//fail("no cache test yet");
	}

	@Test
	void testCheckUserListExistsCache() {
		//fail("no cache test yet");
	}

	@Test
	void testCheckUserExistsCache() {
		//fail("no cache test yet");
	}*/

}
