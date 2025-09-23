package com.proxy.notifications.errorNotifications.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Settings {
	public String getLastChangeOn() {
		return lastChangeOn;
	}
	public void setLastChangeOn(String lastChangeOn) {
		this.lastChangeOn = lastChangeOn;
	}
	public String getLastChangeBy() {
		return lastChangeBy;
	}
	public void setLastChangeBy(String lastChangeBy) {
		this.lastChangeBy = lastChangeBy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String lastChangeOn;
	private String lastChangeBy;
}
