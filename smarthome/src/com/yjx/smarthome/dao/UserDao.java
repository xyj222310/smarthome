package com.yjx.smarthome.dao;

import com.yjx.smarthome.moudel.Userinfo;

public interface UserDao extends BaseDao<Userinfo>{
	//这里应该写一些userdao层专用的方法声明。不必写basedao的方法，因为实现本接口的类是继承basedaoimpl的
	public Userinfo findByName(String name) ;
	
}
