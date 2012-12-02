/**
 * 
 */
package com.realization.framework.rule.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.realization.framework.rule.RuleMatcher;
import com.realization.framework.rule.RuleSelector;
import com.realization.framework.rule.entity.Rule;

/**
 * 
 * 	规则选择器，是否选择当前规则进行处理
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-11   上午09:20:40
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class RuleSelectorImpl implements RuleSelector{
	
	private RuleMatcher<Class<?>>ruleMatcher = new RuleMatcherImpl();
	
	private static final Log log =LogFactory.getLog(RuleSelectorImpl.class);

	/*
	 * 这里默认是不允许传入的参数为0长度的的
	 */
	@Override
	public boolean isSelect(Rule rule,Object...args) {
		if(args==null||args.length==0||rule.getParamsList().size()!=args.length){
			log.debug("  如果传入的参数少于规则文件addCommand中的参数，则任务不匹配");
			return false;
		}
		int i = 0;
		for(Class<?> clz : rule.getParamsList()){
			/*
			 * 判断 规则中的参数是否和传入的执行参数是否匹配
			 */
			if(args[i]==null||!clz.isAssignableFrom(args[i].getClass())) return false ;
			if(ruleMatcher.isTarget(clz)){//是否规则制定检查的类型
				if(ruleMatcher.match(clz, rule, args[i]))continue;	//制定类型是否满足要求
			}
			/*
			 * 要所有参数都满足才会返回true	
			 */
			return false;		
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		RuleSelectorImpl f = new RuleSelectorImpl();
		System.err.println(f.getClass().getName());
	}

}
