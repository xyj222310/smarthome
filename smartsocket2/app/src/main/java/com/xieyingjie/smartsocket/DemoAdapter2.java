package com.xieyingjie.smartsocket;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xieyingjie.smartsocket.myclass.DeviceInfo;

import java.util.ArrayList;

/**
 *
 */
public class DemoAdapter2 extends RecyclerView.Adapter<DemoAdapter2.ViewHolder> {

	private ArrayList<DeviceInfo> mDataset = new ArrayList<>();

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView mDeviceName;
		public TextView mdeviceState;
		public ImageView mdeviceIcon;
		public SwitchCompat mSwitch;
		public ViewHolder(View v) {
			super(v);
			mDeviceName = (TextView) v.findViewById(R.id.device_name);
			mdeviceState = (TextView) v.findViewById(R.id.device_state);
			mdeviceIcon = (ImageView) v.findViewById(R.id.device_icon);
			mSwitch = (SwitchCompat) v.findViewById(R.id.fragment_demo_switch);
		}
	}

	public DemoAdapter2(ArrayList<DeviceInfo> itemsData) {
		mDataset.clear();
		mDataset.addAll(itemsData);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_setting_item, parent, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
			holder.mDeviceName.setText(mDataset.get(position).getDeviceName());
			holder.mdeviceState.setText(mDataset.get(position).getDeviceState());
	}
	@Override
	public int getItemCount() {
		return mDataset.size();
	}

}
