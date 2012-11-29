package com.realization.framework.rule.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.realization.framework.rule.RuleEngine;
import com.realization.framework.rule.RuleIniter;
import com.realization.framework.rule.RuleLoader;

/**
 * 
 * 	规则初始化器   
 * 
 * 		加载规则配置文件，初始化规则引擎
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-13   下午10:12:23
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class RuleIniterImpl implements RuleIniter{
	
	@Resource RuleLoader ruleLoader;
	
	@Resource(name="ruleEngine") RuleEngine ruleEngine;
	
	private static final Log log = LogFactory.getLog(RuleIniterImpl.class);
	
	public boolean initRule(){
		try {
			ruleLoader.loadRules();
		} catch (Exception e) {
			log.error(" occur error on load rule list ", e);
			return false ;
		}
		ruleEngine.initEngine(ruleLoader.getRuleList());
		log.debug(" ruleEngine init success .... ");
		return true;
	}
	
}
