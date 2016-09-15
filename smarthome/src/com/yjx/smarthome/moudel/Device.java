package com.yjx.smarthome.moudel;

public class Device {

	private int deviceId;
	private String deviceName;
	private String startTime;
	private String endTime;
	private String available;
	private User userId;
	//constructor
	public Device(int deviceId, String deviceName, String startTime,
			String endTime, String available, User userId, String state) {
		super();
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.available = available;
		this.userId = userId;
		this.state = state;
	}
	public User getOwnerId() {
		return userId;
	}
	public void setOwnerId(User ownerId) {
		this.userId = ownerId;
	}
	private String state;
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
