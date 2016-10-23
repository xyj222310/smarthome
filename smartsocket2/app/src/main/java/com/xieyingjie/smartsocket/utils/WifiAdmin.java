package com.xieyingjie.smartsocket.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by XieYingjie on 2016/9/26/0026.
 */
public class WifiAdmin {
    // 定义WifiManager对象
    private WifiManager mWifiManager;
    // 定义WifiInfo对象
    private WifiInfo mWifiInfo;
    //    // 扫描出的网络连接列表
//    private Set<ScanResult> mWifiList;
    // 扫描出的网络连接列表
    private List<ScanResult> mWifiList;
    // 网络连接列表
    private List<WifiConfiguration> mWifiConfiguration;
    // 定义一个WifiLock
    WifiManager.WifiLock mWifiLock;

    public static final int TYPE_NO_PASSWD = 11;
    public static final int TYPE_WEP = 12;
    public static final int TYPE_WPA = 13;

    // 构造器
    public WifiAdmin(Context context) {
        // 取得WifiManager对象
        mWifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        // 取得WifiInfo对象
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    // 打开WIFI
    public void openWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    // 关闭WIFI
    public void closeWifi() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    // 检查当前WIFI状态
    public int checkState() {
        return mWifiManager.getWifiState();
    }

    // 锁定WifiLock
    public void acquireWifiLock() {
        mWifiLock.acquire();
    }

    // 解锁WifiLock
    public void releaseWifiLock() {
        // 判断时候锁定
        if (mWifiLock.isHeld()) {
            mWifiLock.acquire();
        }
    }

    // 创建一个WifiLock
    public void creatWifiLock() {
        mWifiLock = mWifiManager.createWifiLock("Test");
    }

    // 得到配置好的网络
    public List<WifiConfiguration> getWifiConfiguration() {
        return mWifiConfiguration;
    }

    // 指定配置好的网络进行连接
    public void connectConfiguration(int index) {
        // 索引大于配置好的网络索引返回
        if (index > mWifiConfiguration.size()) {
            return;
        }
        // 连接配置好的指定ID的网络
        mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,
                true);
    }

    public void startScan() {
        mWifiManager.startScan();
        // 得到扫描结果
        mWifiList = mWifiManager.getScanResults();
        // 得到配置好的网络连接
        mWifiConfiguration = mWifiManager.getConfiguredNetworks();
        mWifiInfo = mWifiManager.getConnectionInfo();
    }
//    public void startScan() {
//        mWifiManager.startScan();
//        // 得到扫描结果
//        List<ScanResult> list  = mWifiManager.getScanResults();
//        mWifiList = new HashSet<ScanResult>(list);
//        // 得到配置好的网络连接
//        mWifiConfiguration = mWifiManager.getConfiguredNetworks();
//    }

    // 得到网络列表
    public List<ScanResult> getWifiList() {
        return mWifiList;
    }

    // 得到网络列表
//    public Set<ScanResult> getWifiList() {
//        return mWifiList;
//    }
//
    // 查看扫描结果
    public StringBuilder lookUpScan() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mWifiList.size(); i++) {
            stringBuilder
                    .append("Index_" + new Integer(i + 1).toString() + ":");
            // 将ScanResult信息转换成一个字符串包
            // 其中把包括：BSSID、SSID、capabilities、frequency、level
            stringBuilder.append((mWifiList.get(i)).toString());
            stringBuilder.append("/n");
        }
        return stringBuilder;
    }
//    public StringBuilder lookUpScan() {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (ScanResult value :mWifiList) {
//            int i=0;
//            stringBuilder
//                    .append("Index_" + new Integer(i + 1).toString() + ":");
//            // 将ScanResult信息转换成一个字符串包
//            // 其中把包括：BSSID、SSID、capabilities、frequency、level
//            stringBuilder.append(value.toString());
//            stringBuilder.append("/n");
//            i++;
//        }
//        return stringBuilder;
//    }

    // 得到MAC地址
    public String getMacAddress() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
    }

    // 得到接入点的BSSID
    public String getBSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
    }
    // 得到接入点的BSSID
    public String getSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getSSID();
    }

    // 得到IP地址
    public int getIPAddress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    // 得到连接的ID
    public int getNetworkId() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
    }

    // 得到WifiInfo的所有信息包
    public String getWifiInfo() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
    }

    // 添加一个网络并连接
    public void addNetwork(WifiConfiguration wcg) {
        int wcgID = mWifiManager.addNetwork(wcg);
        boolean b = mWifiManager.enableNetwork(wcgID, true);
        System.out.println("a--" + wcgID);
        System.out.println("b--" + b);
    }

    // 断开指定ID的网络
    public void disconnectWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }
    // ============

    /**
     * 连接指定Id的WIFI * *
     *
     * @param wifiId * @return
     */
    public boolean connectWifi(int wifiId) {
        startScan();
        for (int i = 0; i < mWifiConfiguration.size(); i++) {
            WifiConfiguration wifi = mWifiConfiguration.get(i);
            if (wifi.networkId == wifiId) {
                while (!(mWifiManager.enableNetwork(wifiId, true))) {// 激活该Id，建立连接

                    Log.i("ConnectWifi",
                            String.valueOf(mWifiConfiguration.get(wifiId).status));// status:0--已经连接，1--不可连接，2--可以连接
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 得到Wifi配置好的密码或者其他信息
     */
    public String getConfiguratedPwd(String ssid,int Type) {
        String pwd = null;
        mWifiConfiguration = mWifiManager.getConfiguredNetworks();// 得到配置好的网络信息
        for (int i = 0; i < mWifiConfiguration.size(); i++) {
            Log.i(mWifiConfiguration.get(i).SSID,
                    String.valueOf(mWifiConfiguration.get(i).networkId));
            if (mWifiConfiguration.get(i).SSID.equals(ssid)) {// ssid相同
                if(Type==1){
                    pwd = "no_pass";
                }
                if(Type ==2){
                    pwd = mWifiConfiguration.get(i).wepKeys.toString();
                    pwd = pwd.substring(1,pwd.length()-1);
                }
                else{
                    pwd = mWifiConfiguration.get(i).preSharedKey;
                    pwd = pwd.substring(1,pwd.length()-1);
                }
            }
        }
        return pwd;
    }

    /**
     * 判定指定WIFI是否已经配置好,依据WIFI的地址SSID,返回NetId *
     * * @param SSID
     * * @return
     */
    public int IsConfiguration(String SSID) {
        mWifiConfiguration = mWifiManager.getConfiguredNetworks();// 得到配置好的网络信息
        Log.i("IsConfiguration", String.valueOf(mWifiConfiguration.size()));
        for (int i = 0; i < mWifiConfiguration.size(); i++) {
            Log.i(mWifiConfiguration.get(i).SSID,
                    String.valueOf(mWifiConfiguration.get(i).networkId));
            if (mWifiConfiguration.get(i).SSID.equals(SSID)) {// 地址相同
                return mWifiConfiguration.get(i).networkId;
            }
        }
        return -1;
    }

    /**
     * 添加指定WIFI的配置信息,原列表可以存在此SSID *
     * * @param wifiList
     * * @param ssid
     * * @param pwd
     * * @return
     */
    public int  AddWifiConfigInTest(List<ScanResult> wifiList, String ssid, String pwd, int Type) {
        int wifiId = -1;
        for (int i = 0; i < wifiList.size(); i++) {
            ScanResult wifi = wifiList.get(i);
            if (TextUtils.equals(ssid, wifi.SSID)) {
                WifiConfiguration config = new WifiConfiguration();
                Log.i("AddWifiConfig", "equals");
                config.allowedAuthAlgorithms.clear();
                config.allowedGroupCiphers.clear();
                config.allowedKeyManagement.clear();
                config.allowedPairwiseCiphers.clear();
                config.allowedProtocols.clear();
                config.SSID = "\"" + wifi.SSID + "\"";// \"转义字符，代表"
//                config.preSharedKey = "\"" + pwd + "\"";// WPA-PSK密码
                WifiConfiguration tempConfig = this.IsExsits(ssid);
                if (tempConfig != null) {
                    mWifiManager.removeNetwork(tempConfig.networkId);
                }
                if (Type == 1) // WIFICIPHER_NOPASS
                {
//                    config.wepKeys[0] = "";
                    config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    config.status = WifiConfiguration.Status.ENABLED;
                    config.wepTxKeyIndex = 0;
                }
                if (Type == 2) // WIFICIPHER_WEP
                {
                    config.hiddenSSID = true;
                    config.wepKeys[0] = "\"" + pwd + "\"";
                    config.wepTxKeyIndex = 0;
                    config.status = WifiConfiguration.Status.ENABLED;
                    config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                }
                // WIFICIPHER_WPA
                else if (Type == 3){
                    config.preSharedKey = "\"" + pwd + "\"";
                    config.hiddenSSID = true;
                    config.status = WifiConfiguration.Status.ENABLED;
                    config.allowedAuthAlgorithms
                            .set(WifiConfiguration.AuthAlgorithm.OPEN);
                    config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                    config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                    config.allowedGroupCiphers
                            .set(WifiConfiguration.GroupCipher.TKIP);
                    config.allowedPairwiseCiphers
                            .set(WifiConfiguration.PairwiseCipher.TKIP);
                    config.allowedPairwiseCiphers
                            .set(WifiConfiguration.PairwiseCipher.CCMP);
                    config.allowedProtocols
                            .set(WifiConfiguration.Protocol.RSN);
                    config.allowedProtocols
                            .set(WifiConfiguration.Protocol.WPA);
                }
                wifiId = mWifiManager.addNetwork(config);
//                return wifiId;
            }
        }
        return wifiId;
    }


    /**
     *
     * @param ssid
     * @return
     */

    public int  AddWifiConfigInConnect(List<ScanResult> wifiList, String ssid, String pwd, int Type) {
        int wifiId = -1;
        for (int i = 0; i < wifiList.size(); i++) {
            ScanResult wifi = wifiList.get(i);
            if (TextUtils.equals(ssid, wifi.SSID)) {
                WifiConfiguration config = new WifiConfiguration();
                Log.i("AddWifiConfig", "equals");
                config.allowedAuthAlgorithms.clear();
                config.allowedGroupCiphers.clear();
                config.allowedKeyManagement.clear();
                config.allowedPairwiseCiphers.clear();
                config.allowedProtocols.clear();
                config.SSID = "\"" + wifi.SSID + "\"";// \"转义字符，代表"
                WifiConfiguration tempConfig = this.IsExsits(ssid);
                if (tempConfig != null) {
                    wifiId =  tempConfig.networkId;
                }
                if (Type == 1) // WIFICIPHER_NOPASS
                {
                    config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    config.status = WifiConfiguration.Status.ENABLED;
                    config.wepTxKeyIndex = 0;
                }
                if (Type == 2) // WIFICIPHER_WEP
                {
                    config.hiddenSSID = true;
                    config.wepKeys[0] = "\"" + pwd + "\"";
                    config.wepTxKeyIndex = 0;
                    config.status = WifiConfiguration.Status.ENABLED;
                    config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                }
                // WIFICIPHER_WPA
                else if (Type == 3){
                    config.preSharedKey = "\"" + pwd + "\"";
                    config.hiddenSSID = true;
                    config.status = WifiConfiguration.Status.ENABLED;
                    config.allowedAuthAlgorithms
                            .set(WifiConfiguration.AuthAlgorithm.OPEN);
                    config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                    config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                    config.allowedGroupCiphers
                            .set(WifiConfiguration.GroupCipher.TKIP);
                    config.allowedPairwiseCiphers
                            .set(WifiConfiguration.PairwiseCipher.TKIP);
                    config.allowedPairwiseCiphers
                            .set(WifiConfiguration.PairwiseCipher.CCMP);
                    config.allowedProtocols
                            .set(WifiConfiguration.Protocol.RSN);
                    config.allowedProtocols
                            .set(WifiConfiguration.Protocol.WPA);
                }
                wifiId = mWifiManager.addNetwork(config);
            }
        }
        return wifiId;
    }
    public WifiConfiguration IsExsits(String ssid) {
        List<WifiConfiguration> existingConfigs = mWifiManager
                .getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            System.out.println(existingConfig.SSID);
            if(TextUtils.equals(existingConfig.SSID,"\"" + ssid + "\"")){
                System.out.println("拿到了相同配置对象：" + existingConfig.SSID);
                return existingConfig;
            }
        }
        return null;
    }

    // 将int类型的IP转换成字符串形式的IP
    private String ipIntToString(int ip) {
        try {
            byte[] bytes = new byte[4];
            bytes[0] = (byte) (0xff & ip);
            bytes[1] = (byte) ((0xff00 & ip) >> 8);
            bytes[2] = (byte) ((0xff0000 & ip) >> 16);
            bytes[3] = (byte) ((0xff000000 & ip) >> 24);
            return Inet4Address.getByAddress(bytes).getHostAddress();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 判断网络连接状态
     */
    //判断网络连接是否可用
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
        } else {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null&&networkInfo.length>0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
                        try {
                            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
                            int status = p.waitFor();
                            if (status == 0) {
                                return true;
                            } else {
                               return false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }
    public static boolean isWiFiEnable(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        if(wifiManager.getWifiState() != WifiManager.WIFI_STATE_DISABLED){
            return true;
        }
        return false;
    }
    //判断WiFi是连接
    public static boolean isWiFiConnect(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
    //判断移动数据是否打开
    public static boolean isMobileConnect(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }
}