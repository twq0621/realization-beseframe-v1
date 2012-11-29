/**
 * 
 */
package com.realization.framework.rule.impl;

import javax.annotation.Resource;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


import com.realization.framework.rule.RuleContextLoader;
import com.realization.framework.rule.RuleEngine;
import com.realization.framework.rule.RuleLoader;


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

	@Resource
	private ApplicationContext ctx;
	
	@Resource RuleLoader  ruleLoader;

	@Override
	public void init() {
		log.debug(" rule engine init start ... ");
		try {
			ruleLoader.loadRules();
		} catch (Exception e) {
			log.error(" =====  rule engine init failure when load rule configuration  ", e);
			return ;
		}
		RuleEngine engine =  ctx.getBean("ruleEngine",RuleEngine.class);
		engine.initEngine(ruleLoader.getRuleList());
		
		log.debug(" rule engine init success ... ");
	}

}
