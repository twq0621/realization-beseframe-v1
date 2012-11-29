/**
 * 
 */
package com.realization.framework.rule.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.realization.framework.rule.CommandChain;
import com.realization.framework.rule.ResultProcessor;
import com.realization.framework.rule.Rule;
import com.realization.framework.rule.RuleEngine;
import com.realization.framework.rule.RuleLoader;
import com.realization.framework.rule.RuleSelector;

/**
 * 
 * 默认规则引擎实现类
 * 
 * 		规则引擎只会驱动规则的相关事项，并不会针对某一规则的特殊处理
 * 		所以对于规则的命令处理，规则的个性化操作等，抽象到单独的接口中处理
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-11   上午10:55:35
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service("ruleEngine")
public class DefaultRuleEngine  implements RuleEngine{
	
	private static final Log log =LogFactory.getLog(DefaultRuleEngine.class);

	private  final TreeSet<Rule> ruleList = new TreeSet<Rule>();  //是否有并发危险？
	
	@Resource private RuleLoader ruleLoader;
	
	@Resource private ApplicationContext ctx;
	
	private List<ResultProcessor> processList = new ArrayList<ResultProcessor>();
	
	@Resource(name="ruleSelectorImpl")
	private RuleSelector selector ;
	
	@Override
	public void executeRule(int priority ,Object...args){
		for(Rule rule : ruleList){
			doSingleRule(priority,rule,args);
		}
	}


	@Override
	public void initEngine() {
		initEngine(ruleLoader.getRuleList());
		
	}
	
	@Override
	public void initEngine(List<Rule> ruleList) {
		log.debug(" init rule engine start ... ");
		if(ruleList==null) return ;
			this.ruleList.addAll(ruleList);
		String[] ps = ctx.getBeanNamesForType(ResultProcessor.class);
		for(String s : ps){
			processList.add(ctx.getBean(s, ResultProcessor.class));
		}
		log.debug(" init rule engine success ... ");
	}
	
	private void doSingleRule(int priority, Rule rule ,Object...args){
		if(priority>rule.getPriority()) return ;
		if(!selector.isSelect(rule, args))return ;
		log.info(" enging execute rule start ... ");
		CommandChain chain = new CommandChain(rule.getCmdList().iterator());
		chain.doProcess(args);
		doResults(chain.getResultList().toArray());
		
	}
	
	/**
	 * 
	 * 处理命令处理结果。处理结果放在args的最后一位，并且是processresult类型
	 * 
	 * 
	 * @param args
	 * @return  false 表示没有结果需要处理
	 */
	protected boolean doResults(Object[] args) {
		if(args==null||args.length==0) return false ;
		
		for(ResultProcessor r : processList){
			r.doCommandResult(args);
		}
		return true;
		
	}



}
