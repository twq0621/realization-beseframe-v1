package com.realization.framework.rule.process.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.realization.framework.common.exception.MarshallerException;
import com.realization.framework.communicate.Marshaller;
import com.realization.framework.communicate.nio.Channel;
import com.realization.framework.communicate.nio.MessageFact;
import com.realization.framework.messaging.IpcMessage;
import com.realization.framework.messaging.message.Message;
import com.realization.framework.rule.RuleEngine;
import com.realization.framework.rule.entity.ProcessResult;
import com.realization.framework.rule.process.MessageProcessor;
import com.realization.framework.rule.process.ResultProcessor;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-19   下午09:41:08
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service("messageProcessorImpl")
public class MessageProcessorImpl implements MessageProcessor,ResultProcessor{

	private ThreadLocal<MessageFact> factGroup = new ThreadLocal<MessageFact>();
	
	@Resource(name="ruleEngine") RuleEngine ruleEngine;
	
	private static final Log log =LogFactory.getLog(MessageProcessorImpl.class);
	
	@Resource
	private Marshaller<Message> messageFactMarshaller;
	
	
	@Override
	public void process(MessageFact fact) {
		factGroup.set(fact);
		IpcMessage msg = (IpcMessage) fact.fact;
		ruleEngine.executeRule(0, msg);
	}

	@Override
	public void doCommandResult(Object[] args) {
		
		if(!(args[args.length-1] instanceof ProcessResult)) return ;
		ProcessResult pr = (ProcessResult) args[args.length-1];
		
		/*
		 * 已经将http的outputstream放进去channel了
		 */
		Channel  channel = factGroup.get().session;
		
		
		for(IpcMessage o  : pr.getResults()){
				try {
					Message message = messageFactMarshaller.marshal((IpcMessage) o, null);
					if(log.isDebugEnabled()){
						log.debug(" SEND Message : " + message);
					}
					channel.write(message.toBytes());	//输出返回数据到终端
				} catch (MarshallerException e) {
					log.error(" ==== occur error on marshal ipcmessage  ",e);
				} catch (Exception e) {
					log.error(" ==== occur error on write message to client  ",e);
				}
			
		}
		
	}

}
