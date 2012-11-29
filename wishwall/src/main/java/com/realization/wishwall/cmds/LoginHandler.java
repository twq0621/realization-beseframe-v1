package com.realization.wishwall.cmds;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.realization.framework.messaging.IpcMessage;
import com.realization.framework.rule.BaseCommand;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-15   下午11:03:46
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service("loginHandler")
public class LoginHandler extends BaseCommand{

	private static final Log log = LogFactory.getLog(LoginHandler.class);
	
	@Override
	protected Object process(IpcMessage ipcMessage) throws Exception {
		log.debug(" do login Handler start //.. ");
		return null;
	}

}
