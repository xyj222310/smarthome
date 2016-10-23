package com.yjx.smarthome.service;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.yjx.smarthome.moudel.Userinfo;
/**
 * ������ӿڲ�����Ϊbean��applicationContext.xml�У���ΪserviceImpl���麯��
 * @author XieYingjie
 *
 * @param <T>
 */
public interface UserService  extends  BaseService<Userinfo>{
	//����Ӧ��дһЩuserservice��ר�õķ�������������дbaseservice�ķ�����
	//��Ϊʵ�ֱ��ӿڵ����Ǽ̳�baseserviceimpl��
	public boolean login(String name,String password);

	public boolean userExist(String account);
	public boolean register(Userinfo user);
	public Userinfo findByName(String name) ;
	
	public JSONObject userToJson(Userinfo user) throws JSONException;
	public Userinfo jsonToUser(JSONObject jsonObject) throws JSONException;
	public JSONObject streamToJson(InputStream inputStream) throws IOException,JSONException;
}