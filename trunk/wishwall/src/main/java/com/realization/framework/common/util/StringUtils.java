package com.realization.framework.common.util;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-25   下午09:53:22
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class StringUtils {

	public static String getLowerName(String name ){
		String first =name.substring(0,1);
		return first.toLowerCase()+name.substring(1, name.length());
		
	}
	
	public static String getClassBeanName(String name ){
		name = name.substring(name.lastIndexOf(".")+1, name.length());
		String first =name.substring(0,1);
		return first.toLowerCase()+name.substring(1, name.length());
		
	}
}
