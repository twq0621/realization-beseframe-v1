/**
 * 
 */
package com.realization.framework.rule.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.realization.framework.rule.AbstractCommand;

/**
 * 	规则实体，从配置文件中获取信息
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-10   下午09:35:19
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class PropertiesRule implements Rule{

	private String ruleNmae;
	
//	private String type ; 
	
	private int priority ;
	
	private String cmd ;
	
	private List<String> cmdStrList =  new ArrayList<String>();
	
	private List<AbstractCommand> cmdList = new ArrayList<AbstractCommand>();
	
	private List <Class<?>>  paramsList = new ArrayList<Class<?>>();
	
	private Map<String,String> parsmMap = new HashMap<String, String>();
	
	public PropertiesRule(String name , int priority ,String cmd){
		this.ruleNmae = name;
		this.priority = priority;
		this.cmd= cmd;
	}
	
	public PropertiesRule(){
	}

	@Override
	public int compareTo(Rule r) {
		if(this.getPriority()<r.getPriority())return -1;
		return 1;
	}
	
	@Override
	public String toString() {
		return " rule = [ priorty : "+priority+ " ] [ name : "+ ruleNmae+"]";
	}

	public String getRuleNmae() {
		return ruleNmae;
	}

	public void setRuleNmae(String ruleNmae) {
		this.ruleNmae = ruleNmae;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Override
	public Map<String, String> getParsmMap() {
		return this.parsmMap;
	}

	@Override
	public String getConditionByType(String type) {
		return this.getParsmMap().get(type);
	}

	public List<String> getCmdStrList() {
		return cmdStrList;
	}

	public List<AbstractCommand> getCmdList() {
		return cmdList;
	}

	public List <Class<?>> getParamsList() {
		return paramsList;
	}



}
