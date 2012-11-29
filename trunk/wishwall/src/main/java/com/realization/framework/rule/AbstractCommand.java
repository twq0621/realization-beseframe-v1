/**
 * 
 */
package com.realization.framework.rule;


/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-11   上午12:13:10
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public abstract class AbstractCommand {
	
	public abstract void execute(CommandChain chain , Object...args);
}
