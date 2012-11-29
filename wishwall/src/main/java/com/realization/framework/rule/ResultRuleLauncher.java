/**
 * 
 */
package com.realization.framework.rule;

import java.util.Map;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-11   上午11:20:51
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface ResultRuleLauncher extends RuleLauncher{
	public Map<String,Object> launchWithResult(int priority ,Object...args);
}
