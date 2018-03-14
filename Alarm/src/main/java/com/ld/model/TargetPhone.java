package com.ld.model;

/**
 * 目标手机Model
 * 
 * @version 1.0
 * @since 2018-1-26
 * @author zjl 1781187542@qq.com
 *
 */
public class TargetPhone {

	private Long id; // id号
	private String name; // 姓名
	private String targetPhone; // 目标手机号
	private Long smsCount;
	private Float smsPrice;// 短信单价
	private Float prepayMoney;// 预付金额
	private Float remainMoney;// 余额
	private String smsSend;// 是否短信转发(1: 可以转发, 0:不可以)
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTargetPhone() {
		return targetPhone;
	}
	public void setTargetPhone(String targetPhone) {
		this.targetPhone = targetPhone;
	}
	public Long getSmsCount() {
		return smsCount;
	}
	public void setSmsCount(Long smsCount) {
		this.smsCount = smsCount;
	}
	public Float getSmsPrice() {
		return smsPrice;
	}
	public void setSmsPrice(Float smsPrice) {
		this.smsPrice = smsPrice;
	}
	public Float getPrepayMoney() {
		return prepayMoney;
	}
	public void setPrepayMoney(Float prepayMoney) {
		this.prepayMoney = prepayMoney;
	}
	public Float getRemainMoney() {
		return remainMoney;
	}
	public void setRemainMoney(Float remainMoney) {
		this.remainMoney = remainMoney;
	}
	public String getSmsSend() {
		return smsSend;
	}
	public void setSmsSend(String smsSend) {
		this.smsSend = smsSend;
	}
	@Override
	public String toString() {
		return "TargetPhone [id=" + id + ", name=" + name + ", targetPhone=" + targetPhone + ", smsCount=" + smsCount
				+ ", smsPrice=" + smsPrice + ", prepayMoney=" + prepayMoney + ", remainMoney=" + remainMoney
				+ ", smsSend=" + smsSend + "]";
	}
	
}
