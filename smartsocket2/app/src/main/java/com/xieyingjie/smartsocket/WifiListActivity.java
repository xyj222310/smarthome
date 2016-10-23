package com.xieyingjie.smartsocket;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xieyingjie.smartsocket.adapters.RvAdapterLibirary.CommonAdapter;
import com.xieyingjie.smartsocket.adapters.RvAdapterLibirary.ViewHolder;
import com.xieyingjie.smartsocket.moudel.MyWifiInfo;
import com.xieyingjie.smartsocket.utils.Config;
import com.xieyingjie.smartsocket.utils.DividerItemDecoration;
import com.xieyingjie.smartsocket.utils.IfNetWorkConnected;
import com.xieyingjie.smartsocket.utils.WifiAdmin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Handler;

public class WifiListActivity extends AppCompatActivity {

    /**
     * ui
     */
    private RecyclerView wifiListrecycler;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeLayout;

    /**
     * config
     */

    private IfNetWorkConnected ifConnectToInterNet;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private  List<MyWifiInfo> arrayWifiInfo;
    private StringBuffer sb = new StringBuffer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            arrayWifiInfo = new ArrayList<>();
        initLayout();
        wifiScanWrapper(); //扫描wifi线程
        /**
         * 设置recyclerView的自动义超级设配器
         */
        wifiListrecycler.setAdapter(new CommonAdapter<MyWifiInfo>(this, R.layout.layout_item_wifi_info, arrayWifiInfo) {
            @Override
            protected void convert(ViewHolder holder, final MyWifiInfo mWifiInfo, int position) {
                holder.setText(R.id.wifi_item_id, mWifiInfo.getSSID().substring(1,mWifiInfo.getSSID().length()-1));
                holder.setText(R.id.wifi_item_state, "" + mWifiInfo.getLevel());
                WifiAdmin wifiAdmin = new WifiAdmin(WifiListActivity.this);
                wifiAdmin.startScan();
                if (mWifiInfo.getSSID().equals(wifiAdmin.getSSID())) {
                    holder.setText(R.id.wifi_item_save_or_not, "已连接");
                } else {
                    if(wifiAdmin.IsConfiguration(mWifiInfo.getSSID())!=-1){
                        holder.setText(R.id.wifi_item_save_or_not, "已保存");
                    }
                    else if (mWifiInfo.getCapabilities().contains("WP")) {
                        holder.setText(R.id.wifi_item_save_or_not, "安全");
                    } else {
                        holder.setText(R.id.wifi_item_save_or_not, "开放");
                    }
                }
                final int resource;
                if (-mWifiInfo.getLevel() >= 90) {
                    resource = R.drawable.ic_maps_wifi1;
                } else if (-mWifiInfo.getLevel() >= 70) {
                    resource = R.drawable.ic_maps_wifi2;
                } else if (-mWifiInfo.getLevel() >= 60) {
                    resource = R.drawable.ic_maps_wifi3;
                } else {
                    resource = R.drawable.ic_maps_wifi4;
                }
                holder.setImageResource(R.id.wifi_item_image, resource);
                setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                        WifiAdmin wifiAdmin = new WifiAdmin(WifiListActivity.this);
                        wifiAdmin.startScan();
                        AlertDialog.Builder builder = new AlertDialog.Builder(WifiListActivity.this);
                        builder.setTitle(arrayWifiInfo.get(position).getSSID());
                        builder.setItems(
                                new String[]{
                                        "信号强度:\n"+arrayWifiInfo.get(position).getLevel()+""
                                        ,"MAC Address：\n"+arrayWifiInfo.get(position).getBSSID()
                                        ,"加密类型：\n"+arrayWifiInfo.get(position).getCapabilities()
                                        ,"当前连接的路由器：\n"+new WifiAdmin(WifiListActivity.this).getSSID()
                                        ,"请核对当前是否连接智能设备的热点，确认后输入密码：\n"
                                }
                                ,null);
                        final EditText editPass= new EditText(WifiListActivity.this);
//                        if(wifiAdmin.IsConfiguration(arrayWifiInfo.get(position).getSSID()) !=-1){
//                            int type = -1;
//                            if(arrayWifiInfo.get(position).getCapabilities().contains("WP")){
//                                type = 3;
//                            }
//                            else if(arrayWifiInfo.get(position).getCapabilities().contains("WE")){
//                                type=2;
//                            }
//                            else{type=1;
//                            }
//                            String pwd = wifiAdmin.getConfiguratedPwd(arrayWifiInfo.get(position).getSSID(),type);
//                            editPass.setText(pwd);
//                        }
                        builder.setView(editPass);
                        builder.setNegativeButton("取消", null);
                       if ((arrayWifiInfo.get(position).getCapabilities().contains("WP")
                                || arrayWifiInfo.get(position).getCapabilities().contains("WE"))
                               && TextUtils.isEmpty(editPass.getText().toString())){
                            editPass.setError("密码不能为空");
                            editPass.requestFocus();
                        }
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent data = new Intent(WifiListActivity.this,RouterActivity.class);
                                data.putExtra("isWhat",Config.SSID_CHOOSED);//返回登陆成功消息给DEMO Activity
                                data.putExtra("ssid",arrayWifiInfo.get(position).getSSID()
                                        .substring(1,arrayWifiInfo.get(position).getSSID().length()-1));
                                data.putExtra("password",editPass.getText().toString());
                                if(arrayWifiInfo.get(position).getCapabilities().contains("WP")){
                                    data.putExtra("Type",3);
                                }
                                else if(arrayWifiInfo.get(position).getCapabilities().contains("WE")){
                                    data.putExtra("Type",2);
                                }
                                else{
                                    data.putExtra("Type",1);
                                }
                                setResult(2,data); //resultCode一致
                                finish();
                            }
                        });
                            builder.show();

                    }
                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        Log.i("fsfsfsdfsdfsfsfsdf", "setOnItemClickListener: ");
                        AlertDialog.Builder builder = new AlertDialog.Builder(WifiListActivity.this);
                        builder.setTitle(arrayWifiInfo.get(position).getSSID());
                        builder.setItems(
                                new String[]{
                                        "信号强度:\n"+arrayWifiInfo.get(position).getLevel()+""
                                        ,"MAC Address：\n"+arrayWifiInfo.get(position).getBSSID()
                                        ,"加密类型：\n"+arrayWifiInfo.get(position).getCapabilities()
                                        ,"手机连接的路由器：\n"+new WifiAdmin(WifiListActivity.this).getSSID()
                                }
                                ,null);
                        builder.setNegativeButton("取消", null);
                        builder.setPositiveButton("确定", null);
                        builder.show();
                        return true;
                    }
                });
            }
        });
    }

    private void initLayout() {
        wifiListrecycler = (RecyclerView) findViewById(R.id.wifi_list_recycler_view);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.wifi_list_swipe_container);
        wifiListrecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        wifiListrecycler.setLayoutManager(layoutManager);
        //设置Item增加、移除动画
        wifiListrecycler.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        wifiListrecycler.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                if(IfNetWorkConnected.isWiFiEnable(WifiListActivity.this)){
                    wifiScanWrapper();
                }else {
                    Toast.makeText(WifiListActivity.this,"请打开wifi重试",Toast.LENGTH_SHORT).show();
                }
                wifiListrecycler.getAdapter().notifyDataSetChanged();
                swipeLayout.setRefreshing(false);
            }
        });
    }
    private void getAllNetWorkList() {
         WifiAdmin wifiAdmin = new WifiAdmin(WifiListActivity.this);
         List<ScanResult>   mScanResult = new ArrayList<>();
        if (sb != null) {
            sb = new StringBuffer();
        }
        arrayWifiInfo.clear();
        wifiAdmin.startScan();
        if (wifiAdmin.getWifiList() != null) {
            mScanResult = noSameName(wifiAdmin.getWifiList());
            for (ScanResult value: mScanResult) {
                arrayWifiInfo.add(new MyWifiInfo(value.BSSID,
                        value.SSID, value.capabilities,
                        value.level));
            }
        }
        /**
         * 排序和 使得已连接网络显示在第一位
         */
        wifiAdmin.startScan();
        for (int i=0;i<arrayWifiInfo.size();i++){
            MyWifiInfo temp  =  arrayWifiInfo.get(i);
            String ssid = wifiAdmin.getSSID();
            if (temp.getSSID().equals(ssid)) {
                arrayWifiInfo.remove(arrayWifiInfo.get(i));
                arrayWifiInfo.add(0,temp);
            }
            else{
                if(wifiAdmin.IsConfiguration(temp.getSSID())!=-1 && arrayWifiInfo.size()>1){
                arrayWifiInfo.remove(arrayWifiInfo.get(i));
                arrayWifiInfo.add(1,temp);
                }
            }
        }
    }

    /**
     * 棉花糖系统犯贱，必须要手动处理权限问题，即使添加权限到menifest 也没暖用
     */
    private void wifiScanWrapper() {
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
        getAllNetWorkList();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(WifiListActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    /**
     * 去除同名WIFI
     * param 需要去除同名的列表
     * @return 返回不包含同命的列表
     */
    public List<ScanResult> noSameName(List<ScanResult> oldSr) {
        List<ScanResult> newSr = new ArrayList<>();
        for (ScanResult result : oldSr)
        {
            if (!TextUtils.isEmpty(result.SSID) && !containName(newSr, result.SSID)){
                newSr.add(result);
            }
        }
        return newSr;
    }

    /**
     * 判断一个扫描结果中，是否包含了某个名称的WIFI
     *
     * @param sr
     * 扫描结果
     * @param name
     * 要查询的名称
     * @return 返回true表示包含了该名称的WIFI，返回false表示不包含
     */
    public boolean containName(List<ScanResult> sr, String name){
        for (ScanResult result : sr)
        {
            if (!TextUtils.isEmpty(result.SSID) && result.SSID.equals(name))
                return true;
        }
        return false;
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
