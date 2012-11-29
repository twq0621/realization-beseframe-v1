package com.realization.framework.communicate.nio.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.realization.framework.common.exception.MarshallerException;
import com.realization.framework.communicate.Marshaller;
import com.realization.framework.messaging.IpcMessage;
import com.realization.framework.messaging.IpcMessageFactory;
import com.realization.framework.messaging.message.Message;
import com.realization.framework.messaging.message.PackFactory;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-19   下午10:30:09
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service("messageMarshaller")
public class MessageMarshaller implements Marshaller<Message>{

	@Override
	public IpcMessage unMarshal(Message msg, IpcMessageFactory factory)
			throws MarshallerException {
		Map<String,Object>msgHead = new HashMap<String, Object>();
		msgHead.put("msgCode", msg.getDictate()+"");
		msgHead.put("tranSeq", msg.getSessionID()+"");
		msgHead.put("sysId", msg.getRemoteSystem()+"");
		com.realization.framework.messaging.IpcMessage ipcMsg=  new com.realization.framework.messaging.message.IpcMessage(msgHead);
		ipcMsg.setMsgData(msg);
		return ipcMsg;
	}

	@Override
	public Message marshal(IpcMessage msg, PackFactory<Message> factory)
			throws MarshallerException {
		boolean isReq = msg.getMsgHead("request")==null?false:(Boolean)msg.getMsgHead("request");
		boolean needResp = msg.getMsgHead("needResp")==null?false:(Boolean)msg.getMsgHead("needResp");
		
		Message message= new Message(isReq , needResp , Integer.parseInt(msg.getMsgHead("tranSeq").toString()),Integer.parseInt(msg.getMsgHead("msgCode").toString()),31);
		Map<String,Object> map = msg.getMsgData();
//		map.put("result", (short)0);
//		map.put("msg", "success");
		for(Map.Entry<String, Object> e: map.entrySet()){
			message.put(e.getKey(),e.getValue());
		}
		return message;
	}

}
