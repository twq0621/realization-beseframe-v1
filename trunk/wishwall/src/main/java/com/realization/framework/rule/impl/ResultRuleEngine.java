package com.realization.framework.rule.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.realization.framework.rule.CommandListResulter;
import com.realization.framework.rule.entity.ProcessResult;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-11   下午10:26:38
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service("resultRuleEngine")
public class ResultRuleEngine extends DefaultRuleEngine implements CommandListResulter {

	private static ThreadLocal<Map<String, Object>> result = new  ThreadLocal<Map<String, Object>>();
	
	@Override
	public Map<String, Object> getCommandResult() {
		return result.get();
	}

	@Override
	protected boolean doResults(Object[] args) {
		if(!super.doResults(args)) return false;
		ProcessResult pr = (ProcessResult) args[args.length-1];
		result.set(pr.getResultMap()) ;
		return true;
		
	}

	
}
