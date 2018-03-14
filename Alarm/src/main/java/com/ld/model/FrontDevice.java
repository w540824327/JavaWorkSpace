package com.ld.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class FrontDevice {

	private Long id;
	private String frontPhone;
	private Float battery1;
	private Float battery2;
	private Float battery3;
	private String installPosition;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date installTime; 
	private Integer signal; // 信号强度
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startCheckTime; // 开始自检时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endCheckTime; // 最后自检时间
	private Integer checkCycle; // 自检周期（单位天）
	
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
	public Integer getSignal() {
		return signal;
	}
	public void setSignal(Integer signal) {
		this.signal = signal;
	}
	public Date getStartCheckTime() {
		return startCheckTime;
	}
	public void setStartCheckTime(Date startCheckTime) {
		this.startCheckTime = startCheckTime;
	}
	public Date getEndCheckTime() {
		return endCheckTime;
	}
	public void setEndCheckTime(Date endCheckTime) {
		this.endCheckTime = endCheckTime;
	}
	public Integer getCheckCycle() {
		return checkCycle;
	}
	public void setCheckCycle(Integer checkCycle) {
		this.checkCycle = checkCycle;
	}
	@Override
	public String toString() {
		return "FrontDevice [id=" + id + ", frontPhone=" + frontPhone + ", battery1=" + battery1 + ", battery2="
				+ battery2 + ", battery3=" + battery3 + ", installPosition=" + installPosition + ", installTime="
				+ installTime + ", signal=" + signal + ", startCheckTime=" + startCheckTime + ", endCheckTime="
				+ endCheckTime + ", checkCycle=" + checkCycle + "]";
	}
	


}
