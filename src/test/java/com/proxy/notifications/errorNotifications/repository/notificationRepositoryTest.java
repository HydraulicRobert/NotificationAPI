package com.proxy.notifications.errorNotifications.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.proxy.notifications.errorNotifications.entity.Notification;
import com.proxy.notifications.errorNotifications.entity.Settings;

class notificationRepositoryTest {

	@Mock
	NotificationRepository ntRep;
	@Mock
	Notification nt;
	@Mock
	Notification nt2;
	
	
	@BeforeEach
	void setUp() throws Exception {
		 ntRep 	= mock(NotificationRepository.class,RETURNS_SMART_NULLS);
		 nt		= mock(Notification.class,RETURNS_SMART_NULLS);
		 nt2	= mock(Notification.class,RETURNS_SMART_NULLS);
	}

	@Test
	void testFindAllByOrderByStartDateDesc() {
		nt = new Notification();
		nt.setAffected("machine");
		nt.setEndDate("2025-01-01 23:00:01");
		nt.setProblem("broken");
		nt.setSeverity(2);
		nt.setStartDate("2024-01-01 23:00:01");
		nt2 = new Notification();
		nt2.setAffected("machine");
		nt2.setEndDate("2025-01-01 23:00:01");
		nt2.setProblem("broken");
		nt2.setSeverity(2);
		nt2.setStartDate("2024-01-01 23:00:01");
		List<Notification> lstNots = new ArrayList<>();
		lstNots.add(nt);
		lstNots.add(nt2);
		Iterable<Notification> objectList = (List<Notification>)lstNots;
		when(ntRep.findAllByOrderByStartDateDesc()).thenReturn(objectList);
		assertEquals(lstNots, ntRep.findAllByOrderByStartDateDesc());
	}
	@Test
	void testFindAllByOrderByStartDateDescEmptyObjects() {
		nt = new Notification();
		nt2 = new Notification();
		List<Notification> lstNots = new ArrayList<>();
		lstNots.add(nt);
		lstNots.add(nt2);
		Iterable<Notification> objectList = (List<Notification>)lstNots;
		when(ntRep.findAllByOrderByStartDateDesc()).thenReturn(objectList);
		assertEquals(lstNots, ntRep.findAllByOrderByStartDateDesc());
	}

	@Test
	void testFindTop1ByOrderByStartDateDesc() {
		nt = new Notification();
		nt.setAffected("machine");
		nt.setEndDate("2025-01-01 23:00:01");
		nt.setProblem("broken");
		nt.setSeverity(2);
		nt.setStartDate("2024-01-01 23:00:01");
		when(ntRep.findTop1ByOrderByStartDateDesc()).thenReturn(nt);
		assertThat(ntRep.findTop1ByOrderByStartDateDesc().equals(nt));
	}
	@Test
	void testFindTop1ByOrderByStartDateDescEmptyObject() {
		nt = new Notification();
		when(ntRep.findTop1ByOrderByStartDateDesc()).thenReturn(nt);
		assertThat(ntRep.findTop1ByOrderByStartDateDesc().equals(nt));
	}
	@Test
	void testFindTop1ByOrderByStartDateDescNoObject() {
		when(ntRep.findTop1ByOrderByStartDateDesc()).thenReturn(null);
		assertNull(ntRep.findTop1ByOrderByStartDateDesc());
	}

}
