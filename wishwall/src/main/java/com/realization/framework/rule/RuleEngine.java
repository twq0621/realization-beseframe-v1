/**
 * 
 */
package com.realization.framework.rule;

import java.util.List;



/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-10   下午09:35:12
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface RuleEngine {
	
	public void initEngine() ;
	
	public void initEngine(List<Rule>ruleList) ;
	
	public void executeRule(int priority ,Object...args);

}
