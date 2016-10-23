package com.xieyingjie.smartsocket.utils;

import android.content.Context;

/**
 * Created by XieYingjie on 2016/9/26/0026.
 */
public class Config {

    /**
     * Http Ip and Port
     */
//    public static String DEVICE_SERVLET= "http://192.168.137.1:8080/smarthome/DeviceServlet?";
//    public static String USER_SERVLET = "http://192.168.137.1:8080/smarthome/UserServlet?";
    public static String DEVICE_SERVLET= "http://115.28.148.178:8080/smarthome/DeviceServlet?";
    public static String USER_SERVLET = "http://115.28.148.178:8080/smarthome/UserServlet?";
    /**
     定义一个SharedPreferences名。之后将以这个名字保存在Android文件系统
     */
    public static final String PREFERENCE_DEVICE_SSID = "SaveDevice";
    public static final String PREFERENCE_USER_NAME = "SaveUser";
    public static int MODE = Context.MODE_PRIVATE;
    /**
     * 设备wifi名字
     */
    public static String DEVICE_SSID ="AI-THINKER_0B47FA";
//    public static String DEVICE_PASSWORD ="00000000";


    /**
     * activity之间通信刷新控件的标志
     */
    public static final int OPERATION_LORD_ALL = 1;
    public static final int OPERATION_REFRESH_BY_INDEX = 2;
    public static final int OPERATION_LOGIN = 3;
    public static final int OPERATION_LOGOUT = 4;
    public static final int OPERATION_DELETE_DEVICE = 5;
    public static final int OPERATION_ADD_DEVICE = 6;

    public  static final int SSID_CHOOSED = 100;
    /**
     *SQLITE
     */
    public static final String CONNECT_IP = "192.168.4.1";
//    public static final String CONNECT_IP = "192.168.137.1";
    public static final int CONNECT_PORT= 8888;
//    public static final int CONNECT_PORT = 9999;

    public static final String DEVICE_ID_STR = "deviceId"; //从设备传来
    public static final String DEVICE_NAME_STR = "name";//从设备传来
    public static final String DEVICE_STATE_STR = "state";//初始化自定义
    public static final String DEVICE_AVAILABLE_STR = "available";//自定义
    public static final String DEVICE_TIMING_STR = "timing";//自定义
    public static final String DEVICE_OWNERID_STR = "ownerId";//当前用户获取
    /**
     * 数据库表的名字
     */
    public static final String DEVICE_TABLE_NAME = "deviceInfo";

    public static final String SQLITE_DB_NAME = "smartHome.db";
    public static final int SQLITE_VERSION = 2;
//    public static final String SQLITE_CREATE_TABLE = " CREATE TABLE IF NOT EXISTS `"+Config.DEVICE_TABLE_NAME+" ` (" +
//            "  `deviceId` INTEGER PRIMARY KEY NOT NULL," +
//            "  `name` varchar(20) DEFAULT NULL," +
//            "  `registerDate` timestamp NULL DEFAULT NULL," +
//            "  `startTime` timestamp NULL DEFAULT NULL," +
//            "  `endTime` timestamp NULL DEFAULT NULL," +
//            "  `ownerId` varchar(255) DEFAULT NULL," +
//            "  `state` varchar(255) DEFAULT ''," +
//            "  `available` varchar(255) DEFAULT NULL)" ;

    public static final String SQLITE_CREATE_TABLE = "create table "+ Config.DEVICE_TABLE_NAME +
            "(deviceId integer PRIMARY KEY,name varchar(20),ownerId varchar(255),state varchar(255),available varchar(255), timing integer)";

}
