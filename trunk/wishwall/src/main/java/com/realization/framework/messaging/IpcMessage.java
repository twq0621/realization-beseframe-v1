package com.realization.framework.messaging;

import java.util.Map;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-12   下午08:08:34
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface IpcMessage extends IpcMeta{

	/** 设置头部*/
	void setMsgHead(Map<String,Object> msgHead);

	/** 设置body*/
    void setMsgData(Map<String,Object> msgData);
    
    /**
     * 判断当前包是否是指定包的回复包.
     * 
     */
    boolean isExpectedBy(IpcMessage source);
    
    /**
     * 判断来包是否为当前包的回复包.
     * 
     */
    boolean isWaitingFor(IpcMessage income);
    
    /** 判断此包是否应答包 */
    boolean isReponse();
}
