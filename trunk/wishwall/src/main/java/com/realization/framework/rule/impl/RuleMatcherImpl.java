/**
 * 
 */
package com.realization.framework.rule.impl;

import javax.annotation.Resource;

import com.realization.framework.core.init.CompomentConfiguration;
import com.realization.framework.messaging.IpcMessage;
import com.realization.framework.rule.Rule;
import com.realization.framework.rule.RuleMatcher;

/**
 * 
 * 	规则参数检测器
 * 
 * 		仅支持对ipcMessage的详细检测
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-11   上午10:19:01
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Resource
public class RuleMatcherImpl implements RuleMatcher<Class<?>> {
	
//	private static final Log log=LogFactory.getLog(RuleMatcherImpl.class);

	/**
	 * 当前仅支持message类型的详细检测
	 */
	@Override
	public boolean isTarget(Class<?> t) {
		//只检查message类型的参数的具体内容，其他的不检查
		if(t.getName().equals(CompomentConfiguration.getValue("message")))return true;
		return false;
	}

	/**
	 * 当前仅支持相等条件元素的检测
	 */
	@Override
	public boolean match(Class<?> t, Rule rule, Object params) {
		String paramStr = rule.getParsmMap().get(t);
		String[] ps = paramStr.split("==");
		/*
		 *只检查IpcMessage 
		 */
		IpcMessage msg = (IpcMessage) params;
		/*
		 * 判断msgCode是否相等
		 */
		if(msg.getMsgHead(ps[0]).equals(ps[1]))return true;	
		return false;
	}


}
