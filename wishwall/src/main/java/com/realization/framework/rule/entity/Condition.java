package com.realization.framework.rule.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


/**
 *  @author xiai_fei
 *
 *  @create-time	2012-12-1   下午07:32:51
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@XStreamAlias("property")
//@XStreamConverter(ConditionConvert.class)
public class Condition {

	@XStreamAsAttribute
	private String name ;
	@XStreamAsAttribute
	private String type ;
	@XStreamAsAttribute
	private String condition ;
	
	public Condition(String name ,String type ,String condition ){
		this.name = name;
		this.type = type;
		this.condition  = condition;
	}
	
	public Condition(){
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	
	
}
