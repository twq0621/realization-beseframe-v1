package com.realization.framework.rule.entity;

import java.util.List;
import java.util.Map;

import com.realization.framework.rule.AbstractCommand;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-12-2   下午07:59:12
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface Rule extends  Comparable<Rule>{


	public String getRuleNmae();

	public void setRuleNmae(String ruleNmae);
	
	public int getPriority() ;
	
	public void setPriority(int priority) ;

	public String getCmd();
	
	public void setCmd(String cmd);

	public List<String> getCmdStrList() ;

	public List<AbstractCommand> getCmdList();
	
	/**
	 * @return the paramsList
	 */
	public List <Class<?>> getParamsList();

	public Map<String, String> getParsmMap();
	
//	public Iterator<String> getCmdStrList();
//
//	public Iterator<AbstractCommand> getCmdList();
//
//	public Iterator<Class<?>> getParamsList();
	
	public String getConditionByType(String type);

}
