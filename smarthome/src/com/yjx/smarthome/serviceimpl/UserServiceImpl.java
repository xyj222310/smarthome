package com.yjx.smarthome.serviceimpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yjx.smarthome.dao.UserDao;
import com.yjx.smarthome.moudel.Userinfo;
import com.yjx.smarthome.service.UserService;

/**
 *  由于各个实体类的create（）方法有不同的业务逻辑（例如创建用户前需要检查账号是否已经存在），
 *  所以把类定义成abstract
 * 把create（）方法定义成abstract，由具体的Service单独实现
 * 但是觉得这样不好，所以每个service和dao 具体实现不同的功能就分开写，并且继承baseimpl 
 * @author XieYingjie
 * @param <User>
 */
@Service(value="userServiceImpl")
@Transactional
public class  UserServiceImpl extends BaseServiceImpl<Userinfo> implements UserService{
	@Resource(name="userDaoImpl")
	@Qualifier
	private UserDao userDaoImpl;
	
//	public UserServiceImpl(BaseDao<User> basedao) {
//		// TODO Auto-generated constructor stub
//		this.userDao =  (UserDao)basedao;//null
//	}
	public UserServiceImpl() {
		super();
	}
//	public UserDao getUserDao() {
//		return userDao;
//	}
//	public void setUserDao(UserDao userDao) {
//		this.userDao = userDao;
//	}
	public Userinfo findByName(String name){
	        // TODO Auto-generated method stub
        return userDaoImpl.findByName(name);
    }
	
	public boolean userExist(String account){
		if(userDaoImpl.findByName(account) ==null){
			return false;
		}
		return true;
	}
	
	public boolean login(String account,String password) {
		// TODO Auto-generated method stub
		Userinfo user = new Userinfo();
		user = userDaoImpl.findByName(account);
		if(user.getUserpass().equals(password)){
			return true;
		}
		return false;
	}

	public boolean register(Userinfo user) {
		// TODO Auto-generated method stub
		userDaoImpl.create(user);
//		userDaoImpl.save(user);
		return true;
	}

	@Override
	public JSONObject userToJson(Userinfo user) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId",user.getUserid());
		jsonObject.put("account",user.getAccount());
		jsonObject.put("icon",user.getIcon());
		jsonObject.put("userPass",user.getUserpass());
		jsonObject.put("phone",user.getPhone());
		jsonObject.put("sex",user.getSex());
		return jsonObject;
	}
	
	@Override
	public JSONObject streamToJson(InputStream inputStream) throws IOException, JSONException {
		// TODO Auto-generated method stub
		BufferedReader bf=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
		String jsonString = IOUtils.readLines(bf).toString();// 把字符数组转换成字符串
		JSONObject jsonObject = new JSONObject(jsonString);
		return jsonObject;
	}

	@Override
	public Userinfo jsonToUser(JSONObject jsonObject) throws JSONException {
		// TODO Auto-generated method stub
		Userinfo user = new Userinfo();
		user.setUserid(jsonObject.getInt("userId"));
		user.setAccount(jsonObject.getString("account"));
		user.setIcon(jsonObject.getString("icon"));
		user.setUserpass(jsonObject.getString("userPass"));
		user.setPhone(jsonObject.getString("phone"));
		user.setSex(jsonObject.getString("sex"));
		return user;
	}
}