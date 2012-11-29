package com.realization.wishwall.cmds;

import com.realization.framework.messaging.IpcMessage;
import com.realization.wishwall.common.constant.ResultCode;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-20   下午10:32:39
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface MessageFactory {

	public IpcMessage buildResponseMsg(IpcMessage msg, ResultCode code );
	
	public IpcMessage buildResponseMsg(IpcMessage msg, byte result , String desc);
}
