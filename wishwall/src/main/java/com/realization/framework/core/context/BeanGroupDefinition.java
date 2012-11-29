package com.realization.framework.core.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 		bean组描述器
 * 			相同接口或者超类的实现类，用不用的名字区分
 * 		
 *  @author xiai_fei
 *
 *  @create-time	2012-11-24   上午10:02:09
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class BeanGroupDefinition<T>{

	private String name ;
	
	private Map<String,T> beans =new HashMap<String, T>();
	
	public void setBean(String name , T bean){
		beans.put(name, bean);
	}
	
	public T getBean(String name ){
		return beans.get(name);
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
	
	
	
}
