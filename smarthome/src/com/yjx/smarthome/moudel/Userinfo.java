package com.yjx.smarthome.moudel;

import java.util.HashSet;
import java.util.Set;

/**
 * Userinfo entity. @author MyEclipse Persistence Tools
 */

public class Userinfo implements java.io.Serializable {

	// Fields

	private Integer userid;
	private String account;
	private String userpass;
	private String sex;
	private String phone;
	private String icon;
	private Set socketinfos = new HashSet(0);

	// Constructors

	/** default constructor */
	public Userinfo() {
	}

	/** minimal constructor */
	public Userinfo(String account, String userpass) {
		this.account = account;
		this.userpass = userpass;
	}

	/** full constructor */
	public Userinfo(String account, String userpass, String sex, String phone,
			String icon, Set socketinfos) {
		this.account = account;
		this.userpass = userpass;
		this.sex = sex;
		this.phone = phone;
		this.icon = icon;
		this.socketinfos = socketinfos;
	}

	// Property accessors

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserpass() {
		return this.userpass;
	}

	public void setUserpass(String userpass) {
		this.userpass = userpass;
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

	public Set getSocketinfos() {
		return this.socketinfos;
	}

	public void setSocketinfos(Set socketinfos) {
		this.socketinfos = socketinfos;
	}

}