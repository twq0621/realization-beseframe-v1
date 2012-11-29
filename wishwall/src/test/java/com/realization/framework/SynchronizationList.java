package com.realization.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-26   下午10:03:00
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class SynchronizationList {

	public List<String > testList =Collections.synchronizedList(new ArrayList<String>());
	
	public void addElement(String value){
		this.testList.add(value);
	}
	
	public static void main(String[] args) {
		SynchronizationList sl = new SynchronizationList();
		sl.addElement("test");
		System.err.println("ok");
		sl.startThread();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	public void startThread(){
		MyThread m = new MyThread();
		m.start();
	}
	
	class MyThread extends Thread{

		@Override
		public void run() {
			testList.add("1234");
			System.err.println("thread add success ");
			Iterator<String> i = testList.iterator();
			while(i.hasNext()){
				System.err.println(i.next());
			}
		}
		
	}
	
	
}
