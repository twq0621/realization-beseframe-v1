package com.realization.framework.communicate.nio;

import java.io.IOException;
import java.net.SocketAddress;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-19   下午08:11:54
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface Channel {

	/**
	 * 获取通道ID
	 * @return
	 */
    Long getId();
    
    /**
     * 关闭通道
     */
    void close()  throws IOException;
    
    /**
     * 获取本地地址
     * @return
     */
    SocketAddress getLocalAddress();
    
    /**
     * 获取远程地址
     * @return
     */
    SocketAddress getRemoteAddress();
    
    /**
     * 	写数据
     * 
    *@param content
     */
    void write(byte[] content) throws Exception;
    
    void flush() throws IOException;
}
