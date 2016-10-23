package com.xieyingjie.smartsocket.adapters.RvAdapterLibirary;

import com.xieyingjie.smartsocket.R;
import com.xieyingjie.smartsocket.adapters.RvAdapterLibirary.ItemViewDelegate;
import com.xieyingjie.smartsocket.adapters.RvAdapterLibirary.ViewHolder;
import com.xieyingjie.smartsocket.moudel.DeviceInfo;

import java.security.Timestamp;
import java.text.SimpleDateFormat;

/**
 * 多种item的 recyclerView 需要自定义delegate
 */

//public class DeviceItemDelegate implements ItemViewDelegate<DeviceInfo>{

//    @Override
//    public int getItemViewLayoutId() {
//        return R.layout.layout_item_device;
//    }
//
//    @Override
//    public boolean isForViewType(DeviceInfo item, int position) {
//        return false;
//    }
//
//    @Override
//    public void convert(ViewHolder holder, DeviceInfo deviceInfo, int position) {
//        holder.setText(R.id.device_name,deviceInfo.getName());
//        holder.setText(R.id.device_valid,deviceInfo.getState());
//        Timestamp timestamp= deviceInfo.getEndTime();
//        if(deviceInfo.getEndTime()!=null){
//            SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("MM/dd HH:MM");
//            simpleDateFormat.format(timestamp);
//            holder.setText(R.id.device_end_time,"将在"+timestamp.toString()+"关闭");
//        }
//        else{
//            holder.setText(R.id.device_end_time,"去设置定时");
//        }
//    }
//}
