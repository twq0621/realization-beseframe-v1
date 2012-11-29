package com.realization.framework.communicate;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.realization.framework.messaging.IpcMessage;


/**
 * 	消息接收基类
 * 
 * @author xu.jianpu
 *
 *  2012-10-23  下午03:11:28
 * 	 @version 1.0
 */
public abstract class MessageObserver<M> extends MessageListenerManager implements Runnable , Servlet{
	final private static Log log = LogFactory.getLog(MessageObserver.class);
	
	protected volatile boolean exit ;
	
	protected final String id ;
	
	protected final int poolSize ;
	
	protected Executor executor = Executors.newCachedThreadPool();
	
	public MessageObserver(String id , int poolSize ){
		this.id = id;
		this.poolSize = poolSize;
	}

	@Override
	public Status getStatus() {
		return exit?Status.PAUSE:Status.RUNNING;
	}
	
    public int getType() {
        return Integer.parseInt(id);
    }
	
	
	@Override
	public void exit() {
		log.debug(" ========== service : + " + id + " exit ; ");
		exit = true;
		close();
	}

	/**
	 * 关闭服务，释放连接资源.
	 * 
	 * 		服务推出时自动调用
	 * 
	 */
	protected void close() {}
	
	/**
	 * 接收一个消息(Message或Socket)，以阻塞方式或监听方式.
	 * 
	 * @return 返回消息(Message或Socket).
	 * 
	 * @throws Exception 消息接收异常，此情况下存在消息包被丢弃的可能性.
	 * 
	 */
	protected abstract M accept() throws Exception;
	
	/**
	 * 转换，将收到的M转换为包装的对象.
	 * 
	 * @param message 收到的通讯包.
	 * @return 返回转换后的包装对象，IcpMessage.
	 * 
	 */
	protected abstract IpcMessage convert(M message) throws Exception;
}
