package com.realization.framework.rule.entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.realization.framework.rule.WhenConvert;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


/**
 *  @author xiai_fei
 *
 *  @create-time	2012-12-1   下午07:31:54
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@XStreamConverter(WhenConvert.class)
public class When {

	public static final String PRIORITY = "priority";
	
	public static final String PROPERTY = "property";
	
	private Integer priority;
	
	@XStreamImplicit
	private Set<Condition> conditions = new HashSet<Condition>();

	@XStreamOmitField
	private List <Class<?>>  paramsList = new ArrayList<Class<?>>();
	@XStreamOmitField
	private Map<String,String> parsmMap = new HashMap<String, String>();

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Set<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(Set<Condition> conditions) {
		this.conditions = conditions;
	}

	public List<Class<?>> getParamsList() {
		return paramsList;
	}

	public void setParamsList(List<Class<?>> paramsList) {
		this.paramsList = paramsList;
	}

	public Map<String, String> getParsmMap() {
		return parsmMap==null?new HashMap<String,String>():this.parsmMap;
	}

	public void setParsmMap(Map<String, String> parsmMap) {
		this.parsmMap = parsmMap;
	}
	
	
}
