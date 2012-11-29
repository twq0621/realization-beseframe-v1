package com.realization.framework.core.context;

/**
 * 	类加载过滤器
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-25   下午12:05:56
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface ClassFilter {

	/**
	 * 是否接受当前类
	*@param advice
	*@param key
	*@return
	 */
	public boolean accpet(Class<?> advice, String key);
}
