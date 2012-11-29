/**
 * 
 */
package com.realization.framework.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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
public class Rule implements Comparable<Rule>{

	private String ruleNmae;
	
	private int priority ;
	
	private String cmd ;
	
	private List<String> cmdStrList =  new ArrayList<String>();
	
	private List<AbstractCommand> cmdList = new ArrayList<AbstractCommand>();
	
	private List <Class<?>>  paramsList = new ArrayList<Class<?>>();
	
	private Map<Class<?>,String> parsmMap = new HashMap<Class<?>, String>();
	
	public Rule(String name , int priority ,String cmd){
		this.ruleNmae = name;
		this.priority = priority;
		this.cmd= cmd;
	}
	
	public Rule(){
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

	public List<String> getCmdStrList() {
		return cmdStrList;
	}

	public List<AbstractCommand> getCmdList() {
		return cmdList;
	}

	/**
	 * @return the paramsList
	 */
	public List <Class<?>> getParamsList() {
		return paramsList;
	}

	public static void main(String[] args) {
		Rule r1 = new Rule();
		r1.setPriority(1);
		Rule r2 = new Rule();
		r2.setPriority(3);
		Rule r3 = new Rule();
		r3.setPriority(2);
		Rule r4 = new Rule();
		r4.setPriority(2);
		r4.setRuleNmae("rule 4");
		
		List<Rule> rList = new ArrayList<Rule>();
		rList.add(r3);
		rList.add(r2);
		rList.add(r1);
		rList.add(r4);
		TreeSet<Rule> set = new TreeSet<Rule>();
		set.addAll(rList);
		for(Rule rule : set){
			System.err.println(rule);
		}
	}

	public Map<Class<?>, String> getParsmMap() {
		return parsmMap;
	}


}
