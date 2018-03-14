package com.ld.model;

/**
 * 报警信息统计model
 * 
 * @version 1.0
 * @since 2018-01-26
 * @author dlw 727697064@qq.com
 *
 */
public class AlarmInfoCount {
	
	private Long id;// id
	private FrontDevice frontDevice; //前端设备对象
	private Long alarmCount; // 告警次数
	private Long smsCount; // 短信累计次数
	private Long smsCountSuccess; // 短信成功次数
	private Long checkCount; // 当月收到的自检次数
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public FrontDevice getFrontDevice() {
		return frontDevice;
	}
	public void setFrontDevice(FrontDevice frontDevice) {
		this.frontDevice = frontDevice;
	}
	public Long getAlarmCount() {
		return alarmCount;
	}
	public void setAlarmCount(Long alarmCount) {
		this.alarmCount = alarmCount;
	}
	public Long getSmsCount() {
		return smsCount;
	}
	public void setSmsCount(Long smsCount) {
		this.smsCount = smsCount;
	}
	public Long getSmsCountSuccess() {
		return smsCountSuccess;
	}
	public void setSmsCountSuccess(Long smsCountSuccess) {
		this.smsCountSuccess = smsCountSuccess;
	}
	public Long getCheckCount() {
		return checkCount;
	}
	public void setCheckCount(Long checkCount) {
		this.checkCount = checkCount;
	}
}
