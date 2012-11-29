package com.realization.framework.communicate.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.realization.framework.communicate.ConnectionListener;
import com.realization.framework.communicate.nio.Channel;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-26   下午10:33:58
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class DefaultConnectionListener implements ConnectionListener{

	@Override
	public void notify(TYPE type, Channel connection, Object fact)
			throws Exception {
		System.err.println(" connection event incomming ...");
	}

	@Override
	public boolean accept(SITE site, TYPE... types) {
		return true;
	}

}
