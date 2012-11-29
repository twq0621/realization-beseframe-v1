package com.realization.wishwall.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-15   下午10:28:09
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class SpringContextUtils implements ServletContextListener{
	
	private static ApplicationContext ctx ;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent sc) {
		ctx = WebApplicationContextUtils.getWebApplicationContext(sc.getServletContext());
	}

	public ApplicationContext getAppContext(){
		return  ctx;
	}
}
