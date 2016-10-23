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
 *  ���ڸ���ʵ�����create���������в�ͬ��ҵ���߼������紴���û�ǰ��Ҫ����˺��Ƿ��Ѿ����ڣ���
 *  ���԰��ඨ���abstract
 * ��create�������������abstract���ɾ����Service����ʵ��
 * ���Ǿ����������ã�����ÿ��service��dao ����ʵ�ֲ�ͬ�Ĺ��ܾͷֿ�д�����Ҽ̳�baseimpl 
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
		String jsonString = IOUtils.readLines(bf).toString();// ���ַ�����ת�����ַ���
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