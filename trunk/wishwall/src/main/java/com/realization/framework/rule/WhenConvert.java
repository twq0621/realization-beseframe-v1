package com.realization.framework.rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.realization.framework.rule.entity.Condition;
import com.realization.framework.rule.entity.When;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


/**
 *  @author xiai_fei
 *
 *  @create-time	2012-12-2   上午10:20:06
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class WhenConvert implements Converter{
	
	private static final Log log = LogFactory.getLog(WhenConvert.class);

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class arg0) {
		if(When.class.isAssignableFrom(arg0)) return true ;
		return false;
	}

	@Override
	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) {
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext arg1) {
		When when = new When();
		while(reader.hasMoreChildren()){
			reader.moveDown();
			if(When.PRIORITY.equals(reader.getNodeName())){
				when.setPriority(new Integer(reader.getValue()));
			}
			if(When.PROPERTY.equals(reader.getNodeName())){
				setProperty(reader, when);
			}
			
			reader.moveUp();
		}
		
		return when;
	}

	private void setProperty(HierarchicalStreamReader reader,When when ){
		
		String name= reader.getAttribute("name");
		String type = reader.getAttribute("type");
		String con = reader.getAttribute("condition");
		Condition condition = new Condition(name,type,con);
		when.getConditions().add(condition);
		try{
		Class<?> clz = this.getClass().getClassLoader().loadClass(type);
		when.getParamsList().add(clz);
		when.getParsmMap().put(type, con);
		}catch (Exception e) {
			log.error("  ======  occur error on load rule  when  ", e);
		}
		
	}
}
