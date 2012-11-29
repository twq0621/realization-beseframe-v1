package com.realization.framework.communicate.impl;

import org.springframework.stereotype.Service;

import com.realization.framework.communicate.MessageEvent;
import com.realization.framework.communicate.MessageListener;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-26   下午10:35:11
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class DefaultMessageReceviering implements MessageListener{

	@Override
	public void receive(MessageEvent event) throws Exception {
		System.err.println("  message event incomming...");
	}

}
