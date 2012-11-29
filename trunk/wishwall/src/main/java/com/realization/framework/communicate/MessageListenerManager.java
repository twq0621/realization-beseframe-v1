package com.realization.framework.communicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.realization.framework.common.exception.UossException;
import com.realization.framework.core.context.InstanceClass;
import com.realization.framework.core.context.UossContext;


public abstract class MessageListenerManager {
final private static Log log = LogFactory.getLog(MessageListenerManager.class);
	
    /**
     * 已注册的消息监听器.
     * 
     * 	synchronizedList 	返回指定列表支持的同步（线程安全的）列表。为了保证按顺序访问，必须通过返回的列表完成所有对底层实现列表的访问。
			在返回的列表上进行迭代时，用户必须手工在返回的列表上进行同步

     * 	也就是取代vector，生成同步集合。而且为了保证同步集合的方位，必须手动设置在同步快中进行访问
     */
    protected final List<MessageListener> listeners = Collections.synchronizedList(new ArrayList<MessageListener>());	
    
	public List<MessageListener> getListeners() {
		return listeners;
	}
	
	//这里不是没有在同步块中执行吗？我试验过不在同步块中执行也没有错啊，不是会报ConcurrentModificationException吗？难道只是会出现意想不到的效果而不抛异常？
	public void addListeners(List<MessageListener> listeners) {
		this.listeners.addAll(listeners);
	}
	public void addListener(MessageListener listener) {
		this.listeners.add(listener);
	}
	public void clearListeners(Object listener) {
		this.listeners.clear();
	}
	
	
	protected UossContext context;
	/**
	 * 注入容器上下文.
	 * 
	 * @param context 容器上下文，由容器注入.
	 * 
	 */
    @Resource
	void setContext(UossContext context) {
    	this.context =context;
		try {
			for (InstanceClass<? extends MessageListener> mc: context.getInstanceClasses(MessageListener.class,null,null)) {
				if (null!=mc) {
					try {
						MessageListener ml = context.getInstance(mc.instanceClass,mc.name);
						addListener(ml);
					}
					catch (Exception e) {
						log.error("Occur error on create MessageListener for "+mc, e);
					}
				}
			}
		}
		catch (UossException e) {
			log.error("Occur error on get instance classes for MessageListener", e);
		}
	}
    
    protected boolean hasListener() {
        return !listeners.isEmpty();
    }

    /**
     * 消息事件通知
    *@param me
    *@throws Exception
     */
	protected void fireEvent(MessageEvent me) throws Exception {
		for (MessageListener ml: listeners) {
			try {ml.receive(me);}
            catch (Exception e) {
                handleException("Occur exception(s) on handling message!", e);
            }
		}
	}
	
	protected void handleException(String prompt, Exception e) throws Exception {
	    log.error(prompt, e);
	}
}
