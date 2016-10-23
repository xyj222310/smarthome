package com.xieyingjie.smartsocket;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xieyingjie.smartsocket.adapters.RvAdapterLibirary.CommonAdapter;
import com.xieyingjie.smartsocket.adapters.RvAdapterLibirary.ViewHolder;
import com.xieyingjie.smartsocket.dao.DeviceDAO;
import com.xieyingjie.smartsocket.moudel.DeviceInfo;
import com.xieyingjie.smartsocket.moudel.MyWifiInfo;
import com.xieyingjie.smartsocket.utils.Config;
import com.xieyingjie.smartsocket.utils.DividerItemDecoration;
import com.xieyingjie.smartsocket.utils.HttpUtils;
import com.xieyingjie.smartsocket.utils.JsonUtils;
import com.xieyingjie.smartsocket.utils.SocketUtils;
import com.xieyingjie.smartsocket.utils.WifiAdmin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.xieyingjie.smartsocket.R.id.disableHome;
import static com.xieyingjie.smartsocket.R.id.fragment_setting_device_column;

/**
 * Fragment Manager
 */
public class DemoFragment extends Fragment {
	/**
	 * 第一个fragment
	 */
	private FrameLayout fragmentContainer;//第一个的布局
	private RecyclerView deviceRecyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private TextView deviceColumn;
	private Button deviceAddBtn;
	private SwipeRefreshLayout swipeLayout;
	private List<DeviceInfo> mData ;

	/**
	 * 第二个布局de 控件
	 */
	private View view  = null;
	private  Toolbar toolbar =null;
	private RecyclerView forumRecyclerView;
	private  boolean visibility_Flag = false;

	/**
	 * 第三个fragment的控件
	 */
	private ImageView userCenter_user_icon;//第3个吧布局的控件
	private TextView userCenter_username;//用户名

	private LinearLayout fragment3_tv4_form;//设置textview控件的父控件
	private LinearLayout fragment3_tv1_form;//“亟待开发”textview的父控件

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
			deviceRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_setting_device_recycler);
			deviceColumn = (TextView)view.findViewById(fragment_setting_device_column);
			Drawable drawable = view.getResources().getDrawable(R.drawable.down_arrow);
			drawable.setBounds(10, 10, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
			deviceColumn.setCompoundDrawables(null,null,drawable,null);
			deviceAddBtn = (Button)view.findViewById(R.id.fragment_setting_add_device_btn);
			swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.device_list_swipe_container);
			refresh(Config.OPERATION_LORD_ALL);
//            attemptSynDataRemotly();
			initDemoSettings(view);
			return view;
		} else if (getArguments().getInt("index", 0) == 1){
			view = inflater.inflate(R.layout.fragment_demo_list, container, false);
			fragmentContainer = (FrameLayout) view.findViewById(R.id.fragment_container);
			forumRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_demo_recycler_view);
			initDemoList(view);
//			refresh(Config.OPERATION_LORD_ALL);
			return view;
		}
		else {
			view = inflater.inflate(R.layout.fragment_demo_usercenter, container, false);
			userCenter_user_icon = (ImageView)view.findViewById(R.id.profile_image);
			userCenter_username = (TextView)view.findViewById(R.id.username);
			fragment3_tv1_form = (LinearLayout) view.findViewById(R.id.fragment3_tv1_form);
			fragment3_tv4_form  =(LinearLayout) view.findViewById(R.id.fragment3_tv4_form) ;
			initDemoUserCenter(view);
			refresh(Config.OPERATION_LOGIN);
			return view;
		}
	}
	/**
	 * Init demo settings fragment 1
	 */
//	DeviceInfo deviceInfo = new DeviceInfo();
	private void initDemoSettings(final View view) {
		deviceRecyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		deviceRecyclerView.setLayoutManager(layoutManager);
		//设置Item增加、移除动画
		deviceRecyclerView.setItemAnimator(new DefaultItemAnimator());
		//添加分割线
		deviceRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
		swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {

				attemptSynDataRemotly();
				mData.clear();
				DeviceDAO dao = new DeviceDAO(getActivity());
				List<DeviceInfo> list = dao.getDataFromDevice();
				mData .addAll(list);
				deviceRecyclerView.getAdapter().notifyDataSetChanged();
				swipeLayout.setRefreshing(false);
			}
		});
		/**
		 * 添加设备列表
		 */
		 DeviceDAO dao = new DeviceDAO(getActivity());
		mData = new ArrayList<>();
//		if(mData!=null){
			mData.clear();
//		}
		mData = dao.getDataFromDevice();
//		Log.i(TAG, "initDemoSettings: "+mData.get(0).getOwnerId());
		deviceRecyclerView.setAdapter(new CommonAdapter<DeviceInfo>(getActivity(),R.layout.layout_item_device,mData) {
			@Override
			protected void convert(ViewHolder holder, final DeviceInfo deviceInfo, int position) {
				holder.setText(R.id.device_name,deviceInfo.getName());
				holder.setText(R.id.device_valid,deviceInfo.getAvailable());
				holder.setImageResource(R.id.device_icon,R.drawable.socket_icon);
				holder.setChecked(R.id.device_switch,Boolean.valueOf(deviceInfo.getState()));//
//				if( deviceInfo.getEndTime()!=null){
					holder.setText(R.id.device_end_time, deviceInfo.getTiming().toString());
//				}
				/**
				 * 添加设备item操作的监听函数
				 */
				holder.setOnLongClickListener(R.id.device_item_form, new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setTitle("删除该设备？");
						builder.setPositiveButton("of course",new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								attemptDeleteDevice(deviceInfo);
							}
						});
						builder.show();
						return true;
					}
				});
				holder.setOnCheckedChangeListener(R.id.device_switch,new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//						先把远程数据库改掉，
						if(isChecked){
							deviceInfo.setState("true");
						}else{deviceInfo.setState("false");}
						attemptModifyDevice(deviceInfo);
					}
				});
			}
		});
		/**
		 * 添加界面1上的按钮的监听
		 */
		SharedPreferences sharedPreferences= getActivity().getSharedPreferences(Config.PREFERENCE_USER_NAME,Config.MODE);
		final  String password = sharedPreferences.getString("password",null);
		final  String account = sharedPreferences.getString("username",null);
		deviceRecyclerView.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {//跳转到

			}
		});
		deviceAddBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(password==null){
					//设置搜索设备的监听函数
					Snackbar.make(v,"去寻找登录入口，否则不能添加设备",Snackbar.LENGTH_SHORT).show();
				}
				else{
					Snackbar.make(v,"前方高能",Snackbar.LENGTH_SHORT).show();
					final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
					alertDialog.setTitle("Alert：");
					alertDialog.setMessage(getString(R.string.alert_dialog_CharSequence));
					alertDialog.setPositiveButton("I Agree", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							attemptSearchDevice();
						}
					});
					alertDialog.setNeutralButton("拜拜", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
//					alertDialog.show();
//					Log.i(TAG, "onClick: fdfdfdfdffdfdfdfdfdfdfdfdfdfd");
//					Integer deviceId = 14321;
//					String ownerId = "xxxxx";
//					String name = "fdfdf";
//					 Integer timing=0;
//					String state = "closed";
//					String available = "available";
//
//					DeviceInfo deviceInfo = new DeviceInfo(deviceId, ownerId, name, state,available,timing);
//					DeviceDAO dao = new DeviceDAO(getActivity());
//					dao.addDevice(deviceInfo);
				}
			}
		});
		deviceColumn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable drawable  = null;
				if(password==null){
					//设置搜索设备的监听函数
					Toast.makeText(getActivity(),"送你去登录",Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getActivity(),LoginActivity.class);
					startActivity(intent);
//					startActivityForResult(intent,1);
				}
				if(visibility_Flag){
					deviceRecyclerView.setVisibility(View.VISIBLE);
					drawable = view.getResources().getDrawable(R.drawable.down_arrow);
					drawable.setBounds(10, 10, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
					deviceColumn.setCompoundDrawables(null,null,drawable,null);
					visibility_Flag = false;
				} else {
					deviceRecyclerView.setVisibility(View.INVISIBLE);
					drawable = view.getResources().getDrawable(R.drawable.up_arrow);
					drawable.setBounds(10, 10, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
					deviceColumn.setCompoundDrawables(null,null,drawable,null);
					visibility_Flag =true;
				}
			}
		});
    }

	/**
	 * Init the fragment2
	 */
	private void initDemoList(View view) {
		forumRecyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		forumRecyclerView.setLayoutManager(layoutManager);
		ArrayList<String> itemsData = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			itemsData.add("Fragment " + getArguments().getInt("index", -1) + " / Item " + i);
		}
		forumRecyclerView.setAdapter(new CommonAdapter<String>(getActivity(),R.layout.layout_item_demo,itemsData) {
			@Override
			protected void convert(ViewHolder holder, String s, int position) {
				holder.setText(R.id.layout_item_demo_title,s);
			}
		});
	}

	/**
	 * Init the fragment 3
	 */
	private void initDemoUserCenter(View view){
		final  SharedPreferences sharedPreferences= getActivity().getSharedPreferences(Config.PREFERENCE_USER_NAME, Config.MODE); //获取sharepreferences
		final String account = sharedPreferences.getString("account","未登录");
		userCenter_username.setText(account);
		fragment3_tv1_form.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Snackbar.make(fragment3_tv1_form, "我和下面几项都没开发出来，别乱按了", Snackbar.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(),RouterActivity.class);
				startActivityForResult(intent,1);
			}
		});
		userCenter_user_icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(null==sharedPreferences.getString("password",null)){
					Intent intent = new Intent(getActivity(),LoginActivity.class);
//					startActivityForResult(intent,1);
					startActivity(intent);
				}
				else{
					Intent intent = new Intent(getActivity(),ProfileActivity.class);
//					startActivityForResult(intent,1);
					startActivity(intent);
				}
			}
		});
		fragment3_tv4_form.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),ProfileActivity.class);
//				startActivityForResult(intent,1);
				startActivity(intent);
			}
		});
	}
	/**
	 * 搜索有无设备开的热点
	 */
	private void attemptSearchDevice(){

		List<MyWifiInfo> arrayWifiInfo = new ArrayList<>();
		WifiAdmin wifiAdmin = new WifiAdmin(getActivity());
		wifiAdmin.startScan();
		List<ScanResult> mScanResult = wifiAdmin.getWifiList();
		for (ScanResult value: mScanResult) {
			if(TextUtils.equals(value.SSID,Config.DEVICE_SSID)) {
				Toast.makeText(getActivity(), "搜索到设备", Toast.LENGTH_SHORT).show();
				final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setCancelable(false);
				builder.setMessage("继续设置路由信息？");
				builder.setPositiveButton("agree", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(getActivity(), RouterActivity.class);
						startActivityForResult(intent, 1);
					}
				});
			}
		}

//		final ProgressDialog dialog = new ProgressDialog(getActivity());
//		dialog.setTitle("Searching...");
//		dialog.setCancelable(false);
//		dialog.show();
//		final SocketUtils socketUtils = SocketUtils.getInstance();
//		SocketUtils.getInstance().init(Config.CONNECT_IP,Config.CONNECT_PORT);
//		SocketUtils.getInstance().setConnectListener(new SocketUtils.ConnectListener() {
//			@Override
//			public void OnConnectSuccess() {
//				getActivity().runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.PREFERENCE_DEVICE_SSID,Config.MODE);
//						SharedPreferences.Editor editor = sharedPreferences.edit();
//						WifiAdmin wifiAdmin = new WifiAdmin(getActivity());
//						editor.putString("DEVICE_SSID",wifiAdmin.getSSID().substring(1,wifiAdmin.getSSID().length()-1));
////						editor.putString("DEVICE_SSID",wifiAdmin.getSSID());
//						editor.commit();
//						dialog.setMessage("sending...");
//						socketUtils.SendDataToSensor("hello");
//						SharedPreferences sp = getActivity().getSharedPreferences(Config.PREFERENCE_DEVICE_SSID,Config.MODE);
////						TextUtils.equals(sp.getString("DEVICE_SSID",null),"8ge0" );
//						Log.i(TAG, "run: "+sp.getString("DEVICE_SSID",null));
//						Toast.makeText(getActivity(),"连接设备成功",Toast.LENGTH_SHORT).show();
//						//返回设备类型，默认关闭、可用状态注册日期写入，用户名写入，
//					}
//				});
//			}
//			@Override
//			public void OnConnectFail() {
//				getActivity().runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						dialog.setMessage("connecting failed...\nclick Blank Space To cancel this dialog");
//						dialog.setCancelable(true);
//						Toast.makeText(getActivity(),"连接设备失败,请确认是否已经连接热点",Toast.LENGTH_SHORT).show();
//					}
//				});
//			}
//		});
//		SocketUtils.getInstance().setMessageListener(new SocketUtils.MessageListener() {
//			@Override
//			public void OnSendSuccess() {
//				getActivity().runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						dialog.setMessage("starting receiving");
////						socketUtils.RecieveFromDevice();
//						Toast.makeText(getActivity(),"请求发送成功",Toast.LENGTH_SHORT).show();
//					}
//				});
//			}
//
//			@Override
//			public void OnReceiveSuccess(final String message) {
//				/**
//				 * 数据格式 ： header：start  length： data: cs:  tail:end
//				 */
//				getActivity().runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						dialog.setMessage("starting to setting Router...");
//						dialog.setCancelable(true);
////						SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.PREFERENCE_USER_NAME,Config.MODE);
////						String password = sharedPreferences.getString("password",null);
////						String account = sharedPreferences.getString("account",null);
//						if(message.contains("hello")){
//                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                            builder.setCancelable(false);
//                            builder.setMessage("继续设置路由信息？");
//                            builder.setPositiveButton("agree", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Toast.makeText(getActivity(),"设备对你说："+message,Toast.LENGTH_SHORT).show();
//									SocketUtils.getInstance().Dissocket();
//                                    Intent intent = new Intent(getActivity(),RouterActivity.class);
//                                    startActivityForResult(intent,1);
////                               		dialog.dismiss();
//                                }
//                            });
//                            builder.setNegativeButton("disAgree",new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Toast.makeText(getActivity(), "您已经取消设置路由器", Toast.LENGTH_SHORT).show();
////                                    dialog.dismiss();
//                                }
//                            });
//                            dialog.dismiss();
//                            builder.show();
//						}
//					}
//				});
//			}
//
//			@Override
//			public void OnSendFail() {
//				getActivity().runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						dialog.setMessage("发送失败...");
//						dialog.setCancelable(true);
//						Toast.makeText(getActivity(),"请求出错，请检查连接并稍后重试",Toast.LENGTH_SHORT).show();
//					}
//				});
//			}
//			@Override
//			public void OnReceiveFail() {
//				getActivity().runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						dialog.setMessage("device not response...");
//						dialog.setCancelable(true);
//						Toast.makeText(getActivity(),"设备响应失败，稍后重试",Toast.LENGTH_SHORT).show();
//					}
//				});
//			}
//		});
	}

	/**
	 * 尝试远程链接数据库进行删除操作
	 */
	private void attemptDeleteDevice(final DeviceInfo deviceInfo){
		SharedPreferences sharedPreferences= getActivity().getSharedPreferences(Config.PREFERENCE_USER_NAME,Config.MODE);
		final  String password = sharedPreferences.getString("password",null);
		final  String account = sharedPreferences.getString("username",null);
		final String function = "deleteDevice";
		final String httpUrl = Config.DEVICE_SERVLET;
		HttpUtils httpUtils = new HttpUtils();
		String params = "function=" + function + "&account=" + account + "&password=" + password+"&deviceId="+deviceInfo.getDeviceId();
		//A Asyn to Login
		httpUtils.doPostAsyn(httpUrl, params,new HttpUtils.CallBack() {

			@Override
			public void onRequestComplete(String result) {
				DeviceDAO dao = new DeviceDAO(getActivity());
				dao.deleteDataFromDeviceById(deviceInfo.getDeviceId());
				new AlertDialog.Builder(getActivity()).setTitle("notice").setMessage("成功删除\n").show();
			}

			@Override
			public void onRequestError(String result) {
				new AlertDialog.Builder(getActivity()).setTitle("notice").setMessage("失败删除\n").show();
			}
		});
	}
	/**
	 * 同步本地远程数据库
	 */
	private void attemptSynDataRemotly(){
		SharedPreferences sharedPreferences= getActivity().getSharedPreferences(Config.PREFERENCE_USER_NAME,Config.MODE);
		final  String password = sharedPreferences.getString("password",null);
		final  String account = sharedPreferences.getString("account",null);
		final String function = "getAll";
		final String httpUrl = Config.DEVICE_SERVLET;
		HttpUtils httpUtils = new HttpUtils();
		String params = "function=" + function + "&account=" + account + "&password=" + password;
		//A Asyn to Login
		httpUtils.doPostAsyn(httpUrl, params,new HttpUtils.CallBack() {
			@Override
			public void onRequestComplete(String result) {
				DeviceDAO dao = new DeviceDAO(getActivity());
				try {
					JSONObject jsonObject = new JSONObject(result);
					JSONArray jsonArray = new JSONArray();
					if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
						jsonArray =jsonObject.getJSONArray("device");
					}
					if(jsonObject.getBoolean("state")){
                        DeviceDAO deviceDAO = new DeviceDAO(getActivity());
                        deviceDAO.deleteAll();
                        for (int i = 0;i <jsonArray.length(); i++) {
                            JSONObject jsobj = (JSONObject) jsonArray.get(i);
                            DeviceInfo deviceInfo = JsonUtils.jsonToDevice(jsobj);
                            dao.addDevice(deviceInfo);
                        }
                    }

				} catch (final JSONException e) {
					e.printStackTrace();
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							new AlertDialog.Builder(getActivity()).setTitle("notice").setMessage("同步远程数据库失败"+e).show();
						}
					});
				}
				//Toast.makeText(getActivity(),"同步远程数据库成功",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onRequestError(String result) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(),"当前无法连接服务器请检查网络",Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
	/**
	 * 尝试修改状态
	 */
	private void attemptModifyDevice(final DeviceInfo deviceInfo) {
		SharedPreferences sharedPreferences= getActivity().getSharedPreferences(Config.PREFERENCE_USER_NAME,Config.MODE);
		final  String password = sharedPreferences.getString("password",null);
		final  String account = sharedPreferences.getString("username",null);
		final String function = "modifyDevice";
		final String httpUrl = Config.DEVICE_SERVLET;
		HttpUtils httpUtils = new HttpUtils();
		String params = "function=" + function + "&account=" + account + "&password=" + password
                +"&deviceId="+deviceInfo.getDeviceId()+"&deviceState="+deviceInfo.getState();
		//A Asyn to Login
		httpUtils.doPostAsyn(httpUrl, params,new HttpUtils.CallBack() {

			@Override
			public void onRequestComplete(String result) {
				DeviceDAO dao = new DeviceDAO(getActivity());
                List<DeviceInfo> device = dao.getDataFromDevice();
                DeviceInfo deviceToModify = new DeviceInfo();
                for(DeviceInfo d:device){
                    if(d.getDeviceId() == deviceInfo.getDeviceId()){
                        deviceToModify = d;
                    }
                }
				dao.deleteDataFromDeviceById(deviceInfo.getDeviceId());
                dao.addDevice(deviceToModify);
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						new AlertDialog.Builder(getActivity()).setTitle("notice").setMessage("成功修改\n").show();
					}
				});
			}

			@Override
			public void onRequestError(String result) {
				getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					new AlertDialog.Builder(getActivity()).setTitle("notice").setMessage("失敗修改\n").show();
				}
				});
			}
		});
	}
	/**
	 * Refresh UI
	 */
	public void refresh(int operation) {
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.PREFERENCE_USER_NAME,Config.MODE);
		String password = sharedPreferences.getString("password",null);
		String account = sharedPreferences.getString("account",null);
		int index = getArguments().getInt("index",-1);
		switch (operation){
			case Config.OPERATION_LORD_ALL://初始化
				refreshIcon(account);
				break;
			case Config.OPERATION_LOGIN:
				refreshIcon(account);
				break;
			case Config.OPERATION_LOGOUT:
				refreshIcon(account);
				break;
			case Config.OPERATION_REFRESH_BY_INDEX://按下标刷新本页
				if(index==0){
				}else if(index==1){
					if(forumRecyclerView!=null){forumRecyclerView.smoothScrollToPosition(0);}
				}else{}
				break;
			default:break;
		}
	}

	private void refreshIcon(String account) {
//		SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.PREFERENCE_USER_NAME,Config.MODE);
//		String password = sharedPreferences.getString("password",null);
//		String account = sharedPreferences.getString("account",null);
		if(userCenter_username!=null){
			userCenter_username.setText(account);
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
