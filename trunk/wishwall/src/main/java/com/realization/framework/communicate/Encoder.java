package com.realization.framework.communicate;
/**
 * 	系统组包服务
 * 
 * 		这里的组包和解包是相对与客户端来言的。从管道里面读取到的是包，需要进行解包。
 * 		发送到管道的需要进行封包编码，和一版的理解还不一样。
 * 
 * @author xu.jianpu
 *
 *  2012-10-24  下午04:51:39
 * 	 @version 1.0
 */
public interface Encoder {
	 byte[] encode(Object msg) throws Exception;
}
