package com.xieyingjie.smartsocket.myclass;

import java.security.Timestamp;

/**
 * Created by XieYingjie on 2016/9/6/0006.
 */
public class DeviceInfo {
    int deviceId;
    String deviceName;
    Timestamp startTime;
    Timestamp endTime;
    String ownerName;
    String deviceState;
    String deviceAvailable;

    public String getDeviceAvailable() {
        return deviceAvailable;
    }

    public void setDeviceAvailable(String deviceAvailable) {
        this.deviceAvailable = deviceAvailable;
    }

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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState;
    }
}
