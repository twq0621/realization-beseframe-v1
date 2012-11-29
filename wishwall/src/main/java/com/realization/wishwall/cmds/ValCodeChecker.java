package com.realization.wishwall.cmds;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.realization.framework.rule.AbstractCommand;
import com.realization.framework.rule.CommandChain;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-15   下午10:59:42
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service("valCodeChecker")
public class ValCodeChecker extends AbstractCommand{
	
	private static final Log log = LogFactory.getLog(ValCodeChecker.class);

	@Override
	public void execute(CommandChain chain, Object... args) {
		log.debug(" do valcode check ... ");
		chain.doProcess(args);
	}

}
