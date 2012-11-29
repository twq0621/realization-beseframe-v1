package com.realization.framework.messaging;

import java.util.Map;

/**
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-13   下午09:49:37
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public interface IpcMessageFactory {

    /**
     * 创建一个IpcMessage.
     * 
     */
    IpcMessage newIpcMessage(Map<String,Object> config) throws Exception;
    
    /**
     * 创建一个IpcMessage.
     * 
     * @param ipcMeta 元数据.
     * 
     * 
     */
    IpcMessage newIpcMessage(IpcMeta ipcMeta) throws Exception;
    
//    IpcMessage newIpcMessage(Message message) throws Exception;
    
}