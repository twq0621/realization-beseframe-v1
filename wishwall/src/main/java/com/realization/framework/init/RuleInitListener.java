/**
 * 
 */
package com.realization.framework.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

/**
 * 
 * 	保留，主要是这里记录一些很有用的注释
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-10   下午10:07:37
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Deprecated
@Service
public class RuleInitListener  implements ApplicationListener<ContextRefreshedEvent> , Ordered{
	
//	@Resource private  ApplicationContext ctx ;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		/*
		 * 这里遇到了一个很奇怪的问题
		 * 
		 *  RuleContextLoader和其相关实现类并没有在扫描路径下面，但是下面的bean获取竟然能获取成功，
		 *  并且通过String[] re = ctx.getBeanNamesForType(RuleContextLoader.class);
		 *  可以看到re中RuleContextLoader的所有实现类的注入名字都有。
		 *  更奇怪的是，当出去了当前类的时候，在ruleContexxtLoader通过
		 *  String[] re = ctx.getBeanNamesForType(RuleContextLoader.class);只能获取到ruleContexxtLoader当前bean的名字
		 *  当时如果进行RuleContextLoader的令一个实现类中，
		 *  RuleContextLoader context2 = ctx.getBean("ruleLoader2",RuleContextLoader.class);
		 *  依然还是可以看到所有实现类，这个非常不懂
		 */
//		RuleContextLoader contextLoader = ctx.getBean("ruleContexxtLoader",RuleContextLoader.class);
//		contextLoader.init();
		
		System.err.println("");
		
		/*
		 * TODO 
		 * 	重要
		 * String[] re = ctx.getBeanNamesForType(RuleContextLoader.class);
		 * 
		 * 这个方法可以获取RuleContextLoader.class所有实现类的beanname
		 * 
		 * 通过beanName可以获取RuleContextLoader.class的所有实现类
		 * 
		 */
	
	}

	@Override
	public int getOrder() {
		return 6;
	}

}
