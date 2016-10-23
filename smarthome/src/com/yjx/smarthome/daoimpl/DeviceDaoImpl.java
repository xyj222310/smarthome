package com.yjx.smarthome.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yjx.smarthome.dao.DeviceDao;
import com.yjx.smarthome.moudel.Socketinfo;

@SuppressWarnings("serial")
@Repository(value="deviceDaoImpl")
public  class DeviceDaoImpl extends BaseDaoImpl<Socketinfo> implements DeviceDao {
	public DeviceDaoImpl(){ 
    }  
	@SuppressWarnings("unchecked")
	public List<Socketinfo> findAll(Integer userid) {
		// TODO Auto-generated method stub
		List<Socketinfo> lDevice = null;
		lDevice = getSession().createQuery("from Socketinfo s where s.userinfo.userid = :userid")
				.setParameter("userid", userid)
//				.setParameter("userid",userid)
				.list();
//		lDevice = getSession().createSQLQuery("select * from smarthome.socketinfo where ownerid="+userid).list();
		if(lDevice.size()==0){
			return null;
		}
		return lDevice;
	}

	@Override
	public void delete(Socketinfo socket) {
		// TODO Auto-generated method stub
		try{
			this.getSession().delete(socket);
		}catch(Exception e){
			e.printStackTrace();
		}
//		.setParameter("deviceid", deviceId);
	}
}
