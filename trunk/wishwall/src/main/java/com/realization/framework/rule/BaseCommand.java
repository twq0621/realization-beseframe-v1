package com.realization.framework.rule;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.realization.framework.common.CommandException;
import com.realization.framework.common.MessageFactory;
import com.realization.framework.messaging.IpcMessage;
import com.realization.framework.rule.entity.ProcessResult;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-15   下午11:01:06
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public abstract class BaseCommand extends AbstractCommand{
	@Resource MessageFactory messageFactory;

	private static final Log log =LogFactory.getLog(BaseCommand.class);
	public void execute(CommandChain chain , Object...args){
		if(args!=null&&args.length>0){
			try {
				chain.doProcess(process((IpcMessage)args[0]));
			} catch (Exception e) {
				chain.doProcess(doInCatchException(e,(IpcMessage)args[0]));
			}
		}
	}

	/**
	*@param e
	*@param ipcMessage
	*@return
	*/
	
	private ProcessResult doInCatchException(Exception e, IpcMessage ipcMessage) {
		log.error("  ====== occur error on cmd : " + ipcMessage.getMsgHead("msgCode"), e);
		ProcessResult pr = new ProcessResult() ;
		if(e instanceof CommandException){
			CommandException ce = (CommandException) e;
			pr.addResponseMessage(messageFactory.buildResponseMsg(ipcMessage, ce.getCode(), ce.getMsg()));
			return pr;
		}
		pr.addResponseMessage(messageFactory.buildResponseMsg(ipcMessage,(short)-1, ""));
		return pr;
	}

	/**
	*@param ipcMessage
	*/
	
	protected abstract Object process(IpcMessage ipcMessage) throws Exception;
}
