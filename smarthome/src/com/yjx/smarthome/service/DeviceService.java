package com.yjx.smarthome.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.yjx.smarthome.moudel.Socketinfo;
/**
 * 这个父接口不能作为bean在applicationContext.xml中，因为serviceImpl是虚函数
 * @author XieYingjie
 *
 * @param <T>
 */
public interface DeviceService  extends  BaseService<Socketinfo>{
	//这里应该写一些userservice层专用的方法声明。不必写baseservice的方法，
	//因为实现本接口的类是继承baseserviceimpl的

	public void addDevice(Socketinfo device);
	public List<Socketinfo> findAll(Integer userid);
	//删除和保存都有
	public JSONObject deviceToJson(Socketinfo device) throws JSONException;
	public Socketinfo jsonToDevice(JSONObject jsonObject) throws JSONException;
	public JSONObject streamToJson(InputStream inputStream) throws IOException,JSONException;
	public void modifyDevice(Socketinfo device);
	Socketinfo findById(Integer deviceId);
}