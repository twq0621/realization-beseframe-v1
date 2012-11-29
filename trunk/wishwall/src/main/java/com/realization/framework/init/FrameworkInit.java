package com.realization.framework.init;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.realization.framework.core.init.CoreIniter;

/**
 * 
 * 	基础框架初始化服务，启动基础框架的相关内容
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-12   下午09:55:16
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service
public class FrameworkInit  implements ApplicationListener<ContextRefreshedEvent> , Ordered{
	
	@Resource CoreIniter coreIniter;
	
	private static boolean isInit = false ;

	@Override
	public int getOrder() {
		return 3;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if(!isInit){
			coreIniter.init();
			isInit=true;
		}
	}

}
