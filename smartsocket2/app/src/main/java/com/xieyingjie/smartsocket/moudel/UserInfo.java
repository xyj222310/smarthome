package com.xieyingjie.smartsocket.moudel;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * UserInfo entity. @author MyEclipse Persistence Tools
 */

public class UserInfo implements java.io.Serializable {

	// Fields

	private Integer userId;
	private String account;
	private String password;
	private String sex;
	private String phone;
	private String icon;
	private Timestamp registerDate;
	private Set deviceInfos = new HashSet(0);

	// Constructors

	/** default constructor */
	public UserInfo() {
	}

	/** minimal constructor */
	public UserInfo(String account, String password) {
		this.account = account;
		this.password = password;
	}

	/** full constructor */
	public UserInfo(String account, String password, String sex, String phone,
			String icon, Timestamp registerDate, Set deviceInfos) {
		this.account = account;
		this.password = password;
		this.sex = sex;
		this.phone = phone;
		this.icon = icon;
		this.registerDate = registerDate;
		this.deviceInfos = deviceInfos;
	}

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Timestamp getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}

	public Set getDeviceInfos() {
		return this.deviceInfos;
	}

	public void setDeviceInfos(Set deviceInfos) {
		this.deviceInfos = deviceInfos;
	}

}