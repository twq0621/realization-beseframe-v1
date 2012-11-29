package com.realization.framework.core.context;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.realization.framework.common.exception.UossException;

/**
 * 	系统容器
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-23   下午11:18:23
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public interface UossContext extends ApplicationContext{
	
	/**
	 * 框架容器初始化
//	*@throws UossException
//	 */
//	public void contextInitialized() throws UossException;
	
	public void loadConfigBean();
	
	//TODO 日后要实现排序和过滤
	public <T> List<T> getInstanceClasses(Class<T> clz);
	
	
	public <T> T getInstance(Class<T> clz,String name);
	/**
	 * 	获取cllz所有子类或者所有实现cllz接口的类。
	*@param <T>
	*@param cllz
	*@param filter	类过滤器，根据过滤规则过滤实现类
	*@param comparator	对返回实现类的排序规则
	*@return
	 */
	public <T> Collection<InstanceClass<? extends T>> getInstanceClasses(Class<T>cllz , ClassFilter filter , Comparator<InstanceClass<? extends T>> comparator) throws UossException;
}
