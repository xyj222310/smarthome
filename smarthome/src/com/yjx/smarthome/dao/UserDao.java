package com.yjx.smarthome.dao;

import com.yjx.smarthome.moudel.Userinfo;

public interface UserDao extends BaseDao<Userinfo>{
	//����Ӧ��дһЩuserdao��ר�õķ�������������дbasedao�ķ�������Ϊʵ�ֱ��ӿڵ����Ǽ̳�basedaoimpl��
	public Userinfo findByName(String name) ;
	
}
