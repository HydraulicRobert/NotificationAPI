package com.proxy.notifications.errorNotifications.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.proxy.notifications.errorNotifications.entity.Notification;

@Repository
@Component
public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	//notifications findByID();
	Iterable<Notification> findAllByOrderByStartDateDesc();
	
	Notification findTop1ByOrderByStartDateDesc();
}
