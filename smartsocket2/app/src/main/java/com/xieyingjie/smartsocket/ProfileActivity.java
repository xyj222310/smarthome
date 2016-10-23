package com.xieyingjie.smartsocket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xieyingjie.smartsocket.utils.Config;

public class ProfileActivity extends AppCompatActivity {


    private Button logout;
    private static int MODE = Context.MODE_PRIVATE;
    /*定义一个SharedPreferences名。之后将以这个名字保存在Android文件系统中*/
    private static final String PREFERENCE_NAME = "SaveUser";
    private String account=null;
    private  String password = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        getSupportActionBar().setTitle("系统设置");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences= getSharedPreferences(PREFERENCE_NAME, MODE); //获取sharepreferences
                SharedPreferences.Editor editor= sharedPreferences.edit();
                account = sharedPreferences.getString("account",null);
                password =sharedPreferences.getString("password",null);
                if(account!=null && password!=null){    // 只有账户和密码都保存了才算登陆
                    if(account.length()>0&&password.length()>0 ){
                        Toast.makeText(ProfileActivity.this, "注销成功", Toast.LENGTH_SHORT).show();
                        editor.putString("password",null);
                        editor.putString("account","未登录");
                        if(editor.commit()){
                            Intent data = new Intent(ProfileActivity.this,DemoActivity.class);
                            data.putExtra("isWhat", Config.OPERATION_LOGOUT);//返回登陆成功消息给DEMO Activity
                            setResult(1,data); //请求码一致
                            finish();
                        }
                    }
                }
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home :
//                Intent intent = new Intent(this,DemoActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//刷新
//                startActivity(intent);
                finish();
            default: return super.onOptionsItemSelected(item);
        }
    }
}
