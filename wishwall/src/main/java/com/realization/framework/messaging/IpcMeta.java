package com.realization.framework.messaging;

import java.util.Map;
/**
 * 
 * 	消息元数据
 * 
 * @author xu.jianpu
 *
 *  2012-11-13  上午08:41:38
 * 	 @version 1.0
 */
public interface IpcMeta {
	 /** 取回消息体 */
    Map<String,Object> getMsgData();
    
    /** 等待延时(秒,小于或等于0表示无需回复) */
    int waitingDuration();
    
    /** 等待延时(秒) */
    void setWaitDuration(int time);
    // --
    
    /**
     * 取回消息头信息.
     * 
     * @param name 消息头的项名.
     * 
     */
    Object getMsgHead(String name);
    
    /**
     * 设置消息头信息.
     * 
     * @param name 消息头的项名.
     * @param value 消息头的项值.
     * 
     */
    void setMsgHead(String name, Object value);
    
    /**
     * 取回所有消息头.
     * 
     */
    Map<String,Object> getMsgHeads();
    
    /**
     * 取回消息体信息.
     * 
     * @param name 消息体的项名.
     * 
     */
    Object getMsgBody(String name);
    
    /**
     * 设置消息体信息.
     * 
     * @param name 消息体的项名.
     * @param value 消息体的项值.
     * 
     */
    void setMsgBody(String name, Object value);

    
    /**
     * 取回消息缓存.
     * 
     * @param name 消息缓存的项名.
     * 
     */
    Object getMsgAttribute(String name);
    
    /**
     * 设置消息缓存.
     * 
     * @param name 消息缓存的项名.
     * @param value 消息缓存的项值.
     * 
     */
    void setMsgAttribute(String name, Object value);
}
