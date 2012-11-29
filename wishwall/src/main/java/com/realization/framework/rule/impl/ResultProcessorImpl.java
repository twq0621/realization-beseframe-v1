package com.realization.framework.rule.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.realization.framework.messaging.IpcMessage;
import com.realization.framework.rule.MessageSender;
import com.realization.framework.rule.ProcessResult;
import com.realization.framework.rule.ResultProcessor;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-13   下午10:33:43
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class ResultProcessorImpl implements ResultProcessor{

	@Resource MessageSender messageSender;
	
	@Override
	public void doCommandResult(Object[] args) {
		if(args[args.length-1] instanceof ProcessResult){
			ProcessResult pr = (ProcessResult) args[args.length-1];
			for(IpcMessage ipc : pr.getResults() ){
				doResult(ipc);
			}
		}
		
	}

	/**
	*@param ipc
	*/
	
	private void doResult(IpcMessage ipc) {
		// TODO Auto-generated method stub
		
	}

}
