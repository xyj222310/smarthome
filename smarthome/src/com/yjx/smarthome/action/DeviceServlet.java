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
public class DeviceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DeviceService deviceServiceImpl = new DeviceServiceImpl();
	private UserService userServiceImpl = new UserServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeviceServlet() {
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
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonToSend = new JSONObject();
		String msg= "无附加信息";
		@SuppressWarnings("resource")
		ApplicationContext ctx = 
				new ClassPathXmlApplicationContext("applicationContext.xml");	
		deviceServiceImpl = (DeviceService) ctx.getBean("deviceServiceImpl");
		userServiceImpl = (UserService) ctx.getBean("userServiceImpl");
		boolean state = false;
		boolean exist = false;
		try {
//			jsonFromClient = userServiceImpl.streamToJson(request.getInputStream());
			//上面这一句得到client发过来的json包
			String account = request.getParameter("account");
			switch (request.getParameter("function").toString()) {
			case "deleteDevice":	
				//String params = "function=" + function + "&account=" + 
				//account + "&password=" + password+"&deviceId="+deviceInfo.getDeviceId()+"&deviceState";
				Integer id = Integer.valueOf(request.getParameter("deviceId"));
//				System.out.println(jsonToSend.toString()+password);
				Socketinfo deviceInfo = new Socketinfo();
				deviceInfo = deviceServiceImpl.findById(id);
				deviceServiceImpl.delete(deviceInfo);
//				deviceServiceImpl.deleteQuery(deviceInfo.getDeviceid());
				jsonToSend.put("state", true);
				jsonToSend.put("msg", msg);
				jsonToSend.put("account", account);
				break;
			case "getAll":
				SocketUtils.getInstance();
				JSONArray jsonArray = new JSONArray();
				Userinfo user = userServiceImpl.findByName(account);
				List<Socketinfo> list = deviceServiceImpl.findAll(user.getUserid());
				
				for(Socketinfo l:list){
//					if(l.getUserid()==user.getUserid()){
						JSONObject jsonObject = new JSONObject();
						jsonObject = deviceServiceImpl.deviceToJson(l);
						jsonArray.put(jsonObject);
//					}
				}
				jsonToSend.put("state", true);
				jsonToSend.put("account", account);
				jsonToSend.put("device", jsonArray);
				break;
			case "modifyDevice" ://获取用户信息
				Integer deviceId = Integer.valueOf(request.getParameter("deviceId"));
				String deviceState = request.getParameter("deviceState");
				Socketinfo device = new Socketinfo();
				device = deviceServiceImpl.findById(deviceId);

				device.setState(deviceState);
				msg="设备在线";
				deviceServiceImpl.modifyDevice(device);
				jsonToSend.put("state", true);
				jsonToSend.put("msg", msg);
				break;
			case "addDevice" :
//				JSONObject jsonFromClient = new JSONObject();
//				jsonFromClient = userServiceImpl.streamToJson(request.getInputStream());
				Userinfo user2 = userServiceImpl.findByName(account);
				Socketinfo deInfo = new Socketinfo();
				deInfo.setDeviceid(Integer.valueOf(request.getParameter("deviceId")));
				deInfo.setDevicename(request.getParameter("deviceName"));
				deInfo.setState(request.getParameter("deviceState"));
				deInfo.setAvailable(request.getParameter("deviceAvailable"));
				deInfo.setTiming(Integer.valueOf(request.getParameter("deviceTiming")));
				deInfo.setUserinfo(user2);
//				deviceServiceImpl.findAll();
				deviceServiceImpl.addDevice(deInfo);
				jsonToSend.put("state", true);
				jsonToSend.put("msg", msg);
				
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				jsonToSend.put("state", false);
				jsonToSend.put("msg", msg);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		out.write(jsonToSend.toString());
//		out.println(jsonObject.toString());
        out.flush();
        out.close();
	}

}
