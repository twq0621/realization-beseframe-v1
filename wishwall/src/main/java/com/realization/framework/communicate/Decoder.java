package com.realization.framework.communicate;

import com.realization.framework.communicate.nio.Buffer;


/**
 * 	系统解包服务
 * 	
 * 
 * @author xu.jianpu
 *
 *  2012-10-24  下午04:48:28
 * 	 @version 1.0
 */
public interface Decoder {

    /** 解包 */
    Object decode(byte[] msg) throws Exception;
    
    /** 读取通讯通道的数据 */
    byte[] readBuffer(Buffer buffer);
}
