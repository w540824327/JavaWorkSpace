package com.ld.model;

import java.util.Date;

public class AlarmRecord {

	private Long id;
	private String alarmType;
	private Date alarmTime;
	private String frontPhone;
	private Float battery1;
	private Float battery2;
	private Float battery3;
	private String installPosition;
	private Date installTime;
	private String type;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public Date getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}
	public String getFrontPhone() {
		return frontPhone;
	}
	public void setFrontPhone(String frontPhone) {
		this.frontPhone = frontPhone;
	}
	public Float getBattery1() {
		return battery1;
	}
	public void setBattery1(Float battery1) {
		this.battery1 = battery1;
	}
	public Float getBattery2() {
		return battery2;
	}
	public void setBattery2(Float battery2) {
		this.battery2 = battery2;
	}
	public Float getBattery3() {
		return battery3;
	}
	public void setBattery3(Float battery3) {
		this.battery3 = battery3;
	}
	public String getInstallPosition() {
		return installPosition;
	}
	public void setInstallPosition(String installPosition) {
		this.installPosition = installPosition;
	}
	public Date getInstallTime() {
		return installTime;
	}
	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
