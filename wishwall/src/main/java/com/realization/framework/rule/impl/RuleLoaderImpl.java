/**
 * 
 */
package com.realization.framework.rule.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.realization.framework.rule.AbstractCommand;
import com.realization.framework.rule.entity.PropertiesRule;
import com.realization.framework.rule.entity.Rule;
import com.realization.framework.rule.entity.RuleElement;
import com.realization.framework.rule.init.RuleLoader;

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
@Service("properties")
public  class RuleLoaderImpl implements RuleLoader{

	private static final Log  log = LogFactory.getLog(RuleLoaderImpl.class);
	
	private   List<Rule> ruleList = new ArrayList<Rule>();
	
	private List<PropertiesRule>  pRuleList= new ArrayList<PropertiesRule>();
	
	@Resource private ApplicationContext ctx ;

	
	private String getRuleConfiguration() throws IOException{
		String filePath = "/META-INF/rule.properties";
		InputStream in = this.getClass().getResourceAsStream(filePath);
		InputStreamReader reader = new InputStreamReader(in);
		StringBuffer sb = new StringBuffer();
		char[] cs = new char[512];
		while(reader.read(cs)!=-1){
			sb.append(cs);
		}
		if(log.isDebugEnabled())log.debug("the rule properties content is : "+sb);
		return sb.toString().trim();
	}
	
	@SuppressWarnings("unchecked")
	private void setRules(String configuration)  throws Exception{

		String[] rules = configuration.split(RuleElement.end.name);
		for(String r : rules){
			this.pRuleList.add(setSingelRule(r));
		}
		if(!pRuleList.isEmpty()){
			ruleList.addAll((Collection<? extends Rule>) pRuleList);
		}
		
	}
	
	private PropertiesRule setSingelRule(String ruleStr) throws Exception{
		PropertiesRule rule = new PropertiesRule ();
		rule.setRuleNmae(getStrBetweenAAndB(RuleElement.rule.name, RuleElement.start.name,ruleStr).replace("\"", "").trim());
		setCondition(rule,ruleStr);
		setCmdList(rule, ruleStr);
		return rule ;
	}
	
	private void setCondition(PropertiesRule rule , String ruleStr ) throws ClassNotFoundException{
		String condition = getStrBetweenAAndB(RuleElement.when.name, RuleElement.then.name,ruleStr);
		String[] params = condition.split(";");
		for(int i=1,n=params.length;i<n;i++){
			setSingleParams(rule,params[i]);
		}
	}
	

	private void setSingleParams(PropertiesRule rule,String str) throws ClassNotFoundException{
		String[] os = str.split(":");
		String clzStr = os[1].substring(0, os[1].indexOf("("));
		Class<?> clz  =  this.getClass().getClassLoader().loadClass(clzStr);
		rule.getParamsList().add(clz);
		rule.getParsmMap().put(clzStr, os[1].substring(os[1].indexOf("(")+1, os[1].indexOf(")")));
	}
	
	private void setCmdList(PropertiesRule rule , String ruleStr){
		int thenIndex = ruleStr.indexOf(RuleElement.then.name);
		String temp =ruleStr.substring(ruleStr.indexOf("(",thenIndex)+1, ruleStr.indexOf(")",thenIndex));
		if("".equals(temp))return ;
		String[] cmds = temp.replace("\"", "").trim().split(",");
		for(String c : cmds){
			rule.getCmdStrList().add(c);
//			String[] sc = ctx.getBeanNamesForType(AbstractCommand.class);
			
			rule.getCmdList().add(ctx.getBean(c, AbstractCommand.class));
			log.debug("load rule : " + c);
		}
	}
	
	private String getStrBetweenAAndB(String start,String end ,String conent){
		int sIndex = conent.indexOf(start)+start.length();
		int eIndex = conent.indexOf(end);
		return conent.substring(sIndex, eIndex).trim();
	}
	

	@Override
	public void loadRules() throws Exception {
		log.debug(" load rule configuration start ... ");
		
		String config = "";
		try {
			config = getRuleConfiguration();
		} catch (IOException e) {
			log.error(" occur error on get rule configuration ", e);
			return ;
		}
		try {
			this.setRules(config);
		} catch (Exception e) {
			log.error(" failure to load rule list ", e);
		}
		log.debug(" load rule configuration success  - O.K. ");
		
	}



	@Override
	public List<Rule> getRuleList() {
		return ruleList;
	}




}
