package com.realization.framework.communicate.nio;

/**
 * 消息包
 * @author xu.jianpu
 *
 *  2012-10-23  下午04:14:53
 * 	 @version 1.0
 */
public class MessageFact{
	public static enum EventType {
		EXCEPT,RECEIVE,SEND,CLOSE,CREATE,OPEN,IDLE,DESTROY,BOUND,UNBOUND
	}

	public final EventType type;	
	public final Channel session;	
	public final Object fact;	//消息内容
	
	/**
	 * 
	 * @param type	消息类型
	 * @param session	连接通道
	 * @param fact	消息内容
	 */
	public MessageFact(EventType type, Channel session, Object fact) {
		this.type = type;
		this.session = session;
		this.fact = fact;
	}
}
