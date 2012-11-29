package com.realization.framework.communicate.async.netty;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.springframework.stereotype.Service;

import com.realization.framework.communicate.Encoder;
import com.realization.framework.core.context.Description;
@Service
@Description(2)
public class EncoderWrapper extends OneToOneEncoder implements Selector {
    final private static Log log = LogFactory.getLog(EncoderWrapper.class);
	
    private Encoder encoder;
    
    @Override
    public boolean select( String key) {
       return false;
    }

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        if (msg instanceof ChannelBuffer) return msg;
        if (log.isDebugEnabled()) log.debug("Encoding a Message "+msg+" to  IoBuffer...");
        return ChannelBuffers.wrappedBuffer(encoder.encode(msg));
    }

}