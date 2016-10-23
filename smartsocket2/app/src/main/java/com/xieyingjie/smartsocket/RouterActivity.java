package com.xieyingjie.smartsocket;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xieyingjie.smartsocket.dao.DeviceDAO;
import com.xieyingjie.smartsocket.moudel.DeviceInfo;
import com.xieyingjie.smartsocket.moudel.MyWifiInfo;
import com.xieyingjie.smartsocket.utils.Config;
import com.xieyingjie.smartsocket.utils.HttpUtils;
import com.xieyingjie.smartsocket.utils.IfNetWorkConnected;
import com.xieyingjie.smartsocket.utils.SocketUtils;
import com.xieyingjie.smartsocket.utils.WifiAdmin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RouterActivity extends AppCompatActivity {

    /**
     *
     */
    private int Type = -1;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    /**
     * UI
     */
    private EditText mRouter_id_edit;
    private EditText mRouter_password_edit;
    private Button mRouter_router_password_btn_sure;
    private Button mRouter_router_password_btn_test;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            Type = data.getExtras().getInt("Type");
            if (mRouter_id_edit == null) {
                mRouter_id_edit = (EditText) findViewById(R.id.router_id_edit);
                mRouter_password_edit = (EditText) findViewById(R.id.router_password_edit);
                mRouter_router_password_btn_sure = (Button) findViewById(R.id.router_password_btn_sure);
                mRouter_router_password_btn_sure = (Button) findViewById(R.id.router_password_btn_test);
            }
            if (Config.SSID_CHOOSED == data.getExtras().getInt("isWhat")) {
                Snackbar.make(mRouter_id_edit, "请务必确保密码正确，要不然浪费资源", Snackbar.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(data.getExtras().getString("ssid"))) {
                    mRouter_id_edit.setText(data.getExtras().getString("ssid"));
                }
                if ("请选择路由器".equals(mRouter_id_edit.getText())) {
                    mRouter_router_password_btn_test.setEnabled(false);
                } else {
                    if (WifiAdmin.isWiFiEnable(RouterActivity.this)) {
                        mRouter_router_password_btn_test.setEnabled(true);
                    } else {
                        Toast.makeText(RouterActivity.this, "请打开wifi重试", Toast.LENGTH_SHORT).show();
                        mRouter_router_password_btn_test.setEnabled(false);
                    }
                }
                if (!TextUtils.isEmpty(data.getExtras().getString("password"))) {
                    mRouter_password_edit.setText(data.getExtras().getString("password"));
                } else {//是开放网络
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(RouterActivity.this);
                    alertDialog.setMessage("您的路由器密码设为空！！！！\n如果您执意尝试设置无密码的路由器-->\n请忽略这条提示");
                    alertDialog.setPositiveButton("agree", null);
                    alertDialog.show();
                    mRouter_password_edit.setEnabled(true);
                    mRouter_password_edit.setText("");
                }
            }
        }
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_router);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRouter_id_edit = (EditText) findViewById(R.id.router_id_edit);
        mRouter_password_edit = (EditText) findViewById(R.id.router_password_edit);
        mRouter_router_password_btn_sure = (Button) findViewById(R.id.router_password_btn_sure);
        mRouter_router_password_btn_test = (Button) findViewById(R.id.router_password_btn_test);
        mRouter_router_password_btn_sure.setEnabled(false);
        mRouter_router_password_btn_test.setEnabled(false);
        wifiWithPermissionOperation(); //避免6.0崩溃
        mRouter_router_password_btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 弹出进度条
                 */
                final String ssid = mRouter_id_edit.getText().toString();
                String password = mRouter_password_edit.getText().toString();

                final WifiAdmin wifiAdmin = new WifiAdmin(RouterActivity.this); //new 一个 wifiAdmin类
                final ProgressDialog dialog = new ProgressDialog(RouterActivity.this);
                if (wifiAdmin.checkState() == WifiManager.WIFI_STATE_ENABLED) {
                    dialog.setMessage("测试中。。。\n根据网络状况不同还有你的手机是否卡的一逼\n等待时间不同，请耐心");
                    dialog.setCancelable(false);
                    dialog.show();

                    wifiAdmin.startScan();
                    List<ScanResult> list = wifiAdmin.getWifiList();
//
                    final int netWorkId = wifiAdmin.AddWifiConfigInTest(list, ssid, password, Type); //配置wifi信息
                    final Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wifiAdmin.startScan();
//                            if(!TextUtils.equals(ssid,wifiAdmin.getSSID())){
                            if (wifiAdmin.connectWifi(netWorkId)) {
                                ProgressDialog progressDialog = new ProgressDialog(RouterActivity.this);
                                progressDialog.setMessage("请等待。。。正在尝试连接网络");
                                if (WifiAdmin.isNetworkAvailable(RouterActivity.this)) {
                                    progressDialog.dismiss();
                                }
                                wifiAdmin.startScan();
                                String ssid2 = wifiAdmin.getSSID().substring(1, wifiAdmin.getSSID().length() - 1);
                                if (TextUtils.equals(ssid2, ssid)) {
                                    new AlertDialog.Builder(RouterActivity.this).setMessage("\n您已经连接该网络\n").show();
                                    Toast.makeText(RouterActivity.this, "wifi测试成功", Toast.LENGTH_SHORT).show();
                                    mRouter_password_edit.setEnabled(false);
                                    mRouter_router_password_btn_sure.setEnabled(true);
                                } else {
                                    Toast.makeText(RouterActivity.this, "你设置的网络不行啊\n请检查密码", Toast.LENGTH_SHORT).show();
                                    mRouter_password_edit.setEnabled(true);
                                    mRouter_router_password_btn_sure.setEnabled(false);
                                }
                            } else { //配置失败
                                Toast.makeText(RouterActivity.this, "你设置的网络不行啊\n请检查密码或者路由器", Toast.LENGTH_SHORT).show();
                                mRouter_password_edit.setEnabled(true);
                                mRouter_router_password_btn_sure.setEnabled(false);
                            }
                            dialog.dismiss();
                        }
                    }, 500);
                } else {
                    new AlertDialog.Builder(RouterActivity.this).setMessage("请打开wifi重试").show();
                }

            }
        });

        mRouter_id_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IfNetWorkConnected.isWiFiEnable(RouterActivity.this)) {
                    Intent intent = new Intent(RouterActivity.this, WifiListActivity.class);
                    startActivityForResult(intent, 2);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RouterActivity.this);
                    builder.setTitle("请打开wifi重试");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
            }
        });
        mRouter_router_password_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WifiAdmin wifiAdmin = new WifiAdmin(RouterActivity.this); //new 一个 wifiAdmin类
                final ProgressDialog dialog = new ProgressDialog(RouterActivity.this);
                dialog.setTitle("来，去把好消息告诉你的设备\n喝杯水压压惊在来看看结果吧。。。");
                dialog.setCancelable(false);
                if (wifiAdmin.checkState() == WifiManager.WIFI_STATE_ENABLED) {
                    dialog.show();
                    attemptConnectDevice(dialog);
                } else {
                    new AlertDialog.Builder(RouterActivity.this)
                            .setTitle("一级警告")
                            .setMessage("\nbut当前网络无法连接服务器o\n为了您的安全\n请使用其他路由器\n" +
                                    "或者等待，\n" +
                                    "或者。。。").show();
                    mRouter_router_password_btn_sure.setEnabled(false);
                }
//                if (wifiAdmin.checkState() == WifiManager.WIFI_STATE_ENABLED) {
//                    wifiAdmin.startScan();
//                    List<ScanResult> list = wifiAdmin.getWifiList();
//                    final int netWorkId = wifiAdmin.AddWifiConfig(list, Config.DEVICE_SSID, Config.DEVICE_PASSWORD, 3); //配置设备wifi无密码
////                    sendRouterInfoToDevice(dialog);
//
////                    new Handler().postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
//                            dialog.setMessage("Connecting...");
//                            if (netWorkId != -1) {
//                                if (wifiAdmin.connectWifi(netWorkId)) {
//                                    if(WifiAdmin.isWiFiConnect(RouterActivity.this)) {
//                                        sendRouterInfoToDevice(dialog);
//                                    }
//                                } else {
//                                    dialog.setMessage("connecting device failed");
//                                    dialog.setCancelable(true);
//                                }
//                            } else {
//                                dialog.dismiss();
//                                new AlertDialog.Builder(RouterActivity.this)
//                                        .setMessage("您保存的设备信息有错，无法进行连接\n\n你可以去wlan设置\n把设备的热点信息删除了").show();
//                            }
////                        }
////                    },10);
//                }
//                else{
//                    new AlertDialog.Builder(RouterActivity.this)
//                            .setTitle("一级警告")
//                            .setMessage("\nbut当前网络无法连接服务器o\n为了您的安全\n请使用其他路由器\n" +
//                                    "或者等待，\n" +
//                                    "或者。。。").show();
//                    mRouter_router_password_btn_sure.setEnabled(false);
//                }
            }
        });
    }

    /**
     * 连接插座
     */
    private boolean attemptConnectDevice(final ProgressDialog dialog) {
        List<MyWifiInfo> arrayWifiInfo = new ArrayList<>();
        final WifiAdmin wifiAdmin = new WifiAdmin(RouterActivity.this);
        wifiAdmin.startScan();
        List<ScanResult> mScanResult = wifiAdmin.getWifiList();

        final int netWorkId = wifiAdmin.AddWifiConfigInConnect(mScanResult, Config.DEVICE_SSID, "", 1); //配置wifi信息 ，没有密码
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                wifiAdmin.startScan();
                if(TextUtils.equals(wifiAdmin.getSSID().substring(1,wifiAdmin.getSSID().length()-1),Config.DEVICE_SSID)){
                    dialog.setMessage("您当前已连接热点...");
                    dialog.setCancelable(false);
                    sendRouterInfoToDevice(dialog);
                }
                else {
                    if (wifiAdmin.connectWifi(netWorkId)) {
                        dialog.setMessage("连接设备热点成功...");
                        dialog.setCancelable(false);
                        sendRouterInfoToDevice(dialog);
                    } else {
                        dialog.setMessage("连接设备热点失败，单击取消...");
                        dialog.setCancelable(true);
                    }
                }
            }
        }, 1000);
        return false;


    }

    /**
     * 发送信息至插座设备
     *
     * @param dialog
     */
    private void sendRouterInfoToDevice(final ProgressDialog dialog) {
        final String ssid = mRouter_id_edit.getText().toString();
        final String password = mRouter_password_edit.getText().toString();
        final SocketUtils socketUtils = SocketUtils.getInstance();
        SocketUtils.getInstance().init(Config.CONNECT_IP, Config.CONNECT_PORT); //此处开启线程，连接设备
        SocketUtils.getInstance().setConnectListener(new SocketUtils.ConnectListener() {
            /**
             * 数据格式 ： header：start | data:fdfdsf | tail:end | cs:1
             */
            @Override
            public void OnConnectSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage("socket connecting success...");
                        dialog.setMessage("sending...");
                        Toast.makeText(RouterActivity.this, "连接设备成功", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void OnConnectFail() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setTitle("请检查你和设备之间的连接！");
                        dialog.setMessage("socket connecting failed...\nclick anywhere to cancel this");
                        dialog.setCancelable(true);
                    }
                });
            }
        });
        SocketUtils.getInstance().setMessageListener(new SocketUtils.MessageListener() {
            @Override
            public void OnSendSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage("waiting for receiving...");
                        dialog.setCancelable(true);
                        dialog.show();
                    }
                });
            }

            @Override
            public void OnReceiveSuccess(final String message) {
                //ok|id|10|smartsocket
                if (message.contains("ok")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.setMessage("Add Device...\n");
                            dialog.setCancelable(true);

                            final String data = "ssid|" + ssid + "|passord|" + password;
                            socketUtils.SendDataToSensor(data);
                            socketUtils.SendDataToSensor(data);
                            socketUtils.SendDataToSensor(data);


                            final AlertDialog.Builder builder = new AlertDialog.Builder(RouterActivity.this);
                            builder.setCancelable(false);
                            builder.setTitle("重要提示！！");
                            builder.setMessage("检测到您可能没有网络连接，请现在手动开启网络或者连接可用wifi");
                            builder.setPositiveButton("好的",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!WifiAdmin.isNetworkAvailable(RouterActivity.this)) {
                                        builder.setMessage("叫你去打开网络你不听？");
                                        Toast.makeText(RouterActivity.this,"你没网络",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        addDevice(message);

                                    }
                                }
                            });
                            builder.setNegativeButton("不弄了，拜拜",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();

//                            if (addDevice(message)) {
//                                dialog.setMessage("test Device complete...\nclick anywhere To cancel this Dialog");
//                                dialog.setCancelable(true);
//                                Toast.makeText(RouterActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
//                                Intent data = new Intent(RouterActivity.this, DemoActivity.class);
//                                data.putExtra("isWhat", Config.OPERATION_ADD_DEVICE);//返回登陆成功消息给DEMO Activity
//                                setResult(1, data); //请求码一致
//                                finish();
//                            } else {
//                                dialog.setMessage("Add Device failed...\n请检查原因后重试");
//                                dialog.setCancelable(true);
//                            }
                        }
                    });
                }
            }

            @Override
            public void OnSendFail() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage("send failed...\nclick anywhere to cancel this");
                        dialog.setCancelable(true);
                    }
                });
            }

            @Override
            public void OnReceiveFail() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage("receive failed...\nclick anywhere to cancel this");
                        dialog.setCancelable(true);
                    }
                });
            }
        });
    }

    private boolean addDevice(String message) {
        Integer deviceId = -1;
        String ownerId;
        String name = null;
        String state = "false";
        String available = "available";
        SharedPreferences sharedPreferences = getSharedPreferences(Config.PREFERENCE_USER_NAME, Config.MODE);
        ownerId = sharedPreferences.getString("account", null);

        String s[] = message.split(":");
        //ok|id|10|smartsocket
        deviceId = Integer.valueOf(s[2]);
//        if (message.contains("socket")) {
            name = "SmartSocket"+deviceId;
            Toast.makeText(RouterActivity.this, "这是一个插座", Toast.LENGTH_LONG).show();
//        }
        final DeviceInfo deviceInfo = new DeviceInfo(deviceId, ownerId, name,
                state, available, 0);

        final String function = "addDevice";
        final String httpUrl = Config.DEVICE_SERVLET;
        final HttpUtils httpUtils = new HttpUtils();
        final String params = "function=" + function
                + "&account=" + deviceInfo.getOwnerId()
                + "&deviceId=" + deviceInfo.getDeviceId()
                + "&deviceName=" + deviceInfo.getName()
                + "&deviceState=" + deviceInfo.getState()
                + "&deviceTiming=" + deviceInfo.getTiming()
                + "&deviceAvailable=" + deviceInfo.getAvailable();


        //A Asyn to Login
        httpUtils.doPostAsyn(httpUrl, params, new HttpUtils.CallBack() {
            @Override
            public void onRequestComplete(String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DeviceDAO dao = new DeviceDAO(RouterActivity.this);
                        dao.addDevice(deviceInfo);
                        Toast.makeText(RouterActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                        Intent data = new Intent(RouterActivity.this, DemoActivity.class);
                        data.putExtra("isWhat", Config.OPERATION_ADD_DEVICE);//返回登陆成功消息给DEMO Activity
                        setResult(1, data); //请求码一致
                        finish();
                    }
                });
            }

            @Override
            public void onRequestError(String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(RouterActivity.this).setTitle("notice").setMessage("失败添加\n").show();
                    }
                });
            }
        });
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(RouterActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void wifiWithPermissionOperation() {
        int hasPermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasPermission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    showMessageOKCancel("You need to allow access to ACCESS_COARSE_LOCATION",
                            new DialogInterface.OnClickListener() {
                                @TargetApi(Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                            REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            });
                    return;
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }
    }


    /**
     * 导航的监听事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

