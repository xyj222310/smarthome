package com.yjx.smarthome.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.yjx.smarthome.moudel.Socketinfo;
/**
 * ������ӿڲ�����Ϊbean��applicationContext.xml�У���ΪserviceImpl���麯��
 * @author XieYingjie
 *
 * @param <T>
 */
public interface DeviceService  extends  BaseService<Socketinfo>{
	//����Ӧ��дһЩuserservice��ר�õķ�������������дbaseservice�ķ�����
	//��Ϊʵ�ֱ��ӿڵ����Ǽ̳�baseserviceimpl��

	public void addDevice(Socketinfo device);
	public List<Socketinfo> findAll(Integer userid);
	//ɾ���ͱ��涼��
	public JSONObject deviceToJson(Socketinfo device) throws JSONException;
	public Socketinfo jsonToDevice(JSONObject jsonObject) throws JSONException;
	public JSONObject streamToJson(InputStream inputStream) throws IOException,JSONException;
	public void modifyDevice(Socketinfo device);
	Socketinfo findById(Integer deviceId);
}