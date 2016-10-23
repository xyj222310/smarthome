package com.yjx.smarthome.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yjx.smarthome.dao.BaseDao;
import com.yjx.smarthome.service.BaseService;
/**
 *  由于各个实体类的create（）方法有不同的业务逻辑（例如创建用户前需要检查账号是否已经存在），
 *  所以把类定义成abstract
 * 把create（）方法定义成abstract，由具体的Service单独实现
 * 
 * @author XieYingjie
 *
 * @param <T>
 */
@Service(value="baseServiceImpl")
@Transactional
public  class  BaseServiceImpl<T> implements BaseService<T>{
	/**
     * 通过spring的IoC注射进来
     */
	//这里非常关键BaseService<T>:有RoleService和UserService两的子类
	//BaseRepepositry<T>:有UserRepository和RoleRepositry两个子类
	//由于 BaseService<T>和 BaseRepepositry<T> 有关系所以，得出下面的子类也存在这样的关系
    private BaseDao<T> baseDaoImpl ; 
    public BaseServiceImpl() {
    }
//    public BaseServiceImpl(BaseDao<T> basedao){
//    	this.dao = basedao;
//    }
//    public BaseDao<T> getDao() {
//        return dao;
//    }
//
//    public void setDao(BaseDao<T> dao) {
//        this.dao = dao;
//    }

    @Override
    public T findById(int id) {
        // TODO Auto-generated method stub
        return baseDaoImpl.findById(id);
    }
    
	@Override
    public void create(T t){
		baseDaoImpl.create(t);
    };
    
    @Override
    public void save(T t) {
        // TODO Auto-generated method stub
    	baseDaoImpl.save(t);
    }

    @Override
    public void delete(T t) {
        // TODO Auto-generated method stub
    	baseDaoImpl.delete(t);
    }

    @Override
    public List<T> list(String hql, Map<String, Object> map) {
        // TODO Auto-generated method stub
        return baseDaoImpl.list(hql, map);
    }
    
    @Override
    public int getTotalCount(String hql, Map<String, Object> map) {
        // TODO Auto-generated method stub
        return baseDaoImpl.getTotalCount(hql, map);
    }

    @Override
    public List<T> list(String hql, int firstResult, int maxResults,
            Map<String, Object> map) {
        // TODO Auto-generated method stub
        return baseDaoImpl.list(hql, firstResult, maxResults, map);
    }

    @Override
    //从第page页开始显示，最大数据为maxResults
    public List<T> listPage(String hql, int page, int maxResults,
            Map<String, Object> map) {
        // TODO Auto-generated method stub
        return baseDaoImpl.list(hql, maxResults*(page-1), maxResults, map);
    }

}
