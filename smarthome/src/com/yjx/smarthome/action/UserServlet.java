package com.yjx.smarthome.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yjx.smarthome.moudel.Userinfo;
import com.yjx.smarthome.service.UserService;
import com.yjx.smarthome.serviceimpl.UserServiceImpl;

/**
 * Servlet implementation class TestServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userServiceImpl = new UserServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
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
		userServiceImpl = (UserService) ctx.getBean("userServiceImpl");
//		String userAccount = request.getParameter("id");
//		System.out.print(userAccount+"看这里这里这里这里");
//		Userinfo u = new Userinfo();
//		u = userServiceImpl.findByName(userAccount);
//		try {
//			jsonToSend.put("ww", u.getAccount());
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		out.write(jsonToSend.toString());
//		String password  = request.getParameter("password");
//		String flag = request.getParameter("function");
		boolean state = false;
		boolean exist = false;
		try {
//			jsonFromClient = userServiceImpl.streamToJson(request.getInputStream());
			//上面这一句得到client发过来的json包
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			switch (request.getParameter("function").toString()) {
			case "login"://登陆
				exist = userServiceImpl.userExist(account);
				if(exist){
					state = userServiceImpl.login(account,password);
					if(state){msg="用户登陆成功";}
				}
//				System.out.println(jsonToSend.toString()+password);
				jsonToSend.put("state", state);
				jsonToSend.put("msg", msg);
				jsonToSend.put("exist", exist);
				jsonToSend.put("account", account);
				break;
			case "register"://注册
				Userinfo user = new Userinfo();
				user.setAccount(account);
				user.setUserpass(password);
				exist = userServiceImpl.userExist(account);
				if(exist==false){
					state = userServiceImpl.register(user);
					msg="用户注册成功";
				}
				jsonToSend.put("state", state);
				jsonToSend.put("exist", exist);
				jsonToSend.put("msg", msg);
				jsonToSend.put("account", user.getAccount());
				break;
			case "getUser" ://获取用户信息
				Userinfo userinfo = new Userinfo();
				userinfo = userServiceImpl.findByName(account);
				JSONArray jsonArray = new JSONArray();
				jsonArray.put(userServiceImpl.userToJson(userinfo));
				jsonToSend.put("state", state);
				jsonToSend.put("user", jsonArray);
				break;
			case "modifyUser" :
				Userinfo modifyUser = new Userinfo();
//				JSONObject jsonFromClient = new JSONObject();
//				jsonFromClient = userServiceImpl.streamToJson(request.getInputStream());
				userServiceImpl.save(modifyUser);
				jsonToSend.put("state", state);
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		out.write(jsonToSend.toString());
//		out.println(jsonObject.toString());
        out.flush();
        out.close();
	}

}
