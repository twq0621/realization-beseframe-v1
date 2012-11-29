/**
 * 
 */
package com.realization.framework.rule.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.realization.framework.rule.CommandListResulter;
import com.realization.framework.rule.ResultRuleLauncher;
import com.realization.framework.rule.RuleEngine;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-11   上午11:49:45
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service("defaultRuleLauncher")
public class DefaultRuleLauncher implements ResultRuleLauncher {
	
	private static final Log log = LogFactory.getLog(DefaultRuleLauncher.class);
	
	@Resource(name="ruleEngine") RuleEngine engine;
	
	@Resource(name="resultRuleEngine") RuleEngine resultEngine;

	@Override
	public void launch(int priority, Object... args) {
		log.debug(" launcher rule ... ");
		engine.executeRule(priority, args);

	}

	@Override
	public Map<String,Object> launchWithResult(int priority, Object... args) {
		resultEngine.executeRule(priority, args);
		return ((CommandListResulter)resultEngine).getCommandResult();
	}

}
