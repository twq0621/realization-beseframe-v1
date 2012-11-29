package com.realization.framework.communicate.nio;

import java.util.concurrent.Future;

/**
 * 	异步消息包守候者
 * 
 * 		<br/>守候每一个上送的消息包，进行初步处理。守候操作会在消息处理成功或者超时之后结束
 * 
 * @author xu.jianpu
 *
 *  2012-10-24  上午11:19:34
 * 	 @version 1.0
 */
public interface Daemon {

	   /** 取回一个需要回复的请求消息的Future    因为如果需要消息是需要回复的话，
	    * 
	    * 系统在发出消息之后后阻塞当前线程，进行同步等待操作。会有一个对应的future
	    * 
	    * */
	Future<?> getFuture(Object request);
	
	boolean isResponse(Object response);
	
	boolean isNeedAnswer(Object request);
	
	boolean checkResponse(Object response);
	
	/** 超时时间     有什么用？*/
	long timeout(Object response);
	/** 该异步消息守候者会对很多消息进行守候，相当于有一个守候集合。来了一个异步消息会放进去，所以需要移除*/
	void drop(Object response);
	
	
    /**
     * 返回结果并且可能抛出异常的任务。实现者定义了一个不带任何参数的叫做 call 的方法。 

		Callable 接口类似于 Runnable，两者都是为那些其实例可能被另一个线程执行的类设计的。但是 Runnable 不会返回结果，并且无法抛出经过检查的异常

     */
	class Callable implements java.util.concurrent.Callable<Object>{

		public final Object request ;
		
		public final Daemon daemon ; //不大明白这一类用法，类中又持有自身的句柄？
		private Object result ;
		
		public Callable(Daemon daemon , Object request){
			this.request= request;
			this.daemon =daemon;
		}

		public void setResult(Object result) {
			this.result = result;
			if(null!=result){
				 synchronized (this) {notifyAll();}    //这个不懂，会存在什么并发风险？   ans： 在call方法中如果result为null会等待，这个就是用来唤醒等待的。
			}
		}

		@Override
		public Object call() throws Exception {
			if(null==result){
				 synchronized (this) {wait(daemon.timeout(request)*1000);}   //不懂这么为什么要用同步？wait和notify等必须在同步块中执行
				//wait后会释放当前对象的锁
			}
			daemon.drop(request);//这个移除有什么用？为什么要移除？不移除有什么后果？
			return result;
		}
		
	}
}
