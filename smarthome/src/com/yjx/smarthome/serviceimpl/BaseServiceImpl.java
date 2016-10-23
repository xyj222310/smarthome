package com.yjx.smarthome.serviceimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yjx.smarthome.dao.BaseDao;
import com.yjx.smarthome.service.BaseService;
/**
 *  ���ڸ���ʵ�����create���������в�ͬ��ҵ���߼������紴���û�ǰ��Ҫ����˺��Ƿ��Ѿ����ڣ���
 *  ���԰��ඨ���abstract
 * ��create�������������abstract���ɾ����Service����ʵ��
 * 
 * @author XieYingjie
 *
 * @param <T>
 */
@Service(value="baseServiceImpl")
@Transactional
public  class  BaseServiceImpl<T> implements BaseService<T>{
	/**
     * ͨ��spring��IoCע�����
     */
	//����ǳ��ؼ�BaseService<T>:��RoleService��UserService��������
	//BaseRepepositry<T>:��UserRepository��RoleRepositry��������
	//���� BaseService<T>�� BaseRepepositry<T> �й�ϵ���ԣ��ó����������Ҳ���������Ĺ�ϵ
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
    //�ӵ�pageҳ��ʼ��ʾ���������ΪmaxResults
    public List<T> listPage(String hql, int page, int maxResults,
            Map<String, Object> map) {
        // TODO Auto-generated method stub
        return baseDaoImpl.list(hql, maxResults*(page-1), maxResults, map);
    }

}
