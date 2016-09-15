package com.yjx.smarthome.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yjx.smarthome.service.UserService;

public class TestAction extends ActionSupport {

	private UserService us=null;
	private void setUs(UserService us){
		this.us = us;
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return "success";
	}

	
}
