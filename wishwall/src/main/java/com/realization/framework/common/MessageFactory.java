package com.realization.framework.common;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.realization.framework.messaging.IpcMessage;
import com.realization.framework.messaging.IpcMessageFactory;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-15   下午10:49:57
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class MessageFactory {
	
	private static final Log log =LogFactory.getLog(MessageFactory.class);

	@Resource IpcMessageFactory ipcMessageFactory;
	public IpcMessage buildResponseMsg(IpcMessage inMsg ,short code ,String msg){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("msgCode", inMsg.getMsgHead("msgCode"));
		map.put("tranSeq", inMsg.getMsgHead("tranSeq"));
		IpcMessage outMsg = null;
		try {
			 outMsg =ipcMessageFactory.newIpcMessage(map);
		} catch (Exception e) {
			log.error(" occur error on build message ", e);
			return null;
		}
		map.clear();
		map.put("result", code);
		map.put("msg", msg);
		outMsg.setMsgData(map);
		return outMsg;
	}
	
}
