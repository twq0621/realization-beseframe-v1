/**
 * 
 */
package com.realization.framework.rule;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-11   上午09:19:45
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface RuleSelector {

	public boolean isSelect(Rule rule ,Object ...args);
}
