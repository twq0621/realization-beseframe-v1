package com.realization.wishwall.init;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.realization.framework.common.util.security.DESUtils;
/**
 * 	TODO 需要注入吗？
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-4   下午02:07:53
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {	
	
	//TODO 怎么使用资源文件替代硬编码?
	private String[] encryptPropNames ={"jdbc.username","jdbc.password"};
	
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {		
		if(isEncryptProp(propertyName)){
			String decryptValue = DESUtils.getDecryptString(propertyValue);
			System.out.println(decryptValue);
			return decryptValue;
		}else{
			return propertyValue;
		}
	}
	
	/**
	 * 判断是否是加密的属性
	 * @param propertyName
	 * @return
	 */
	private boolean isEncryptProp(String propertyName){
		for(String encryptPropName:encryptPropNames){
			if(encryptPropName.equals(propertyName)){
				return true;
			}
		}
		return false;
	}
}
