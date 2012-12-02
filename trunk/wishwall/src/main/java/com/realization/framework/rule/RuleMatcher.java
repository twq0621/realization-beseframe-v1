/**
 * 
 */
package com.realization.framework.rule;

import com.realization.framework.rule.entity.Rule;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-11   上午10:07:09
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface RuleMatcher<T> {

	/**
	 * 是否要进行检查的target
	 * @param t
	 * @return
	 */
	public boolean isTarget(T t) ;
	
	/**
	 * 对target的详细内容进行匹配
	 * @param t	检测条件key
	 * @param rule	当前规则
	 * @param params	被检测的参数
	 * @return
	 */
	public boolean match(T t , Rule rule ,Object params);
}
