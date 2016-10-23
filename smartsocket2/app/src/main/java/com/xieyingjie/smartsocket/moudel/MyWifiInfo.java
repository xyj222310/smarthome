package com.xieyingjie.smartsocket.moudel;

/**
 * Created by XieYingjie on 2016/9/27/0027.
 */
public class MyWifiInfo {
    /**
     * The network name.
     */
    public String SSID;

    /**
     * The address of the access point.
     */
    public String BSSID;
    /**
     * Describes the authentication, key management, and encryption schemes
     * supported by the access point.
     */
    public String capabilities;
    /**
     * The detected signal level in dBm, also known as the RSSI.
     *
     * <p>Use {@link android.net.wifi.WifiManager#calculateSignalLevel} to convert this number into
     * an absolute signal level which can be displayed to a user.
     */
    public int level;

    public MyWifiInfo(String BSSID, String SSID, String capabilities, int level) {

        this.BSSID = "\""+BSSID+"\"";
        this.SSID =  "\""+SSID+"\"";
        this.capabilities = capabilities;
        this.level = level;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID =  "\""+SSID+"\"";
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID =  "\""+BSSID+"\"";
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    /**
     * 如果对象类型是MyWifiInfo 的话 则返回true 去比较hashCode值
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(obj instanceof MyWifiInfo) {
            MyWifiInfo myWifiInfo = (MyWifiInfo) obj;
            if (myWifiInfo.getSSID() .equals(this.SSID)) {
//                if (myWifiInfo.getBSSID() .equals(this.BSSID)) { //ssid 一致，在判断BSSID是否一致，此时才去重
                    return true;
//                }
            }// 只比较id
        }
            // 比较id和username 一致时才返回true 之后再去比较 hashCode
//            if(myWifiInfo.id == this.id && user.username.equals(this.username)) return true;
        return false;
    }
//
//
//
    /**
     * 重写hashcode 方法，返回的hashCode 不一样才认定为不同的对象
     */
    @Override
    public int hashCode() {
//         return SSID.hashCode(); // 只比较id，id一样就不添加进集合
        return SSID.hashCode() * BSSID.hashCode();
    }
    //		查看已经连接上的WIFI信息，在Android的SDK中为我们提供了一个叫做WifiInfo的对象，这个对象可以通过WifiManager.getConnectionInfo()来获取。
//      WifiInfo中包含了当前连接中的相关信息。
//		getBSSID()  获取BSSID属性
//		getDetailedStateOf()  获取客户端的连通性
//		getHiddenSSID()  获取SSID 是否被隐藏
//		getIpAddress()  获取IP 地址
//		getLinkSpeed()  获取连接的速度
//		getMacAddress()  获取Mac 地址
//		getRssi()  获取802.11n 网络的信号
//		getSSID()  获取SSID
//		getSupplicanState()  获取具体客户端状态的信息
//StringBuffer sb = new StringBuffer();
//        sb.append("\n获取BSSID属性（所连接的WIFI设备的MAC地址）：" + wifiInfo.getBSSID());
//		sb.append("getDetailedStateOf()  获取客户端的连通性：");
//        sb.append("\n\n获取SSID 是否被隐藏："+ wifiInfo.getHiddenSSID());
//        sb.append("\n\n获取IP 地址：" + wifiInfo.getIpAddress());
//        sb.append("\n\n获取连接的速度：" + wifiInfo.getLinkSpeed());
//        sb.append("\n\n获取Mac 地址（手机本身网卡的MAC地址）：" + WifiMac);
//        sb.append("\n\n获取802.11n 网络的信号：" + wifiInfo.getRssi());
//        sb.append("\n\n获取SSID（所连接的WIFI的网络名称）：" + wifiInfo.getSSID());
//        sb.append("\n\n获取具体客户端状态的信息：" + wifiInfo.getSupplicantState());
//        mac.setText("WIFI网络信息:  " + sb.toString() + "\n\n蓝牙MAC:  " + btMac);
}
