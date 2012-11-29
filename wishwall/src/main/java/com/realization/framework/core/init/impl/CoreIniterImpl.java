package com.realization.framework.core.init.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.realization.framework.communicate.async.netty.MessageReceiver;
import com.realization.framework.core.init.CoreIniter;
import com.realization.framework.rule.RuleIniter;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-12   下午08:29:01
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class CoreIniterImpl implements CoreIniter{
	
	private static final Log log = LogFactory.getLog(CoreIniterImpl.class);
	
	@Resource RuleIniter ruleIniter;
	
	@Resource MessageReceiver messageReceiver;
	
//	@Resource(name="defaultContext") UossContext context;
	
	@Override
	public boolean init() {
		ruleIniter.initRule();
//		String communicateSuit = CompomentConfiguration.getValue("communicate");
		messageReceiver.start();
//		try {
//			context.contextInitialized();
//		} catch (UossException e) {
//			log.error("  ===== occur error on init framework context ", e);
//			return false ;
//		}
		log.debug("  core suit init success ... ");
		return true ;
	}

}
