package com.realization.framework.communicate.async.netty;

import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.HeapChannelBufferFactory;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ChildChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.WriteCompletionEvent;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.realization.framework.communicate.ConnectionListener;
import com.realization.framework.communicate.Marshaller;
import com.realization.framework.communicate.MessageObserver;
import com.realization.framework.communicate.nio.Daemon;
import com.realization.framework.communicate.nio.MessageFact;
import com.realization.framework.communicate.nio.impl.ChannelImpl;
import com.realization.framework.core.context.Description;
import com.realization.framework.core.context.InstanceClass;
import com.realization.framework.core.context.UossContext;
import com.realization.framework.messaging.IpcMessage;
import com.realization.framework.messaging.message.Message;
/**
 * 	SOCKET 消息服务
 * 
 * @author xu.jianpu
 *
 *  2012-10-23  下午04:16:27
 * 	 @version 1.0
 */
@Description(3)
public class MessageReceiver extends MessageObserver<Message> implements ChannelUpstreamHandler {

	private final static Log log =LogFactory.getLog(MessageReceiver.class);
	
	final private InetSocketAddress binding ;
	
	final private ChannelGroup allChannels = new DefaultChannelGroup(" socket.server");
	
	/*
	 * 要对channelhandler进行排序
	 * 		因为上升流（也就是输入流）通过handler的处理顺序就是这里指定的
	 */
	private Collection<ChannelHandler> handlers ;
	
	private Collection<ConnectionListener> connectionListeners ;
	
	private org.jboss.netty.channel.Channel channel;
	
	/** 指定channelhandler排序方式*/
	@Resource(name="channelHandlerComparator") Comparator<InstanceClass<? extends ChannelHandler>> channelHandlerComparator;
	
	@Resource
	 private Marshaller<Message> messageMarshaller;
	 
	 @Resource ApplicationContext ctx;
	
	private Daemon daemon ;
	
	private String port ;
	
	public MessageReceiver(String port ,String id, int poolSize) {
		super(id, poolSize);
		this.port = port;
		this.binding = new InetSocketAddress(new Integer(port));
	}

	/**
	 * 加载所有channelhandler，指定过滤和排序规则
	*@param context
	 */

	public void registerHandler( ){
		if(null==handlers)try{
			handlers = new ArrayList<ChannelHandler>();
			Collection<InstanceClass<? extends ChannelHandler>> cl = context.getInstanceClasses(ChannelHandler.class, null, channelHandlerComparator);
			for(InstanceClass<? extends ChannelHandler> c : cl){
				if(c.instanceClass.isInstance(this)){
					handlers.add(this);
					continue;
				}
				ChannelHandler e = context.getInstance(c.instanceClass, c.name);
				if(!handlers.contains(c)){
					if(e instanceof Selector && ((Selector)e).select(port)){
						handlers.add(e);
					}
				}
			}
			System.err.println(cl);
		}catch (Exception e) {
			log.error(" occur error on get instance class for connectionListener ", e);
		}
	}
	
	@Override
	public void run() {
		while(!open()){	//有可能不可以一次就可以成功监听
			try {Thread.sleep(1000);}
			catch (InterruptedException e) {}
		}
	}


	/**
	 *注册链接监听器
	*@return
	 */
	protected boolean registerConnectionListener(){
		if(null==connectionListeners)try{
			connectionListeners = new ArrayList<ConnectionListener>();
			 String[] ns = ctx.getBeanNamesForType(ConnectionListener.class);
			 for(String n : ns){
				 connectionListeners.add(ctx.getBean(n, ConnectionListener.class));
			 }
			return true ;
		}catch (Exception e) {
			log.error(" occur error on get instance class for connectionListener ", e);
		}
		return true;
	}
	
	@Override
	public void start() {
		executor.execute(this);
	}

	
	
	@Override
	public void exit() {
		super.exit();
		if(!allChannels.isEmpty()) {
			log.debug("disposed bootstrap at " + id + "  start ...  ");
			channel.unbind();
			channel.disconnect();
			channel.close().addListener(new ChannelFutureListener() {
				
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if(future.isSuccess()){
						future.getChannel().close();
						future.getChannel().getFactory().releaseExternalResources();
					}
				}
			});
			allChannels.close().awaitUninterruptibly();
			log.debug("disposed bootstrap at " + id + "  O.K.  ");
		}
	}



	@Override
	public boolean open() {

		if(!registerConnectionListener()){
			return false ;
		}
		if(null==channel||!channel.isOpen()){
			 if (null!=channel) {	
	                channel.unbind();//通道已经被占用，解除这个通道
	                channel.close();
	              //这里还是不明白，当channel关闭之后，group会自用移除的，为什么还要手动
	              //通过实验，确定group会自动移除关闭后的session
//	                allChannels.remove(channel);	
	         }
			
			ServerBootstrap bootStap = new ServerBootstrap(new NioServerSocketChannelFactory(executor, executor));
			//用通道工厂为该通道绑定一些通道处理类.每当有一个客户端请求过来，都会进入此方法，用pipeline工厂创建对应的管道并指定handlers
			bootStap.setPipelineFactory(new ChannelPipelineFactory() {
				@Override
				public ChannelPipeline getPipeline() throws Exception {
					if(null==handlers)return Channels.pipeline(MessageReceiver.this);
					//创建一个管道并指定通道处理类
					ChannelHandler[] ch = handlers.toArray(new ChannelHandler[handlers.size()]);
					ChannelPipeline pipline = Channels.pipeline(ch);
					return pipline;
				}
			});
			HeapChannelBufferFactory bf = new HeapChannelBufferFactory(ByteOrder.LITTLE_ENDIAN);//大小端模式
			bootStap.setOption("bufferFactory", bf);
			bootStap.setOption("child.bufferFactory", bf);
			/*
			 * 学习框架的地方  这个channel代指服务器管道。
			 * 上面所进行的handler设置也是对服务器这条管道进行设置的，相当于socket的new socket，但socket是同步的，这里是异步的。。。。
			 * 	而其他的channel就相当于socket中的与客户端的socket
			 */
			allChannels.add(channel=bootStap.bind(binding)); 
			log.info(" listening on port " + port);
		}
		return channel.isOpen() ;
	}

	@Override
	public void handleUpstream(ChannelHandlerContext context, ChannelEvent e)
			throws Exception {
		org.jboss.netty.channel.Channel channel = e.getChannel(); //每个事件都有对应channel的句柄
		if( e instanceof MessageEvent){
			/*
			 * 消息事件。  包括收到消息和发送消息事件。
			 * 	//虽然messageevent是对应着上送和下发两个消息事件，但是这个是上送流事件处理
			 * ，也就是说只有上送流事件才会发送到这里来，所以没有下降流事件，所以直接将类型判断为REVEVICE
			 */			
			Object message = ((MessageEvent) e).getMessage();
			if(null!=daemon)daemon.checkResponse(message);	//守护线程这里还是不大懂
			execute(MessageFact.EventType.RECEIVE, channel,message);
		}else if( e instanceof WriteCompletionEvent){
			
		}else if(e instanceof ChildChannelStateEvent){	//子通道建立，创建对应的channel---------------
			ChildChannelStateEvent evt = (ChildChannelStateEvent)e ;
			org.jboss.netty.channel.Channel cc = evt.getChildChannel();
			if(cc.isOpen()){	//子通道开启
				execute(MessageFact.EventType.OPEN,cc,null);
				allChannels.add(cc);
			}else{		//子通道关闭
				execute(MessageFact.EventType.CLOSE,cc,null);
				allChannels.remove(cc);
			}
			context.sendUpstream(e);
		}else if( e instanceof ChannelStateEvent){	//通道状态事件，指的是服务器通道
			ChannelStateEvent evt = (ChannelStateEvent)e ;
			switch(evt.getState()){
			case OPEN:
				if(Boolean.TRUE.equals(evt.getValue())){
					execute(MessageFact.EventType.OPEN, channel, null);
					   allChannels.add(channel);	//这里应该会替换ready时的channel，可以debug或者尝试不添加此channel，应该不影响
				}
				else{
					execute(MessageFact.EventType.CLOSE, channel, null);
					  allChannels.remove(channel);
				}
				break ;
			case BOUND:	//绑定连接地址
				if(evt.getValue()!=null){	//valuse = SocketAddress	The channel is bound to a local address.
					   execute(MessageFact.EventType.BOUND, channel, null);
				}
				else{
				    execute(MessageFact.EventType.UNBOUND, channel, null);
				}
				break;
			case CONNECTED:	//链接事件，不大懂
				if(evt.getValue()!=null){
				    execute(MessageFact.EventType.CREATE, channel, null);
				}else{
				    execute(MessageFact.EventType.DESTROY, channel, null);
				}
				break ;
			case INTEREST_OPS:	//只单向变为idle 吗？
				   execute(MessageFact.EventType.IDLE, channel, evt.getValue());
				 break ;
			default:
				context.sendUpstream(e);
			}
		}
		else if(e instanceof ExceptionEvent){
			log.error("occut error on session : " + channel.getId(), ((ExceptionEvent) e).getCause());
			context.sendUpstream(e);
		}
		else{
			context.sendUpstream(e);		//传给下一个channelhandler进行处理
		}
		
	}

	/**
	 *  	自定义的事件机制，连接事件通知
	 *  	
	 *  		<br/>注意和消息监听器的区别，消息监听器在messageListenerManage中定义。
	 *  
	 * @param type
	 * @param channle
	 * @param fact
	 */
	protected void fireConnectionEvet(ConnectionListener.TYPE type , Channel channle , Object fact){
		com.realization.framework.communicate.nio.Channel  channel = new ChannelImpl(channle,daemon);
        for (ConnectionListener cl: connectionListeners) {//TODO question 没找到有监听者的实现啊。
            if (cl.accept(ConnectionListener.SITE.SERVER, type)) {
                try {cl.notify(type, channel, fact);}
                catch(Exception e) {
                    log.error("Occur exception on fire connection event - "+type, e);
                }
            }
        }
	}
	
	/**
	 * 执行通道时间处理
	*@param type	消息类型
	*@param session		通道
	*@param fact	消息实体
	 */
	protected void execute(final MessageFact.EventType type , final org.jboss.netty.channel.Channel session , final Object fact){
		try{
			executor.execute(new Runnable(){//多线程处理回复消息
				  
				@Override
				public void run() {
					com.realization.framework.communicate.nio.Channel channel = new ChannelImpl(session,daemon);
					 try{
						 IpcMessage ipcMessage = convert( (Message) fact);
						 if(null!=ipcMessage){
							  com.realization.framework.communicate.MessageEvent me = new com.realization.framework.communicate.MessageEvent(MessageReceiver.this,ipcMessage);
						        me.setAttribute("nio.session", channel);
						        me.setAttribute("nio.type", type);
						        me.setAttribute("nio.value", fact);
	                            fireEvent(me);
						 }
					 }catch (Exception e) {
						 try {handleException("Occur exception(s) on message reading thread!", e);}
	                        catch(Exception ex) {}
					}
					
				}
				
			});
		}catch (Exception e) {
			log.error("Occur error(s) when listen the message", e);
		}
	}
	
	@Override
	@Deprecated
	protected Message accept() throws Exception {
		return null;
	}

	@Override
	protected IpcMessage convert(Message message) throws Exception {
		return messageMarshaller.unMarshal(message,null);
	}

}
