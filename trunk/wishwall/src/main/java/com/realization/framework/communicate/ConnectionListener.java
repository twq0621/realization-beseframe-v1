package com.realization.framework.communicate;

import com.realization.framework.communicate.nio.Channel;
/**
 * 	连接监听器
 * 
 * @author xu.jianpu
 *
 *  2012-10-23  下午05:36:58
 * 	 @version 1.0
 */
public interface ConnectionListener {
    enum SITE {SERVER,CLIENT,ALL}
    enum TYPE {RECEIVE,SEND,EXCEPT,OPEN,CLOSE,CREATE,IDLE,DESTROY,BOUND,UNBOUND}

    void notify(TYPE type, Channel connection, Object fact) throws Exception;
    
    boolean accept(SITE site, TYPE... types);
}
