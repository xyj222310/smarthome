package com.yjx.smarthome.action;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import utils.SocketServerUtils;

public class MyListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		SocketServerUtils serverUtils = SocketServerUtils.getInstance();
		serverUtils.init(9000);
		serverUtils.setConnectListener(new SocketServerUtils.ConnectListener() {
			
			@Override
			public void OnConnectSuccess() {
				// TODO Auto-generated method stub
				System.out.print("caonime ");
				serverUtils.SendDataToSensor("nimabi");
			}
			
			@Override
			public void OnConnectFail() {
				// TODO Auto-generated method stub
				System.out.print("caonimedeiafnidf  ");
			}
		});
		serverUtils.setMessageListener(new SocketServerUtils.MessageListener() {
			
			@Override
			public void OnSendSuccess() {
				// TODO Auto-generated method stub
				System.out.print("roger  ");
			}
			
			@Override
			public void OnSendFail() {
				// TODO Auto-generated method stub
				System.out.print("Ω” ’ ß∞‹  ");
			}
			
			@Override
			public void OnReceiveSuccess(String message) {
				// TODO Auto-generated method stub

				System.out.println("receive  "+message);
			}
			
			@Override
			public void OnReceiveFail() {
				// TODO Auto-generated method stub
				System.out.println("receive failed");
			}
		});
		System.out.print("fsafssfsfsdf");
	}

}
