package com.realization.framework.core.init;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-12   下午08:31:45
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class CompomentConfiguration {
	
	private static Properties ps = null ;
	
	private static Hashtable<Object,Object>properties = new Hashtable<Object, Object>();
	
	private static final Log log = LogFactory.getLog(CompomentConfiguration.class);

	public static boolean loadConfig(){
		if(ps==null)
		{
			ps= new Properties();
			String filePath= "/META-INF/compoment.properties";
			Resource res = new ClassPathResource(filePath);
//		InputStream in =CompomentConfiguration.class.getClass().getResourceAsStream("/META-INF/compoment.properties");
		try {
			ps.load(res.getInputStream());
		} catch (IOException e) {
			log.error(" occur error on load compoment.properties ", e);
			return false;
		}
		properties=ps ;
		}
		log.debug("  ===  load framework  properties success ");
		return true;
	}
	
	public static void addElement(String key ,String value){
		properties.put(key, value);
	}
	
	public static String getValue(String key){
		return properties.get(key)==null?"":properties.get(key).toString();
	}
	
	public static String getValue(String key,String defaultValue){
		return properties.get(key)==null?defaultValue:properties.get(key).toString();
	}
	
	public static  Set<Entry<Object, Object>> getEntryKeySet(){
		return properties.entrySet();
	}
}
