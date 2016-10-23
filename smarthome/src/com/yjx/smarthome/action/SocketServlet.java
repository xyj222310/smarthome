package com.yjx.smarthome.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import utils.SocketServerUtils;
import utils.SocketUtils;

import com.yjx.smarthome.moudel.Socketinfo;
import com.yjx.smarthome.moudel.Userinfo;
import com.yjx.smarthome.service.DeviceService;
import com.yjx.smarthome.service.UserService;
import com.yjx.smarthome.serviceimpl.DeviceServiceImpl;
import com.yjx.smarthome.serviceimpl.UserServiceImpl;

/**
 * Servlet implementation class TestServlet
 */
public class SocketServlet extends HttpServlet {
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SocketServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/**ע������������ʽ
	 * {"total":2,"success":true,"arrayData":[{"id":1,"name":"С��"},{"id":2,"name":"Сè"}]}
	 * 			ʹ�� JSONObject jsonObject=new JSONObject(json); Ȼ�������array���Լ���
	 * 				JSONArray jsonArray = jsonObject.getJSONArray("arrayData");
	 *	���ص�������ʽ��һ��Object���ͣ����Կ���ֱ��ת����һ��Object   
	 *			ʹ��JSONObject jsonObject=new JSONObject(json);  
	 *	������ʽ��[{"id":1,"name":"С��","age":22},{"id":2,"name":"Сè","age":23}] 
	 *			ʹ��JSONArray jsonArray = new JSONArray(json); 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Ҫ���տͻ��˴�������json����������getQueryString ���� getinputstream
		//Ȼ��������н���,���ǣ�������ȷʵ�鷳�ˣ����ԣ��Ҿ��Ծ���getParamenter

	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		final Integer id=0;
		System.out.print("fsafssfsfsdf");
		SocketServerUtils serverUtils = SocketServerUtils.getInstance();
		serverUtils.init(9000);
		serverUtils.setConnectListener(new SocketServerUtils.ConnectListener() {
			
			@Override
			public void OnConnectSuccess() {
				// TODO Auto-generated method stub
				System.out.print("caonime ");
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
				System.out.print("����ʧ��  ");
			}
			
			@Override
			public void OnReceiveSuccess(String message) {
				// TODO Auto-generated method stub
				ApplicationContext ctx = 
						new ClassPathXmlApplicationContext("applicationContext.xml");	
				DeviceService deviceServiceImpl = (DeviceService) ctx.getBean("deviceServiceImpl");
				//ok:123:smartsocket
				//д������Ӳ����������˿ڵĻ������������ܲ���������id��1
				//message = ":ok:123:";
				System.out.println("receive  "+message);
				if(message.startsWith(":")||message.endsWith(":") ){
					String [] str = message.split(":");
					System.out.println(str[2]);
					if(message.contains("ok")){
						Integer id = Integer.valueOf(str[2].trim());
//			
						Socketinfo socket = deviceServiceImpl.findById(id);
						socket.setAvailable("true");
						if(socket.getState().contains("false")){
							serverUtils.SendDataToSensor("false");
							System.out.println("send  false");
						}
						else{
							serverUtils.SendDataToSensor("true");
							System.out.println("send  true");
						}
					}
					else{
						serverUtils.SendDataToSensor("failed");
						System.out.println("send  falied");
					}
				}
			}
			@Override
			public void OnReceiveFail() {
				// TODO Auto-generated method stub
				ApplicationContext ctx = 
						new ClassPathXmlApplicationContext("applicationContext.xml");	
				DeviceService deviceServiceImpl = (DeviceService) ctx.getBean("deviceServiceImpl");
				Socketinfo socket = deviceServiceImpl.findById(1);
				socket.setAvailable("false");
				System.out.println("receive failed");
			}
		});
		System.out.print("fsafssfsfsdf");
	}

}
