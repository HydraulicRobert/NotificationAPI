package com.proxy.notifications.errorNotifications.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.proxy.notifications.configuration.variable.Global;
import com.proxy.notifications.errorNotifications.entity.Settings;

@Repository
@Component
public interface SettingsRepository extends CrudRepository<Settings, Long>{
	@Query(value = Global.gStrSQLGetSpecificSettings,
			nativeQuery = true)
	Settings findTop1By();
	Settings findTop1ByOrderByLastChangeOnDesc();
}
