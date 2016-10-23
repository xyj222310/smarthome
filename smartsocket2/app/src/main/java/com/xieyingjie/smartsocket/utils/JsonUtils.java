package com.xieyingjie.smartsocket.utils;

import com.xieyingjie.smartsocket.moudel.DeviceInfo;
import com.xieyingjie.smartsocket.moudel.UserInfo;
import com.xieyingjie.smartsocket.moudel.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by XieYingjie on 2016/9/21/0021.
 */
public class JsonUtils {
    public static UserInfo jsonToUser(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        UserInfo user = new UserInfo();
        user.setUserId(jsonObject.getInt("userId"));
        user.setAccount(jsonObject.getString("account"));
        user.setIcon(jsonObject.getString("icon"));
        user.setPassword(jsonObject.getString("userPass"));
        user.setPhone(jsonObject.getString("phone"));
        user.setSex(jsonObject.getString("sex"));
        return user;
    }
    public static DeviceInfo jsonToDevice(JSONObject jsonObject) throws JSONException {
        DeviceInfo DeviceInfo = new DeviceInfo();
        DeviceInfo.setName(jsonObject.getString("deviceName"));
        DeviceInfo.setDeviceId(jsonObject.getInt("deviceId"));
        DeviceInfo.setState(jsonObject.getString("deviceState"));
        DeviceInfo.setAvailable(jsonObject.getString("deviceAvailable"));
        DeviceInfo.setOwnerId(jsonObject.getString("deviceOwnerId"));
        DeviceInfo.setTiming(jsonObject.getInt("deviceTiming"));
        return DeviceInfo;
    }
//    public JSONObject userToJson(UserInfo user) throws JSONException {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("userId",user.getUserid());
//        jsonObject.put("account",user.getAccount());
//        jsonObject.put("icon",user.getIcon());
//        jsonObject.put("userPass",user.getUserpass());
//        jsonObject.put("phone",user.getPhone());
//        jsonObject.put("sex",user.getSex());
//        return jsonObject;
//    }
//    public JSONObject deviceToJson(DeviceInfo DeviceInfo){
//        JSONObject jsonObject = new JSONObject();
//        return jsonObject;
//    }
}
