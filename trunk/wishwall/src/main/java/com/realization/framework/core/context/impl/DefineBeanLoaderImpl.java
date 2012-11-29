package com.realization.framework.core.context.impl;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.realization.framework.core.context.DefineBeanLoader;
import com.realization.framework.core.init.CompomentConfiguration;

/**
 * 		加载配置文件component里面指定的bean
 * 
 * 			这个指定是优先级最高的，会覆盖DI注入的bean
 * 
 * 		这里会对配置文件中以框架包开头的bean进行配置，也就是以：com.realization.framework开头
 * 		如：
 * 			com.realization.framework.communicate.Decoder.8787=com.realization.framework.communicate.impl.DecoderImpl
 * 			这个Decoder在系统中的实现类被注入的名字为8787
 * 
 * 			communicate=netty 这个将会被忽略
 * 
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-24   上午09:57:29
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class DefineBeanLoaderImpl implements DefineBeanLoader{
	
	private static final Log log = LogFactory.getLog(DefineBeanLoaderImpl.class);

	@Override
	public void loadDefineBean(ConcurrentMap<String, Object> map) throws Exception {
		for(Entry<Object, Object> en : CompomentConfiguration.getEntryKeySet()){
			String key = (String) en.getKey();
			if(key.startsWith("com.realization.framework")){	//框架指定的类
				String name = getConfigName(key);
				Class<?> clz = Class.forName((String)en.getValue());
				map.put(name, clz.newInstance());
				log.debug("  ==== add config bean : " + en.getKey() + " beanName : " + name+" success ");
			}
				
		}
		
	}

	/**、
	 *	获取配置的bean的名字
	 *
	 *	没有特殊符号:，则取最后一个.号后的字符串作为bean的名字
	 *
	*@param key	配置的字符串
	*@return
	*/
	
	private String getConfigName(String key) {
		String name =key.substring(key.lastIndexOf(".")+1,key.length());
		if(name.contains(":")){
			name = name.substring(name.indexOf(":")+1,name.length());
		}
		return name;
	}

}
