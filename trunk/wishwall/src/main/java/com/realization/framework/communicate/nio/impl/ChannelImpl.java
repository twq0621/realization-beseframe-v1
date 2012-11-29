package com.realization.framework.communicate.nio.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketAddress;

import com.realization.framework.communicate.nio.Channel;
import com.realization.framework.communicate.nio.Daemon;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-19   下午08:14:41
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class ChannelImpl implements Channel{
	
	private OutputStream out ;
	
	public ChannelImpl(OutputStream out){
		this.out = out;
	}
	public ChannelImpl(org.jboss.netty.channel.Channel channle,Daemon daemon){
		
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws IOException {
		this.out.close();
		
	}

	@Override
	public SocketAddress getLocalAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SocketAddress getRemoteAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void write(byte[] content) throws IOException {
		out.write(content);
	}


	@Override
	public void flush() throws IOException {
		out.flush();
	}

}
