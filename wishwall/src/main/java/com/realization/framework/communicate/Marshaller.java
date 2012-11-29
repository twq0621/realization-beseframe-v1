package com.realization.framework.communicate;

import com.realization.framework.common.exception.MarshallerException;
import com.realization.framework.messaging.IpcMessage;
import com.realization.framework.messaging.IpcMessageFactory;
import com.realization.framework.messaging.message.PackFactory;


/**
 * 编码解码器.
 * 
 * 		</br>&nbsp 是ipcMessage的编解码,将其他消息包封装为ipcmessage
 * 
 * 		P是一个消息事件
 *
 */
public interface Marshaller<P> {

	/**
	 * 解编.
	 * 
	 * @param pack 原生对象，通讯包.
	 * 
	 * @param 制定编码工厂
	 * 
	 * @return 返回解编对象.
	 * 
	 */
	IpcMessage unMarshal(P pack, IpcMessageFactory factory) throws MarshallerException;
	
	/**
	 * 编码成通讯包，以更直接发送.
	 * 
	 * @param msg 组包.
	 * 
	 * @throws Exception
	 */
	P marshal(IpcMessage msg, PackFactory<P> factory) throws MarshallerException;
}
