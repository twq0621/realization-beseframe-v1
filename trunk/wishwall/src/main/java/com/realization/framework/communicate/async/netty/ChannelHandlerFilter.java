package com.realization.framework.communicate.async.netty;

import org.springframework.stereotype.Service;

import com.realization.framework.core.context.ClassFilter;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-25   下午01:25:59
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class ChannelHandlerFilter implements ClassFilter{

	@Override
	public boolean accpet(Class<?> advice, String key) {
		// TODO Auto-generated method stub
		return false;
	}

}
