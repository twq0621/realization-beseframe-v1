package com.realization.framework.core.context;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-25   下午09:24:55
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class InstanceClass<T> implements Comparable<InstanceClass<?>>{

	public final String name ;	//这个是这个类实例化出的bean的注入到容器中的名字
	
	public final Class<? extends T> instanceClass;
	
	public InstanceClass(String name,Class<? extends T> clz){
		this.name = name;
		this.instanceClass = clz;
	}

	@Override
	public int compareTo(InstanceClass<?> o) {
		System.err.println(instanceClass.getName());
		return 1;
	}

}
