package com.xieyingjie.smartsocket.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xieyingjie.smartsocket.utils.Config;
import com.xieyingjie.smartsocket.moudel.DeviceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XieYingjie on 2016/9/26/0026.
 */
public class DeviceDAO {

    private DBOpenHelper helper = null;

    public DeviceDAO(Context context) {
        this.helper = new DBOpenHelper(context);
    }

    public boolean addDevice(DeviceInfo info){
        SQLiteDatabase db ;
        try{
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Config.DEVICE_ID_STR, info.getDeviceId());
            values.put(Config.DEVICE_NAME_STR, info.getName());
            values.put(Config.DEVICE_OWNERID_STR, info.getOwnerId());
            values.put(Config.DEVICE_STATE_STR, info.getState());
            values.put(Config.DEVICE_AVAILABLE_STR, info.getAvailable());
            db.insert(Config.DEVICE_TABLE_NAME, null, values);
            // 及时的关闭数据库连接
            db.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<DeviceInfo> getDataFromDevice(){
        SQLiteDatabase db = helper.getReadableDatabase();
        List<DeviceInfo> deviceInfoList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+Config.DEVICE_TABLE_NAME,null);
        while(cursor.moveToNext()){
            DeviceInfo info = new DeviceInfo();
            info.setDeviceId(cursor.getInt(cursor.getColumnIndex(Config.DEVICE_ID_STR)));
            info.setName(cursor.getString(cursor.getColumnIndex(Config.DEVICE_NAME_STR)));
            info.setOwnerId(cursor.getString(cursor.getColumnIndex(Config.DEVICE_OWNERID_STR)));
            info.setState(cursor.getString(cursor.getColumnIndex(Config.DEVICE_STATE_STR)));
            info.setAvailable(cursor.getString(cursor.getColumnIndex(Config.DEVICE_AVAILABLE_STR)));
            info.setTiming(cursor.getInt(cursor.getColumnIndex(Config.DEVICE_TIMING_STR)));
            deviceInfoList.add(info);
        }
        cursor.close();
        return deviceInfoList;
    }

    public void deleteDataFromDeviceById(Integer id){
        SQLiteDatabase db = helper.getReadableDatabase();
        db.execSQL("delete  from "+Config.DEVICE_TABLE_NAME +" where "+Config.DEVICE_ID_STR +"="+ id);
        db.close();
    }
    public void deleteAll(){
        SQLiteDatabase db = helper.getReadableDatabase();
        db.execSQL("delete from "+Config.DEVICE_TABLE_NAME );
        db.close();
    }

}
