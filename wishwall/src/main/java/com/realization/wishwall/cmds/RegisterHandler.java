package com.realization.wishwall.cmds;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.realization.framework.messaging.IpcMessage;
import com.realization.framework.rule.BaseCommand;
import com.realization.framework.rule.ProcessResult;
import com.realization.wishwall.service.RegisterService;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-20   下午09:08:24
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service("registerHandler") //现在基本上如果service也写了这个名字也有效，以后进行限制
public class RegisterHandler extends BaseCommand{
	
	@Resource RegisterService registerService;

	@Override
	protected Object process(IpcMessage ipcMessage) throws Exception {
		ProcessResult pr = new ProcessResult();
		pr.addResponseMessage(registerService.register(ipcMessage));
		return pr ;
	}

}
