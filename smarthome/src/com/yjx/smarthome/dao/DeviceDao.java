package com.yjx.smarthome.dao;

import java.util.List;

import com.yjx.smarthome.moudel.Socketinfo;

public interface DeviceDao extends BaseDao<Socketinfo>{
	//����Ӧ��дһЩuserdao��ר�õķ�������������дbasedao�ķ�������Ϊʵ�ֱ��ӿڵ����Ǽ̳�basedaoimpl��
	public List<Socketinfo> findAll(Integer userid);

	
}
