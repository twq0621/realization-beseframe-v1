package com.realization.framework.communicate;

import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

import com.realization.framework.messaging.IpcMessage;
/**
 * 消息事件对象.
 * 
 * 注意，与消息监听器不同，消息事件不是一个线程安全的对象，消息事件从线程池中发出，只能在单个线程内部使用.
 * 如果在一个线程安全的环境中，方法（或代码块）必须加同步锁机制（synchronized）.
 * 
 * 
 * 
 *
 *///TODO 为什么没有消息类型？   因为这就代表一个实际接收到的消息包，不是连接事件，所以不存在类型
public class MessageEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 内含消息.
	 */
	private final IpcMessage message;
	
	/**
	 * 属性组，可在消息事件的处理周期内共享属性.
	 *
	 *///TODO 怎么共享？
	private Map<String,Object> attributes = Collections.synchronizedMap(new HashMap<String,Object>());
	
	public MessageEvent(Object source, IpcMessage message) {
		super(source);
		this.message = message;
	}

	public IpcMessage getMessage() {
		return message;
	}

	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}

	public void clearAttribute() {
		attributes.clear();
	}
}
