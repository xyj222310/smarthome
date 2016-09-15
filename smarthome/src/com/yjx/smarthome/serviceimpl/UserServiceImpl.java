package com.yjx.smarthome.serviceimpl;

import com.yjx.smarthome.dao.UserDao;
import com.yjx.smarthome.moudel.User;
import com.yjx.smarthome.service.UserService;

public class UserServiceImpl implements UserService{
	private UserDao ud =null;
	@Override
	public void setId(UserDao ud) {
		this.ud = ud;
	}
}
