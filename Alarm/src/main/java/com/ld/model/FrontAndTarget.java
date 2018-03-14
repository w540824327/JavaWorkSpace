package com.ld.model;

public class FrontAndTarget {

	private Long id; // 主键
	private String frontPhone; // 前端设备号码
	private String installPosition; // 前端设备安装位置
	private Long targetId; // 目标手机id
	private Long frontId; // 前端设备id
	
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
	public String getInstallPosition() {
		return installPosition;
	}
	public void setInstallPosition(String installPosition) {
		this.installPosition = installPosition;
	}
	public Long getTargetId() {
		return targetId;
	}
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	public Long getFrontId() {
		return frontId;
	}
	public void setFrontId(Long frontId) {
		this.frontId = frontId;
	}
	
}
