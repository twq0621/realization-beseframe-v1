package com.realization.framework.rule;

import java.util.concurrent.Future;

import com.realization.framework.messaging.IpcMessage;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-13   下午10:37:49
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface MessageSender {

	/**
	 * 异步消息发送方法
	*@param url
	*@param msg
	 */
	public void send(String url ,IpcMessage msg);
	
	/**
	 * 同步消息发送方法
	*@param url
	*@param msg
	*@return  Future<?>
	 */
	public Future<?> sendSync (String url ,IpcMessage msg);
}
