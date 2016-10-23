package com.xieyingjie.smartsocket.adapters.RvAdapterLibirary;

import android.content.Context;

import com.xieyingjie.smartsocket.adapters.RvAdapterLibirary.MultiItemTypeAdapter;
import com.xieyingjie.smartsocket.moudel.DeviceInfo;

import java.util.List;

/**
 * 添加多种类的自定义数据类型的item的适配器
 */
//public class DeviceRecyclerAdapter extends MultiItemTypeAdapter<DeviceInfo> {
//	public DeviceRecyclerAdapter(Context context, List<DeviceInfo> datas) {
//		super(context, datas);
//		addItemViewDelegate(new DeviceItemDelegate());
//	}

//	/**
//	 *设备recycler的数据list
//	 */
//	private ArrayList<DeviceInfo> mDataSet = new ArrayList<>();
//
//	public static class ViewHolder extends RecyclerView.ViewHolder {
//		public TextView mDeviceName;  //
//		public TextView mDeviceState;
//		public ImageView mDeviceIcon;
//		public SwitchCompat mSwitch;
//		public ViewHolder(View v) {
//			super(v);
//			mDeviceName = (TextView) v.findViewById(R.id.device_name);
//			mDeviceState = (TextView) v.findViewById(R.id.device_state);
//			mDeviceIcon = (ImageView) v.findViewById(R.id.device_icon);
//			mSwitch = (SwitchCompat) v.findViewById(R.id.fragment_demo_switch);
//		}
//	}
//	public DeviceRecyclerAdapter(ArrayList<DeviceInfo> itemsData) {
//		mDataSet.clear();
//		mDataSet.addAll(itemsData);
//	}
//
//	@Override
//	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_device, parent, false);
//		ViewHolder vh = new ViewHolder(v);
//		return vh;
//	}
//	@Override
//	public void onBindViewHolder(ViewHolder holder, int position) {
//		holder.mDeviceName.setText(mDataSet.get(position).getName());
//		holder.mDeviceState.setText(mDataSet.get(position).getState());
//	}
//	@Override
//	public int getItemCount() {
//		return mDataSet.size();
//	}
//}