package com.realization.framework.messaging.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.realization.framework.messaging.IpcMeta;

/**
 *  @author xiai_fei
 *
 *  @create-time	2012-11-13   下午09:48:39
 *
 *  @version 1.0 
 *  @description  realization-BaseFrame
 *  @版权所有     Realization 团队
 */
public class IpcMessage implements com.realization.framework.messaging.IpcMessage , Comparable<IpcMessage>, Serializable{

	private static final long serialVersionUID = 1L;
	//private static final Log log = LogFactory.getLog(IpcMessage.class);
	
	/** 消息体 */
	private Map<String,Object> msgData;
    
	/** 消息头 */
	private Map<String,Object> msgHead;
    
	/** 消息缓存 */
	private transient Map<String,Object> msgAttribute;
	
    @SuppressWarnings("unused")
    private transient String responseMsgCode;
    private transient int duration;
    private transient boolean response;

	// --


    @Override
    public Map<String, Object> getMsgHeads() {
        return msgHead;
    }

    @Override
    public Map<String,Object> getMsgData() {
        return msgData;
    }
    @Override
    public void setMsgData(Map<String,Object> map) {
        if (null!=map && !map.isEmpty()) {
            if (null==msgData) {
                msgData = new HashMap<String,Object>();
            }
            msgData.putAll(map);
        }
        /*if (message instanceof SmsMessage) {
            SmsMessage sms = (SmsMessage)message;
            msgData.put("simaid", sms.simaid);
            msgData.put("func", sms.func);
            msgData.put("mainSeq", sms.mainSeq);
            msgData.put("seqType", sms.seqType);
            msgData.put("subSeq", sms.subSeq);
            msgData.put("alg", sms.alg);
            msgData.put("inf", sms.inf);
            msgData.put("isEncrypt", sms.isEncrypt);
        }*/
    }
    
    @Override
    public Object getMsgHead(String name) {
        if (null==msgHead) return null;
        else return msgHead.get(name);
    }
    @Override
    public void setMsgHead(String name, Object value) {
        if (null==msgHead) msgHead = new HashMap<String,Object>();
        msgHead.put(name, value);
    }

    @Override
    public Object getMsgBody(String name) {
        if (null==msgData) return null;
        else return msgData.get(name);
    }
    @Override
    public void setMsgBody(String name, Object value) {
        if (null==msgData) msgData = new HashMap<String,Object>();
        msgData.put(name, value);
    }


    @Override
    public Object getMsgAttribute(String name) {
        if (null==msgAttribute) return null;
        return msgAttribute.get(name);
    }
    @Override
    public void setMsgAttribute(String name, Object value) {
        if (null==msgAttribute) {
            msgAttribute = new HashMap<String,Object>();
        }
        msgAttribute.put(name, value);
    }
    
    @Override
    public int waitingDuration() {
        return duration;
    }
    @Override
    public boolean isExpectedBy(com.realization.framework.messaging.IpcMessage source) {
        return true;
    }
    @Override
    public boolean isWaitingFor(com.realization.framework.messaging.IpcMessage income) {
        return false;
    }
    @Override
    public boolean isReponse() {
        return response;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }

    // --


	public IpcMessage() {}
    public IpcMessage(Map<String,Object> msgHead) {
        if (null!=msgHead && !msgHead.isEmpty()) {
            if (null==this.msgHead) {
                this.msgHead = new HashMap<String,Object>();
            }
            this.msgHead.putAll(msgHead);
        }
    }
	public IpcMessage(String sendType, String sendNode, String rcvType, String rcvNode, String tranSeq,
            String tranCode, String msgCode, String msgID, String msgDate, String msgTime) {
        this.msgHead = new HashMap<String,Object>();
        this.msgHead.put("sendType",sendType);
        this.msgHead.put("sendNode",sendNode);
        this.msgHead.put("rcvType",rcvType);
        this.msgHead.put("rcvNode",rcvNode);
        this.msgHead.put("tranSeq",tranSeq);
        this.msgHead.put("tranCode",tranCode);
        this.msgHead.put("msgCode",msgCode);
        this.msgHead.put("msgID",msgID);
    }

	@Deprecated
    public IpcMessage(IpcMeta ipcMeta) {
    }
    public void setResponseMsgCode(String msgCode) {
        this.responseMsgCode = msgCode;
    }
    

    @Override
    public int compareTo(IpcMessage ipcMessage) {
        if (null==ipcMessage) return -1;
        return hashCode()-ipcMessage.hashCode();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((msgHead == null) ? 0 : msgHead.hashCode());
        result = prime * result + ((msgData == null) ? 0 : msgData.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        IpcMessage other = (IpcMessage) obj;
        if (msgHead == null) {
            if (other.msgHead != null) return false;
        }
        else if (!msgHead.equals(other.msgHead)) return false;
        if (msgData == null) {
            if (other.msgData != null) return false;
        }
        else if (!msgData.equals(other.msgData)) return false;
        return true;
    }

    @Override
    public String toString(){
        String s = "IpcMessage [msgHead="+msgHead+", msgData="+msgData+"]";
        return s;

    }

	@Override
	public void setWaitDuration(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMsgHead(Map<String, Object> msgHead) {
		// TODO Auto-generated method stub
		
	}

}
