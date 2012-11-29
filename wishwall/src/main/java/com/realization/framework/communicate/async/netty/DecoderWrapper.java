package com.realization.framework.communicate.async.netty;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import org.springframework.stereotype.Service;

import com.realization.framework.communicate.Decoder;
import com.realization.framework.communicate.nio.BufferReader;
import com.realization.framework.core.context.Description;
import com.realization.framework.core.context.UossContext;
@Service
@Description(1)
public class DecoderWrapper extends OneToOneDecoder implements Selector{
	  final private static Log log = LogFactory.getLog(DecoderWrapper.class);
	  
	 private Decoder decoder;
	 
	 @Resource UossContext context;
	  
	@Override
	public boolean select(String key) {
		// TODO Auto-generated method stub
		// 当前组件是否需要执行对对应管道的消息解包操作
		//Decoder的注入名字，在框架启动时已经设定，这个是定是保存在uossContext的缓存中，默认的解码器名字为系统socket监听端口
		decoder = context.getBean(key, Decoder.class);	
		if(decoder==null) return false ;	//没有对应的编码器
		return true;
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		if (!(msg instanceof ChannelBuffer)) return msg;
        if (log.isDebugEnabled()) log.debug("Decoding a IoBuffer "+msg+" to Message ...");
        ChannelBuffer src = (ChannelBuffer) msg;
        byte[] bs = decoder.readBuffer(new BufferReader(src));
        if (null!=bs) return decoder.decode(bs);
        else return null;
	}

}
