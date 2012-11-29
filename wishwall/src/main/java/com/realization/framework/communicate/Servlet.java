package com.realization.framework.communicate;
/**
 * 服务单元
 * 
 * 		@quest 现在还不知道创建一个这样的类有什么意义？先建立，等以后理解了再添加
 * 		@ans   暂时理解是抽象类是为了抽象实现类之前的共同代码，共性操作。但是该实现类所具有的功能，也就是规范是无需要接口定义的。
 * 				接口可以多重继承，无状态。
 * 				接口是定义一种规范
 * @author xu.jianpu
 *
 *  2012-10-23  下午03:20:41
 * 	 @version 1.0
 */
public interface Servlet {
	enum Status{READY , RUNNING , PAUSE ,EXIT};
	
	Status getStatus();
	
	int getType();
	
	void start();
	
	void exit();
	
	boolean open();
}
