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
	/**注意三种数据形式
	 * {"total":2,"success":true,"arrayData":[{"id":1,"name":"小猪"},{"id":2,"name":"小猫"}]}
	 * 			使用 JSONObject jsonObject=new JSONObject(json); 然后里面的array可以继续
	 * 				JSONArray jsonArray = jsonObject.getJSONArray("arrayData");
	 *	返回的数据形式是一个Object类型，所以可以直接转换成一个Object   
	 *			使用JSONObject jsonObject=new JSONObject(json);  
	 *	数据形式：[{"id":1,"name":"小猪","age":22},{"id":2,"name":"小猫","age":23}] 
	 *			使用JSONArray jsonArray = new JSONArray(json); 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//要接收客户端传过来的json包，可以用getQueryString 或者 getinputstream
		//然后对它进行解析,但是，这样做确实麻烦了，所以，我绝对就用getParamenter

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
				System.out.print("接收失败  ");
			}
			
			@Override
			public void OnReceiveSuccess(String message) {
				// TODO Auto-generated method stub
				ApplicationContext ctx = 
						new ClassPathXmlApplicationContext("applicationContext.xml");	
				DeviceService deviceServiceImpl = (DeviceService) ctx.getBean("deviceServiceImpl");
				//ok:123:smartsocket
				//写死，有硬件请求这个端口的话，必须是智能插座。而且id是1
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
