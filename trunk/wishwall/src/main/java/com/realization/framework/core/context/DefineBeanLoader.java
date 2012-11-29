package com.realization.framework.core.context;

import java.util.concurrent.ConcurrentMap;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-24   上午09:49:30
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface DefineBeanLoader {

	public void loadDefineBean(final ConcurrentMap<String, Object> map)throws Exception;
}
