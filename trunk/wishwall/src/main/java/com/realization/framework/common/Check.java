/**
 * 
 */
package com.realization.framework.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-7   下午07:48:44
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Retention(RetentionPolicy.RUNTIME)  //保留期限为：运行时
@Target(ElementType.TYPE)   //类，接口，注解类，enum声明处
public @interface Check {
	
	String[] value() default {};
	
	String[] byteElement()  default {};
	
	String[] intElement()  default {};

}
