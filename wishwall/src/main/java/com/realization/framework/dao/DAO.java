package com.realization.framework.dao;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-22   下午04:19:18
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface DAO {

	/**
	 * 但是并"不保证"标识符(identifier主键对应的属性)被立刻填入到持久化实例中，标识符的填入可能被推迟到flush的时候。 
	*@param obj
	*@throws Exception
	 */
	public void pesist(Object obj) throws Exception;
	
	/**
	 * 会马上执行sql  insert 持久化到数据库
	*@param o
	*@return	持久化标识，一般是id
	*@throws Exception
	 */
	public Object save(Object o ) throws Exception;
	
	/**
	 * 合并。如果O已经存在，则合并实体，不存在则存储实体
	*@param o
	*@return
	*@throws Exception
	 */
	public Object merge(Object o ) throws Exception;
	
	/**
	 * 
	*@param <E>
	*@param sql
	*@param clz
	*@param args
	*@return	返回结果为对应实体，如果查询结果与传入实体不对应，抛出类型转换异常
	*@throws Exception
	 */
	public <E> E find(String sql ,Class<E> clz ,Object...args) throws Exception;
}
