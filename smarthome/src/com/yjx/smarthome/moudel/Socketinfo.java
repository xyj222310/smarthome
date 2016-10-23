package com.yjx.smarthome.moudel;

/**
 * Socketinfo entity. @author MyEclipse Persistence Tools
 */

public class Socketinfo implements java.io.Serializable {

	// Fields

	private Integer deviceid;
	private Userinfo userinfo;
	private String devicename;
	private String state;
	private String available;
	private Integer timing;

	// Constructors

	/** default constructor */
	public Socketinfo() {
	}

	/** full constructor */
	public Socketinfo(Userinfo userinfo, String devicename, String state,
			String available, Integer timing) {
		this.userinfo = userinfo;
		this.devicename = devicename;
		this.state = state;
		this.available = available;
		this.timing = timing;
	}

	// Property accessors

	public Integer getDeviceid() {
		return this.deviceid;
	}

	public void setDeviceid(Integer deviceid) {
		this.deviceid = deviceid;
	}

	public Userinfo getUserinfo() {
		return this.userinfo;
	}
	public Integer getUserid() {
		return this.userinfo.getUserid();
	}

	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}

	public String getDevicename() {
		return this.devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAvailable() {
		return this.available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public Integer getTiming() {
		return this.timing;
	}

	public void setTiming(Integer timing) {
		this.timing = timing;
	}

}