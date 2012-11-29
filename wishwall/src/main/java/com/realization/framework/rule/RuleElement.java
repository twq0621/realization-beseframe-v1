/**
 * 
 */
package com.realization.framework.rule;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-10   下午11:25:50
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public enum RuleElement {

	rule("rule"),
	start("start"),
	when("when"),
	cmd("cmd"),
	priority("priority"),
	then("then"),
	prioritySignal(":"),
	cmdSignal("=="),	//后期引入正则表达式
	end("end");

	public final String name;
	
	RuleElement(String elementName){
		this.name =elementName;
	}
}
