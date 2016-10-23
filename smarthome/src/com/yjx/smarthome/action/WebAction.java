package com.yjx.smarthome.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yjx.smarthome.service.UserService;

/**
 * 此类是未来将要完成的web程序的action类，目前作为完全无界面的服务器 ，这个类用不到
 * @author XieYingjie
 *
 */
public class WebAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	private UserService userService = null;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
//		us = new BaseServiceImpl();
		
		if(true){
			
		}
		else{
		}
		return "success";
	}
	
}
