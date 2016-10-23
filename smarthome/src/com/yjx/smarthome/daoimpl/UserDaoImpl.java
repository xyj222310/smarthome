package com.yjx.smarthome.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yjx.smarthome.dao.UserDao;
import com.yjx.smarthome.moudel.Userinfo;

@SuppressWarnings("serial")
@Repository(value="userDaoImpl")
public  class UserDaoImpl extends BaseDaoImpl<Userinfo> implements UserDao {
	
//��ôд��ԭ�򣺼̳�basedao����ʹ��ͨ��dao������
//��ʵ��userdao��userdao���������дһЩר�õ�dao�������ڴ�ʵ��
//	private SessionFactory  sessionFactory = null;
//	private Class<?> EntityClass;
//	public UserDaoImpl() {
//        ParameterizedType type = (ParameterizedType) getClass()
//                .getGenericSuperclass();
//        this.setEntityClass((Class<?>) type.getActualTypeArguments()[0]);
//    }
//	public Class<?> getEntityClass() {
//		return EntityClass;
//	}
//	public void setEntityClass(Class<?> entityClass) {
//		EntityClass = entityClass;
//	}
	public UserDaoImpl() {   
    }  
	@SuppressWarnings("unchecked")
	public Userinfo findByName(String name) {
	        // TODO Auto-generated method stub
		List<Userinfo> lUser = null;
	    lUser = getSession()
    		.createQuery("from Userinfo userInfo where userInfo.account='"+name+"'")
    		.list();
	    if(lUser.size()==0){
	    	return null;
	    }
		return lUser.get(0);
	}
}
