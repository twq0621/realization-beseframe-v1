package com.realization.framework.messaging.message;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.realization.framework.messaging.IpcMessageFactory;
import com.realization.framework.messaging.IpcMeta;


/**
 * 
 *  @author xiai_fei
 *
 *  @create-time	2012-11-13   下午09:49:58
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
@Service("ipcMessageFactoryImpl")
public class IpcMessageFactoryImpl implements IpcMessageFactory {
    final private static Log log = LogFactory.getLog(IpcMessageFactoryImpl.class);
    final private static String[] HKS = {"msgCode","tranSeq","sendType","sendNode","rcvType","rcvNode","tranCode","msgID","msgDate","msgTime"};

    public IpcMessage newIpcMessage(Map<String,Object> config) throws Exception {
        if (log.isDebugEnabled()) log.debug("create ipc message from "+config+" ...");
        IpcMessage im = new com.realization.framework.messaging.message.IpcMessage();
        if (null!=config && !config.isEmpty()) {
            config = new HashMap<String,Object>(config);
            Object o;
            for (String k: HKS) {
                if (null!=(o=config.remove(k))) im.setMsgHead(k, o.toString());
            }
        }
        configurate(im);
        return im;
    }
    
    public IpcMessage newIpcMessage(IpcMeta ipcMeta) throws Exception {
        if (log.isDebugEnabled()) log.debug("create ipc message from "+ipcMeta+" ...");
        IpcMessage im = new com.realization.framework.messaging.message.IpcMessage(ipcMeta);
        configurate(im);
        return im;
    }
    
    private void configurate(IpcMessage im) {
//        String prefix = IpcMessageFactory.class.getName();
//        Object rcvTyp = im.getMsgHead("rcvType");
//        Object msgCod = im.getMsgHead("msgCode");
//        String key = prefix;
//        if (null!=rcvTyp) key += "."+rcvTyp;
//        if (null!=msgCod) key += "."+msgCod;
//        String s = context.getProperty(key);
//        if (null!=s) FieldHelper.setFieldValue(im, "duration", new Integer(s));
//        else {
//            key = "com.angelshine.framework.common.IpcMessageFactory";
//            if (null!=rcvTyp) key += "."+rcvTyp;
//            if (null!=msgCod) key += "."+msgCod;
//            int dura = context.getProperty(key, im.waitingDuration());
//            if (dura>0) FieldHelper.setFieldValue(im, "duration", dura);
//        }
//        key = IpcMessage.class.getName();
//        if (null!=rcvTyp) key += "."+rcvTyp;
//        if (null!=msgCod) key += "."+msgCod;
//        s = context.getProperty(key);
//        if (null!=s && s.length()>0) FieldHelper.setFieldValue(im, "responseMsgCode", s);
//        
//        // 加密属性:
//        s = context.getProperty(prefix+".ENCRYPTS."+msgCod, context.getProperty(prefix+".ENCRYPTS."+msgCod));
//        if (null!=s && s.length()>0) im.setMsgProperty("encrypts", s);
//        // DAC属性:
//        s = context.getProperty(prefix+".DACS."+msgCod, context.getProperty(prefix+".DACS."+msgCod));
//        if (null!=s && s.length()>0) im.setMsgProperty("dacs", s);
//        // TODO: 其它属性.
    }

//	@Override
//	public com.realization.framework.messaging.IpcMessage newIpcMessage(
//			Message msg) throws Exception {
//		Map<String,Object>msgHead = new HashMap<String, Object>();
//		msgHead.put("msgCode", msg.getDictate()+"");
//		msgHead.put("tranSeq", msg.getSessionID()+"");
//		msgHead.put("sysId", msg.getRemoteSystem()+"");
//		com.realization.framework.messaging.IpcMessage ipcMsg=  new com.realization.framework.messaging.message.IpcMessage(msgHead);
//		ipcMsg.setMsgData(msg);
//		return ipcMsg;
//	}
//    
}
