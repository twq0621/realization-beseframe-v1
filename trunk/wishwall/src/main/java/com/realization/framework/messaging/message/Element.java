package com.realization.framework.messaging.message;

/**
 * 消息的元素.
 * 
 * @author <a href="mailto:zhengduan@angelshine.net">zhengduan</a>
 *
 */
public interface Element {

    /** 消息元素的值 */
    Object getValue();
    
    /** 消息元素占用字节数 */
    short getLength();
    
    /** 消息元素的类型 */
    byte getType();
}
