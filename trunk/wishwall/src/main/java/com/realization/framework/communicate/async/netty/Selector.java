package com.realization.framework.communicate.async.netty;


/**
 * Netty组件选择器
 * 
 * @author <a href="zhengduan@angelshine.net">zhengduan</a>
 * 
 * 
 */
public interface Selector {

    /**
     * 是否选择当前组件.
     * 
     * @param context 容器上下文.
     * @param key host:port
     * 
     */
    boolean select( String key);
}
