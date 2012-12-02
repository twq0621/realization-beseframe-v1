package com.realization.framework.rule.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.realization.framework.rule.entity.Rule;
import com.realization.framework.rule.entity.XmlRule;
import com.realization.framework.rule.init.RuleLoader;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-12-2   下午07:40:27
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service("xmlRuleLoader")
public class XmlRuleLoader implements RuleLoader{
	
	private static XStream xStream;
	
	private List<Rule> ruleList ;
	
	static{
		xStream = new XStream(new DomDriver());
		xStream.alias("Rule", XmlRule.class);
		xStream.alias("Rules", ArrayList.class);
		xStream.autodetectAnnotations(true);  //自动加载注解bean
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadRules() throws Exception {
//		Resource res  = new FileSystemResource("classpath:/META/rule.xml");
		Resource res  = new ClassPathResource("/META-INF/rule.xml");
		List<XmlRule> xmlRuleList =  (List<XmlRule>) xStream.fromXML(res.getInputStream());
		ruleList = new ArrayList<Rule>();
		ruleList.addAll((Collection<? extends Rule>) xmlRuleList);
		
	}

	@Override
	public List<Rule> getRuleList() {
		return this.ruleList;
	}

}
