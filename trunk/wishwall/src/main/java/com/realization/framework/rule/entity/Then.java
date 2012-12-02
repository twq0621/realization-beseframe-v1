package com.realization.framework.rule.entity;
import java.util.ArrayList;
import java.util.List;

import com.realization.framework.rule.AbstractCommand;
import com.realization.framework.rule.ThenConvert;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


/**
 *  @author xiai_fei
 *
 *  @create-time	2012-12-1   下午07:36:55
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@XStreamConverter(ThenConvert.class)
public class Then {

	@XStreamAlias("command")
	@XStreamImplicit       //如果没有了这个注解，那么xml中就需要有<cmdStrListS>这样一个集合标签
	private List<String> cmdStrList =  new ArrayList<String>();
	
	@XStreamOmitField
	private List<AbstractCommand> cmdList = new ArrayList<AbstractCommand>();

	public List<String> getCmdStrList() {
		return cmdStrList==null?(new ArrayList<String>()):(this.cmdStrList);
	}

	public void setCmdStrList(List<String> cmdStrList) {
		this.cmdStrList = cmdStrList;
	}

	public List<AbstractCommand> getCmdList() {
		return cmdList==null?new ArrayList<AbstractCommand>():this.cmdList;
	}

	public void setCmdList(List<AbstractCommand> cmdList) {
		this.cmdList = cmdList;
	}
	
	
}
