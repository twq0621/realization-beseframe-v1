package com.realization.framework.rule.entity;

/**
 * 
 */

import java.util.List;
import java.util.Map;

import com.realization.framework.rule.AbstractCommand;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 规则实体，从配置文件中获取信�?
 * 
 * @author xiai_fei
 * 
 * @create-time 2012-11-10 下午09:35:19
 * 
 * @version 1.0
 * @description realization-BaseFrame
 * @版权�?�� Realization 团队
 */
//@XStreamAlias("Rule")
public class XmlRule implements Rule {

	@XStreamAlias("name")
	@XStreamAsAttribute
	private String ruleNmae;

	@XStreamAlias("type")
	@XStreamAsAttribute
	private String type;

	private When when;

	private Then then;

	public XmlRule(String name, String type) {
		this.ruleNmae = name;
		this.type = type;
	}

	public XmlRule() {
	}

	public int compareTo(Rule r) {
		if (this.getPriority() < r.getPriority())
			return -1;
		return 1;
	}

	public String getRuleNmae() {
		return ruleNmae;
	}

	public void setRuleNmae(String ruleNmae) {
		this.ruleNmae = ruleNmae;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPriority() {
		return this.when.getPriority();
	}

	public When getWhen() {
		return when;
	}

	public void setWhen(When when) {
		this.when = when;
	}

	public Then getThen() {
		return then;
	}

	public void setThen(Then then) {
		this.then = then;
	}

	public String getConditionByType(String type) {
		return this.when.getParsmMap().get(type);
	}

	@Override
	public void setPriority(int priority) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCmd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCmd(String cmd) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getCmdStrList() {
		return this.then.getCmdStrList();
	}

	@Override
	public List<AbstractCommand> getCmdList() {
		return this.then.getCmdList();
	}

	@Override
	public List<Class<?>> getParamsList() {
		return this.when.getParamsList();
	}

	@Override
	public Map<String, String> getParsmMap() {
		return this.when.getParsmMap();
	}

}
