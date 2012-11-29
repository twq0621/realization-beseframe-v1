/**
 * 
 */
package com.realization.framework.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-11   上午08:33:53
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class CommandChain {

	private Iterator<AbstractCommand> cmdItr ;
	
	public CommandChain(Iterator<AbstractCommand> it){
		this.cmdItr =it;
	}
	
	private List<Object>resultList = new ArrayList<Object>();
	
	public Object doProcess(Object...args){
		if(this.cmdItr.hasNext()){
			this.cmdItr.next().execute(this, args);
		}else{
			if(args!=null&&args.length>0){
				for(Object o :args){
					resultList.add(o);
				}
			}
		}
		return args ;
	}

	/**
	 * @return the resultList
	 */
	public List<Object> getResultList() {
		return resultList;
	}
}
