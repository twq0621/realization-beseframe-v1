package com.realization.framework.core.context.impl;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.realization.framework.common.util.StringUtils;
import com.realization.framework.core.context.ClassFilter;
import com.realization.framework.core.context.ClassScaner;
import com.realization.framework.core.context.DefineBeanLoader;
import com.realization.framework.core.context.InstanceClass;
import com.realization.framework.core.context.UossContext;
import com.realization.framework.core.init.CompomentConfiguration;

/**
 * 默认的系统容器实现类
 * 
 * <br/>
 * 基于spring的
 * 
 * 但是当前实现类是无法操作spring容器缓存的。也就是无法显式设定bean在spring容器中的属性和名字等。
 * 这里只提供动态获取bean，也就是主动通过容器getBean等方法时获取有用。因为这时可以使用当前类
 * 替换spring的applicationContext提供实现。
 * 
 * @author xiai_fei
 * 
 * @create-time 2012-11-23 下午11:18:23
 * 
 * @version 1.0
 * @description realization-BaseFrame
 * @版权所有 Realization 团队
 */
@Service("defaultContext")
@Scope("singleton")
public class DefaultContext implements InitializingBean,UossContext {

	private ConcurrentMap<String, Object> configBean = new ConcurrentHashMap<String, Object>();

	private static final Log log = LogFactory.getLog(DefaultContext.class);

	@javax.annotation.Resource
	DefineBeanLoader beanLoader;

	@javax.annotation.Resource
	private ApplicationContext ctx;

	private List<String> classes;

	@javax.annotation.Resource
	ClassScaner classScaner;
	
	private volatile static boolean isInit = false ;
	

	

	/**
	 * 原本是打算将这个初始化动作放在core模块的初始化方法中，但是发现是不可以的。
	 * 	因为容器初始化话时，有一个动作是对特定配置的bean进行加载，有可能在这些bean还没来得及加载时
	 * 	这些bean已经在其他地方被用了。
	 */

	private void contextInitialized() {
		if(isInit) return ;
		isInit = true ;
		CompomentConfiguration.loadConfig();	//因为框架容器需要用到这里的配置，所以需要先加载
		classes = classScaner.getClassName(CompomentConfiguration.getValue(
				"scan.path", "com"));
		try {
			beanLoader.loadDefineBean(configBean);
		} catch (Exception e) {
			log.error(" ===== occur error on load config bean ", e);
		}
	}


	@Override
	public <A extends Annotation> A findAnnotationOnBean(String arg0,
			Class<A> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBeanDefinitionCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] getBeanDefinitionNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getBeanNamesForType(@SuppressWarnings("rawtypes") Class arg0) {
		return ctx.getBeanNamesForType(arg0);
	}

	@Override
	public String[] getBeanNamesForType(Class arg0, boolean arg1, boolean arg2) {
		return ctx.getBeanNamesForType(arg0, arg1, arg2);
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> arg0, boolean arg1,
			boolean arg2) throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getBeansWithAnnotation(
			Class<? extends Annotation> arg0) throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsBean(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] getAliases(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getBean(String arg0) throws BeansException {
		if(configBean.containsKey(arg0)){
			return configBean.get(arg0);
		}
		return ctx.getBean(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(Class<T> arg0) throws BeansException {
		for(Entry<String, Object> en : configBean.entrySet()){
			if(arg0.isAssignableFrom(en.getValue().getClass())){
				return  (T) en.getValue();
			}
		}
		try{
			return ctx.getBean(arg0);
		}catch (NoSuchBeanDefinitionException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(String arg0, Class<T> arg1) throws BeansException {
		if(configBean.containsKey(arg0)){
			Object value =  configBean.get(arg0);
			if(arg1.isAssignableFrom(value.getClass())){
				return (T) value ;
			}
		}
		try{
			return ctx.getBean(arg0,arg1);
		}catch (NoSuchBeanDefinitionException e) {
			return null;
		}
	}

	@Override
	public Object getBean(String arg0, Object... arg1) throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getType(String arg0) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPrototype(String arg0)
			throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSingleton(String arg0)
			throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTypeMatch(String arg0, Class arg1)
			throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsLocalBean(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BeanFactory getParentBeanFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessage(MessageSourceResolvable arg0, Locale arg1)
			throws NoSuchMessageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessage(String arg0, Object[] arg1, Locale arg2)
			throws NoSuchMessageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessage(String arg0, Object[] arg1, String arg2,
			Locale arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void publishEvent(ApplicationEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Resource[] getResources(String arg0) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClassLoader getClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource getResource(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutowireCapableBeanFactory getAutowireCapableBeanFactory()
			throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationContext getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getStartupDate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void loadConfigBean() {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> List<T> getInstanceClasses(Class<T> clz) {
		String[] ns = ctx.getBeanNamesForType(clz);
		String[] ds = ctx.getBeanDefinitionNames();

		List<T> results = new ArrayList<T>();
		for (String s : ns) {
			results.add(ctx.getBean(s, clz));
		}
		return results;
	}

	@Override
	public <T> Collection<InstanceClass<? extends T>> getInstanceClasses(
			Class<T> clz, ClassFilter filter,
			Comparator<InstanceClass<? extends T>> comparator) {
		if(classes==null){
			classes = classScaner.getClassName(CompomentConfiguration.getValue(
					"scan.path", "com"));
		}
		Collection<InstanceClass<? extends T>> set;
		if (comparator != null) {
			set = new TreeSet<InstanceClass<? extends T>>(comparator);
		} else {
			set = new TreeSet<InstanceClass<? extends T>>();
		}
		try {
			for (String n : classes) {
				Class<?> cls = Class.forName(n);
				if (clz.isAssignableFrom(cls)) {
					if (filter == null || filter.accpet(cls, null)) {
						Service ano = cls.getAnnotation(Service.class);
						String beanName = "";
						if(ano==null){
							beanName = checkBeanInXml(cls);
							if(beanName==null)
							continue;
							
						}else{
							if (null == ano.value()
									|| "".equals(ano.value())) {
								beanName = StringUtils.getClassBeanName(cls.getName());
							} else {
								beanName = ano.value();
							}
						}
						@SuppressWarnings("unchecked")
						InstanceClass<? extends T> instanceClass = new InstanceClass<T>(
								beanName, (Class<? extends T>) cls);
						set.add(instanceClass);
					}
				}
			}

		} catch (Exception e) {
			log.error(" ========== occur error on getinstance class ", e);
		}
		return set;
	}
	
	/**
	 * 检测bean是否在xml中进行了配置，上面检测的只是注解
	*@param cls
	*@return
	 */
	private String checkBeanInXml(Class<?> cls ){
		String[] ns =ctx.getBeanNamesForType(cls);
		if(ns==null||ns.length<1){
			return null;
		}
		for(String n :ns){
			Class<?> cs = ctx.getType(ns[0]);
			if(cls==cs) return n;
		}
		return null;
	}

	@Override
	public <T> T getInstance(Class<T> clz, String name) {
		return ctx.getBean(name, clz);
	}


	@Override
	public boolean containsBeanDefinition(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		this.contextInitialized();
		
	}




}
