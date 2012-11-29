package com.realization.wishwall.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author xiai_fei
 * 
 * @create-time 2012-11-20 下午09:18:38
 * 
 * @version 1.0
 * @description realization-BaseFrame
 * @版权所有 Realization 团队
 */
public abstract class BaseDao<T>  {
	
//	@Resource SessionFactory sessionFactory;

	@Resource
	protected HibernateTemplate hibernateTemplate;

	private Class<T> entityClass;

	private String entityClassName;

	@SuppressWarnings("unchecked")
	public BaseDao() {
		if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
			entityClass = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
			entityClassName = entityClass.getSimpleName();
		}
	}

	
	public void pesist(Object obj) throws Exception{
		 hibernateTemplate.persist(obj);
	}
	
	public Object save(Object obj )throws Exception{
		 return hibernateTemplate.save(obj);
	}
	/**
	 * 释放查询管理器
	 * 
	 * @param em
	 */
	protected void releaseManager(EntityManager em) {
		if (null != em) {
			em.clear();
			em.close();
		}
	}

	/**
	 * 根据ID查询实体
	 */
	public T findById(Integer id) {
		T entity = (T) findById(entityClass, id);
		return entity;
	}

	
	public Object merge(Object o ) throws Exception{
		return hibernateTemplate.merge(o);
	}
	
	
	
	/**
	 * 
	*@param <E>
	*@param sql
	*@param cls
	*@param args
	*@return
	 */
	public <E> E find(final String sql , Class<E> cls , final  Object...args) {
		
		return hibernateTemplate.execute(new HibernateCallback<E>() {

			@SuppressWarnings("unchecked")
			@Override
			public E doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(sql);
				int i = 0 ;
				for(Object o : args){
					query.setParameter(i, o);
					i++;
				}
				List<E> list = query.list();
				if(list!=null&&list.size()>0) return list.get(0);
				return null;
			}
		});
	}
	
	public <E> E findById(Class<E> cls, Integer id) {
		return (E) findById(cls, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> queryByProperty(String propertyName, Object value) {
		String queryString = "select model from " + entityClassName
				+ " as model where model." + propertyName + "= ?";
		return hibernateTemplate.find(queryString, value);
	}

	/**
	 * 根据属性查询，自定义表
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> queryByProperty(Class<E> cls, String propertyName,
			Object value) {
		String queryString = "select model from " + cls.getSimpleName()
				+ " as model where model." + propertyName + "= ?";
		return hibernateTemplate.find(queryString, value);
	}

	/**
	 * 条件查询
	 * 
	 * @param modelName
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <E> List<E> queryList(final String hql, final Object... args) {
		return hibernateTemplate.executeFind(new HibernateCallback<T>() {

			@Override
			public T doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				int i = 0 ;
				for(Object o : args){
					query.setParameter(i+1, o);
					i++;
				}
				List<E> list = query.list();
				return  (T) list;
			}
			
		});
	}
}
