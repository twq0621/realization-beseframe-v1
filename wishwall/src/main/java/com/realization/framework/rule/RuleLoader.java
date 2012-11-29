/**
 * 
 */
package com.realization.framework.rule;

import java.util.List;

/**
 *  构造规则链
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-10   下午11:07:17
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public  interface RuleLoader {

	/**
	 *   加载规则
	*@param <T>
	*@param ctx
	*@throws Exception
	 */
	public   void loadRules() throws Exception;

	/**
	 * 获取规则链
	*@param <T>
	*@param t
	*@return
	 */
	public  List<Rule> getRuleList() ;
}
