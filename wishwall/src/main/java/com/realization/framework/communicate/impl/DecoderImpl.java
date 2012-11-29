package com.realization.framework.communicate.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.realization.framework.communicate.Decoder;
import com.realization.framework.communicate.nio.Buffer;
import com.realization.framework.messaging.message.Message;

/**
 * 		
 * 	默认输入流编码封包器
 * 
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-23   上午08:42:41
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class DecoderImpl implements Decoder{
	
	private static final Log log =LogFactory.getLog(DecoderImpl.class);

	@Override
	public Object decode(byte[] msg) throws Exception {
		if(msg==null) return null ;
		Message recevieMsg= new Message((short)(msg.length),msg);
		return recevieMsg;
	}

	@Override
	public byte[] readBuffer(Buffer buffer) {
		short length = buffer.getShort();
		if(length<0)return null ;
		byte[] bs = new byte[length];
		if(buffer.read(bs, 0, length)<0){
			log.error("  ==== the buffer read less than length ");
			return null;
		}
		return bs;
	}

}
