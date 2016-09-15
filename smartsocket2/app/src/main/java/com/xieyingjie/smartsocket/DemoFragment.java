package com.xieyingjie.smartsocket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xieyingjie.smartsocket.myclass.DeviceInfo;

import java.util.ArrayList;

/**
 *
 */
public class DemoFragment extends Fragment {

	public static int MODE = Context.MODE_PRIVATE;
	/*定义一个SharedPreferences名。之后将以这个名字保存在Android文件系统中*/
	public static final String PREFERENCE_NAME = "SaveUser";
	private String account=null;
	private  String password = null;

	private FrameLayout fragmentContainer;//第一个布局
	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;

	private ImageView usercenter_user_icon;//第3个吧布局的控件
	private TextView usercenter_username;
	private Button usercenter_logout;
	private LinearLayout fragment3_tv4_form;
	private LinearLayout fragment3_tv1_form;

										//第二个布局de 控件

	private View view  = null;
	private  Toolbar toolbar =null;
	/**
	 * Create a new instance of the fragment
	 */
	public static DemoFragment newInstance(int index) {
		DemoFragment fragment = new DemoFragment();
		Bundle b = new Bundle();
		b.putInt("index", index);
		fragment.setArguments(b);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
		if (getArguments().getInt("index", 0) == 0) {
			view = inflater.inflate(R.layout.fragment_demo_settings, container, false);
			toolbar.setTitle("设备");
			initDemoSettings(view);
			return view;
		} else if (getArguments().getInt("index", 0) == 1){
			view = inflater.inflate(R.layout.fragment_demo_list, container, false);
			initDemoList(view);
			return view;
		}
		else {
			view = inflater.inflate(R.layout.fragment_demo_usercenter, container, false);
			initDemoUserCenter(view);
			return view;
		}
	}
	@Override
	public void onStart() {
		super.onStart();
		SharedPreferences sharedPreferences= getActivity().getSharedPreferences(PREFERENCE_NAME, MODE); //获取sharepreferences
		account = sharedPreferences.getString("account",null);
		password =sharedPreferences.getString("password",null);
	}
	/**
	 * Init demo settings fragment 1
	 */
	DeviceInfo deviceInfo = new DeviceInfo();
	private void initDemoSettings(View view) {
		recyclerView = (RecyclerView)view.findViewById(R.id.fragment_setting_device_recycler);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);
		ArrayList<DeviceInfo> itemsData = new ArrayList<>();//数据据库操作，本地操作
		for (int i = 0; i < 3; i++) {
			DeviceInfo deviceInfo = new DeviceInfo();
			deviceInfo.setDeviceName("智能插座");
			deviceInfo.setDeviceState("关闭");
			itemsData.add(deviceInfo);
		}
		DemoAdapter2 adapter = new DemoAdapter2(itemsData);
		recyclerView.setAdapter(adapter);
//		final SwitchCompat showHideBottomNavigation = (SwitchCompat) view.findViewById(R.id.fragment_demo_show_hide);
//		final SwitchCompat switchColored = (SwitchCompat) view.findViewById(R.id.fragment_demo_switch_colored);
//		final SwitchCompat switchFiveItems = (SwitchCompat) view.findViewById(R.id.fragment_demo_switch_five_items);
//
//		switchColored.setChecked(demoActivity.isBottomNavigationColored());
//		switchFiveItems.setChecked(demoActivity.getBottomNavigationNbItems() == 5);
//
//		switchColored.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				demoActivity.updateBottomNavigationColor(isChecked);
//			}
//		});
//		switchFiveItems.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				demoActivity.updateBottomNavigationItems(isChecked);
//			}
//		});
//		showHideBottomNavigation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				demoActivity.showOrHideBottomNavigation(isChecked);
//			}
//		});
    }
	/**
	 * Init the fragment 3
	 */

	private void initDemoUserCenter(View view){
		fragment3_tv1_form = (LinearLayout) view.findViewById(R.id.fragment3_tv1_form);
		fragment3_tv1_form.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Snackbar.make(fragment3_tv1_form, "我和下面几项都没开发出来，别乱按了", Snackbar.LENGTH_SHORT).show();
			}
		});
		usercenter_user_icon = (ImageView)view.findViewById(R.id.profile_image);
		usercenter_username = (TextView)view.findViewById(R.id.username);

		SharedPreferences sharedPreferences= getActivity().getSharedPreferences(PREFERENCE_NAME, MODE); //获取sharepreferences
		account = sharedPreferences.getString("account",null);
		password =sharedPreferences.getString("password",null);
		if(account!=null && password!=null){    // 只有账户和密码都保存了才算登陆
			if(account.length()>0&&password.length()>0 ){
				usercenter_username.setText(account);
			}
		}
		else{usercenter_username .setText("请登录");}
		usercenter_user_icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if("请登录".equals(usercenter_username.getText())){
					Intent intent = new Intent(getActivity(),LoginActivity.class);
					startActivity(intent);
				}
				else{
					Intent intent = new Intent(getActivity(),ProfileActivity.class);
					startActivity(intent);
				}
			}
		});
		fragment3_tv4_form  =(LinearLayout) view.findViewById(R.id.fragment3_tv4_form) ;
		fragment3_tv4_form.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),ProfileActivity.class);
				startActivity(intent);
			}
		});
		usercenter_logout = (Button)view.findViewById(R.id.logout);
		usercenter_logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),ProfileActivity.class);
				startActivity(intent);
			}
		});
	}
	/**
	 * Init the fragment2
	 */
	private void initDemoList(View view) {
		fragmentContainer = (FrameLayout) view.findViewById(R.id.fragment_container);
		recyclerView = (RecyclerView) view.findViewById(R.id.fragment_demo_recycler_view);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);
		ArrayList<String> itemsData = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			itemsData.add("Fragment " + getArguments().getInt("index", -1) + " / Item " + i);
		}
		DemoAdapter adapter = new DemoAdapter(itemsData);
		recyclerView.setAdapter(adapter);
	}
	/**
	 * Refresh
	 */
	public void refresh() {
		if (getArguments().getInt("index", 0) > 0 && recyclerView != null) {
			recyclerView.smoothScrollToPosition(0);
		}
	}
	/**
	 * Called when a fragment will be displayed
	 */
	public void willBeDisplayed() {
		// Do what you want here, for example animate the content
		if (fragmentContainer != null) {
			Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
			fragmentContainer.startAnimation(fadeIn);
		}
	}
	/**
	 * Called when a fragment will be hidden
	 */
	public void willBeHidden() {
		if (fragmentContainer != null) {
			Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
			fragmentContainer.startAnimation(fadeOut);
		}
	}
}
