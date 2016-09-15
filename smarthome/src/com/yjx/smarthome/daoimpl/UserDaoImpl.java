package com.yjx.smarthome.daoimpl;

import org.hibernate.SessionFactory;

import com.yjx.smarthome.dao.UserDao;

public class UserDaoImpl implements UserDao {
	private SessionFactory  sf = null;
	public void  setSf(SessionFactory sf) {
		this.sf = sf;
	}
	
}
