package com.realization.framework.rule.process;

import com.realization.framework.communicate.nio.MessageFact;
import com.realization.framework.messaging.IpcMessage;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-19   下午09:18:16
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface MessageProcessor {

	public void process(MessageFact fact);
}
