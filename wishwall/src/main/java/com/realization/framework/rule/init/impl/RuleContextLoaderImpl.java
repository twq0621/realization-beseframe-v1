/**
 * 
 */
package com.realization.framework.rule.init.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.realization.framework.rule.RuleEngine;
import com.realization.framework.rule.entity.Rule;
import com.realization.framework.rule.init.RuleContextLoader;
import com.realization.framework.rule.init.RuleLoader;


/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-10   下午10:23:44
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class RuleContextLoaderImpl implements RuleContextLoader{
	
	private static final Log log = LogFactory.getLog(RuleContextLoaderImpl.class);

//	@Resource
//	private ApplicationContext ctx;
	
	@Resource(name="xmlRuleLoader") RuleLoader ruleLoader;
	
	@Resource(name="ruleEngine") RuleEngine ruleEngine;

	@Override
	public void init() {
		log.debug(" rule engine init start ... ");
		try {
			ruleLoader.loadRules();
		} catch (Exception e) {
			log.error(" =====  rule engine init failure when load rule configuration  ", e);
			return ;
		}
		
		ruleEngine.initEngine(ruleLoader.getRuleList());
		
		log.debug(" rule engine init success ... ");
	}

}
