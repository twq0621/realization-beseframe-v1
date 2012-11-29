package com.realization.framework.communicate;

import java.util.EventListener;
/**
 * 		消息监听器
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-24   上午11:04:13
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface MessageListener extends EventListener {

	/**
	 * 接收到一个消息事件.
	 * 
	 * @param event 消息事件.
	 */
	void receive(MessageEvent event) throws Exception;
}