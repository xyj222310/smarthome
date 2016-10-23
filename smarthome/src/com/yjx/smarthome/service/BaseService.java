package com.yjx.smarthome.service;

import java.util.List;
import java.util.Map;
/**
 * 这个父接口不能作为bean在applicationContext.xml中，因为serviceImpl是虚函数
 * @author XieYingjie
 *
 * @param <T>
 */
public interface  BaseService<T> {
	
	/**
     * 根据id和对象类查找实体
     */
    public T findById( int id);
//    public T findByName(String name);

    /**
     * 创建实体
     */
    public void create(T t);

    /**
     * 保存实体
     */
    public void save(T t);

    /**
     * 删除实体
     */
    public void delete(T t);

    /**
     * 分页实体
     */
    public List<T> list(String hql, int firstResult, int maxResults,
            Map<String, Object> map);

    int getTotalCount(String hql, Map<String, Object> map);

    public List<T> list(String hql, Map<String, Object> map);

    public  List<T> listPage(String hql, int page, int maxResults,
            Map<String, Object> map);
}
