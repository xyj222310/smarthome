package com.yjx.smarthome.service;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.yjx.smarthome.moudel.Userinfo;
/**
 * 这个父接口不能作为bean在applicationContext.xml中，因为serviceImpl是虚函数
 * @author XieYingjie
 *
 * @param <T>
 */
public interface UserService  extends  BaseService<Userinfo>{
	//这里应该写一些userservice层专用的方法声明。不必写baseservice的方法，
	//因为实现本接口的类是继承baseserviceimpl的
	public boolean login(String name,String password);

	public boolean userExist(String account);
	public boolean register(Userinfo user);
	public Userinfo findByName(String name) ;
	
	public JSONObject userToJson(Userinfo user) throws JSONException;
	public Userinfo jsonToUser(JSONObject jsonObject) throws JSONException;
	public JSONObject streamToJson(InputStream inputStream) throws IOException,JSONException;
}