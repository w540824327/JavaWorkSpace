package com.ld.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SmsInfo {

	private Long id;
	private String frontPhone;
	private String targetPhone;
	private String smsContent;
	private String smsSuccess;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date smsTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFrontPhone() {
		return frontPhone;
	}
	public void setFrontPhone(String frontPhone) {
		this.frontPhone = frontPhone;
	}
	public String getTargetPhone() {
		return targetPhone;
	}
	public void setTargetPhone(String targetPhone) {
		this.targetPhone = targetPhone;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public String getSmsSuccess() {
		return smsSuccess;
	}
	public void setSmsSuccess(String smsSuccess) {
		this.smsSuccess = smsSuccess;
	}
	public Date getSmsTime() {
		return smsTime;
	}
	public void setSmsTime(Date smsTime) {
		this.smsTime = smsTime;
	}
	
}
