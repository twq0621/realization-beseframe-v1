//package com.realization.test;
//
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.runner.RunWith;
//import org.junit.runners.Suite;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.realization.wishwall.dao.UserDao;
//
///**
// *  @author xiai_fei
// *
// *  @create-time	2012-11-14   下午09:30:15
// *
// *  @version 1.0 
// *  @description  realization-BaseFrame
// *  @版权所有     Realization 团队
// */
//@RunWith(Suite.class)
//@Suite.SuiteClasses({})
//public class TestSuit {
//
//	private static ApplicationContext ctx;
//	
//	@BeforeClass
//	public static void setUp() throws Exception{
//		String path = "/META-INF/applicationContext.xml";
//		 setCtx(new ClassPathXmlApplicationContext(path));
//		getCtx().getBean(UserDao.class);
//	}
//	
//	@AfterClass
//	public static void tearDown() throws Exception {
//		
//	}
//
//	/**
//	 * @param ctx the ctx to set
//	 */
//	public static void setCtx(ApplicationContext ctx) {
//		TestSuit.ctx = ctx;
//	}
//
//	/**
//	 * @return the ctx
//	 */
//	public static ApplicationContext getCtx() {
//		return ctx;
//	}
//}
