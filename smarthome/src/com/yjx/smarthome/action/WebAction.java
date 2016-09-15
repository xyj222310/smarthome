package com.yjx.smarthome.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yjx.smarthome.service.UserService;
import com.yjx.smarthome.serviceimpl.UserServiceImpl;

public class WebAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserService us = null;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		us = new UserServiceImpl();
		
		if(true){
			
		}
		else{
			
		}
		return "success";
	}
	
}
