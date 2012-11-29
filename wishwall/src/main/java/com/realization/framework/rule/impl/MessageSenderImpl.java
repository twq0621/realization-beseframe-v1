package com.realization.framework.rule.impl;

import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.realization.framework.messaging.IpcMessage;
import com.realization.framework.rule.MessageSender;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-13   下午10:41:57
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class MessageSenderImpl implements MessageSender{

	@Override
	public void send(String url, IpcMessage msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Future<?> sendSync(String url, IpcMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

}
