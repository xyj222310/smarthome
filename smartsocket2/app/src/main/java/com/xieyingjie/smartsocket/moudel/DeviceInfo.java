package com.xieyingjie.smartsocket.moudel;


import java.sql.Timestamp;

/**
 * DeviceInfo entity. @author MyEclipse Persistence Tools
 */

public class DeviceInfo implements java.io.Serializable {

	// Fields

	private Integer deviceId;
	private String ownerId;
	private String name;

	public Integer getTiming() {
		return timing;
	}

	public void setTiming(Integer timing) {
		this.timing = timing;
	}

	//	private Timestamp registerDate;
//	private Timestamp startTime;
	private Integer timing;
	private String state;
	private String available;

	// Constructors

	/** default constructor */
	public DeviceInfo() {
	}

	/** full constructor */
	public DeviceInfo(Integer deviceId, String ownerId, String name, String state,
			String available,Integer timing) {
		this.deviceId = deviceId;
		this.ownerId = ownerId;
		this.name = name;
		this.timing = timing;
		this.state = state;
		this.available = available;
	}

	// Property accessors

	public Integer getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public Timestamp getRegisterDate() {
//		return this.registerDate;
//	}
//
//	public void setRegisterDate(Timestamp registerDate) {
//		this.registerDate = registerDate;
//	}
//
//	public Timestamp getStartTime() {
//		return this.startTime;
//	}
//
//	public void setStartTime(Timestamp startTime) {
//		this.startTime = startTime;
//	}
//
//	public Timestamp getEndTime() {
//		return this.endTime;
//	}
//
//	public void setEndTime(Timestamp endTime) {
//		this.endTime = endTime;
//	}

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

}