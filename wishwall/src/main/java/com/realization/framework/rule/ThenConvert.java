package com.realization.framework.rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.realization.framework.rule.entity.Then;
import com.realization.wishwall.init.SpringContextUtils;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


/**
 *  @author xiai_fei
 *
 *  @create-time	2012-12-2   下午12:05:38
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class ThenConvert implements Converter{
	
	private static final Log log = LogFactory.getLog(ThenConvert.class);

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class arg0) {
		if(Then.class.isAssignableFrom(arg0)) return true ;
		return false;
	}

	@Override
	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext arg1) {
		Then then = new Then();
		while(reader.hasMoreChildren()){
			reader.moveDown();
			System.err.println(reader.getNodeName());
			String command = reader.getValue();
			for(String c :command.split(",")){
				then.getCmdStrList().add(c);
				try {
					then.getCmdList().add(SpringContextUtils.getAppContext().getBean(c, AbstractCommand.class));
				} catch (Exception e) {
					log.error("  occur error on load command "+c + " stop load command  ", e);
					break;
				}
				log.debug("load command : " + c);
			}
			reader.moveUp();
		}
		return then ;
	}
}
