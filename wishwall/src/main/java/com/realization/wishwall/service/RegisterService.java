package com.realization.wishwall.service;

import com.realization.framework.messaging.IpcMessage;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-20   下午09:09:23
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface RegisterService {

	public IpcMessage register(IpcMessage msg) throws Exception ;
}
