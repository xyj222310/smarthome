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
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonToSend = new JSONObject();
		String msg= "�޸�����Ϣ";
		@SuppressWarnings("resource")
		ApplicationContext ctx = 
				new ClassPathXmlApplicationContext("applicationContext.xml");	
		userServiceImpl = (UserService) ctx.getBean("userServiceImpl");
//		String userAccount = request.getParameter("id");
//		System.out.print(userAccount+"������������������");
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
			//������һ��õ�client��������json��
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			switch (request.getParameter("function").toString()) {
			case "login"://��½
				exist = userServiceImpl.userExist(account);
				if(exist){
					state = userServiceImpl.login(account,password);
					if(state){msg="�û���½�ɹ�";}
				}
//				System.out.println(jsonToSend.toString()+password);
				jsonToSend.put("state", state);
				jsonToSend.put("msg", msg);
				jsonToSend.put("exist", exist);
				jsonToSend.put("account", account);
				break;
			case "register"://ע��
				Userinfo user = new Userinfo();
				user.setAccount(account);
				user.setUserpass(password);
				exist = userServiceImpl.userExist(account);
				if(exist==false){
					state = userServiceImpl.register(user);
					msg="�û�ע��ɹ�";
				}
				jsonToSend.put("state", state);
				jsonToSend.put("exist", exist);
				jsonToSend.put("msg", msg);
				jsonToSend.put("account", user.getAccount());
				break;
			case "getUser" ://��ȡ�û���Ϣ
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
