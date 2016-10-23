package com.xieyingjie.smartsocket.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xieyingjie.smartsocket.utils.Config;

/**
 * Created by XieYingjie on 2016/9/26/0026.
 */
public class DBOpenHelper extends SQLiteOpenHelper {


    public DBOpenHelper(Context context ) {
        super(context, Config.SQLITE_DB_NAME, null, Config.SQLITE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Config.SQLITE_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(oldVersion+"","数据库升级:"+newVersion+Config.DEVICE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Config.DEVICE_TABLE_NAME);
        onCreate(db);
    }
}
