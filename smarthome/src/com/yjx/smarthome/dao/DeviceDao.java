package com.yjx.smarthome.dao;

import java.util.List;

import com.yjx.smarthome.moudel.Socketinfo;

public interface DeviceDao extends BaseDao<Socketinfo>{
	//这里应该写一些userdao层专用的方法声明。不必写basedao的方法，因为实现本接口的类是继承basedaoimpl的
	public List<Socketinfo> findAll(Integer userid);

	
}
