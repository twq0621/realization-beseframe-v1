package com.realization.wishwall.cmds.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.realization.framework.messaging.IpcMessage;
import com.realization.framework.messaging.IpcMessageFactory;
import com.realization.wishwall.cmds.MessageFactory;
import com.realization.wishwall.common.constant.ResultCode;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-20   下午10:32:56
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service("messageFactoryImpl")
public class MessageFactoryImpl implements MessageFactory {
	
	@Resource IpcMessageFactory ipcMessageFactory;
	
	private static final Log log =LogFactory.getLog(MessageFactoryImpl.class);

	@Override
	public IpcMessage buildResponseMsg(IpcMessage msg, ResultCode code) {
		return buildResponseMsg(msg,code.result,code.desc);
	}

	@Override
	public IpcMessage buildResponseMsg(IpcMessage msg, byte result, String desc) {
		Map<String,Object> head = new HashMap<String, Object>();
		head.put("msgCode", msg.getMsgHead("msgCode"));
		head.put("tranSeq", msg.getMsgHead("tranSeq"));
		IpcMessage outMsg = null ;
		try {
			 outMsg = ipcMessageFactory.newIpcMessage(head);
		} catch (Exception e) {
			log.error("  === occur error on build msg 	", e);
			return null ;
		}
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("result", result);
		body.put("msg", desc);
		outMsg.setMsgData(body);
		return outMsg;
	}

}
