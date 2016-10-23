package com.yjx.smarthome.serviceimpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yjx.smarthome.dao.DeviceDao;
import com.yjx.smarthome.moudel.Socketinfo;
import com.yjx.smarthome.moudel.Userinfo;
import com.yjx.smarthome.service.DeviceService;

/**
 *  由于各个实体类的create（）方法有不同的业务逻辑（例如创建用户前需要检查账号是否已经存在），
 *  所以把类定义成abstract
 * 把create（）方法定义成abstract，由具体的Service单独实现
 * 但是觉得这样不好，所以每个service和dao 具体实现不同的功能就分开写，并且继承baseimpl 
 * @author XieYingjie
 * @param <User>
 */
@Service(value="deviceServiceImpl")
@Transactional
public class  DeviceServiceImpl extends BaseServiceImpl<Socketinfo> implements DeviceService{
	@Resource(name="deviceDaoImpl")
	@Qualifier
	private DeviceDao deviceDaoImpl;
	public DeviceServiceImpl() {
		super();
	}
	@Override
	public List<Socketinfo> findAll(Integer userid) {
		List<Socketinfo> list = deviceDaoImpl.findAll(userid);
		// TODO Auto-generated method stub
		return list;
	}
	@Override
	public Socketinfo  findById(Integer deviceId) {
		Socketinfo deviceInfo = new Socketinfo();
		deviceInfo = deviceDaoImpl.findById(deviceId);
		
		// TODO Auto-generated method stub
		return deviceInfo;
	}

	@Override
	public void delete(Socketinfo socket) {
		// TODO Auto-generated method stub
		try{
			deviceDaoImpl.delete(socket);
		}catch(Exception e){
			e.printStackTrace();
		}
//		.setParameter("deviceid", deviceId);
	}
	@Override
	public JSONObject deviceToJson(Socketinfo device) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("deviceId",device.getDeviceid());
		jsonObject.put("deviceName",device.getDevicename());
		jsonObject.put("deviceAvailable",device.getAvailable());
		jsonObject.put("deviceState",device.getState());
//		jsonObject.put("deviceStartTime",device.getStartTime());
//		jsonObject.put("deviceEndTime",device.getEndTime());
//		jsonObject.put("deviceRegisterDate",device.getRegisterDate());
		jsonObject.put("deviceOwnerId",device.getUserinfo().getUserid());
		jsonObject.put("deviceTiming",device.getTiming().toString());
		return jsonObject;
	}
	
	@Override
	public JSONObject streamToJson(InputStream inputStream) throws IOException, JSONException {
		// TODO Auto-generated method stub
		BufferedReader bf=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
		String jsonString = IOUtils.readLines(bf).toString();// 把字符数组转换成字符串
		JSONObject jsonObject = new JSONObject(jsonString);
		return jsonObject;
	}

	@Override
	public Socketinfo jsonToDevice(JSONObject jsonObject) throws JSONException {
		// TODO Auto-generated method stub
		Socketinfo device = new Socketinfo();
		device.setDeviceid(jsonObject.getInt("deviceId"));
		device.setDevicename(jsonObject.getString("deviceName"));
		device.setAvailable(jsonObject.getString("deviceAvailable"));
		device.setState(jsonObject.getString("deviceState"));
		device.setTiming(jsonObject.getInt("deviceTiming"));
//		device.setStartTime(Timestamp.valueOf(jsonObject.getString("deviceStartTime")));
//		device.setEndTime(Timestamp.valueOf(jsonObject.getString("deviceEndTime")));
//		device.setRegisterDate(Timestamp.valueOf(jsonObject.getString("deviceRegisterDate")));
		Userinfo user = new Userinfo();
		user.setAccount(jsonObject.getString("deviceOwnerId"));
		device.setUserinfo(user);
		return device;
	}
	@Override
	public void addDevice(Socketinfo device) {
		// TODO Auto-generated method stub
		deviceDaoImpl.create(device);
	}
	@Override
	public void modifyDevice(Socketinfo device) {
		// TODO Auto-generated method stub
		deviceDaoImpl.save(device);
	}
}