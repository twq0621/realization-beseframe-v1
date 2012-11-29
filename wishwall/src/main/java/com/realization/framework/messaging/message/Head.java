package com.realization.framework.messaging.message;

import java.nio.ByteOrder;
import java.util.Random;

import com.realization.framework.communicate.nio.Bytes;

/**
 * 包头.
 *
 */
public class Head extends Bytes {
    final static public String[] KEYS = {"sessionID","request","needAnswer","keepAlive","dictate","node","timestamp"};
	
	private final int sessionID;
	/**
	 * ture为请求包
	 */
	private final boolean request;
	
	/**
	 * true 需要响应
	 */
	private final boolean needAnswer;
	
	/**
	 * 报文码（命令字）
	 */
	private final short dictate;
	
	/**
	 * 包体长
	 */
	private short length;
	
	/**
	 * 客户码
	 */
	private final byte node;
	
	/**
	 * 时间
	 */
	private final long timestamp;
	
	/** 保持连接 */
	private final boolean keepAlive;
	
	/**
	 * 根据包头字节数组(24字节)包装一个包头对象.
	 * 
	 * @param bs 包头字节数组.
	 * 
	 */
	public Head(byte[] bs) {
        this(bs,null);
	}
    public Head(short length, byte[] bs) {
        this(length,bs,null);
    }
	
    /**
     * 根据包头字节数组(24字节)包装一个包头对象.
     * 
     * @param bs 包头字节数组.
     * @param order 字节编码顺序(小端或大端, 默认为大端).
     * 
     */
    public Head(byte[] bs, ByteOrder order) {
        this.length = getShort(bs,0,order);
        short complex = getShort(bs,2);
        request = ((complex & 0x8000)!=0);
        needAnswer = ((complex & 0x4000)!=0);
        dictate = (short)(complex & 0x3fff);
        sessionID = getInt(bs,4,order);
        node = bs[8];
        timestamp = getLong(bs,9,order);
        keepAlive = ((bs[17] & 0x80)!=0);
    }
    public Head(short length, byte[] bs, ByteOrder order) {
        this.length = length;
        short complex = getShort(bs,0);
        request = ((complex & 0x8000)!=0);
        needAnswer = ((complex & 0x4000)!=0);
        dictate = (short)(complex & 0x3fff);
        sessionID = getInt(bs,2,order);
        node = bs[6];
        timestamp = getLong(bs,7,order);
        keepAlive = ((bs[15] & 0x80)!=0);
    }
	
    /**
     * 构造一个包头对象.
     * 
     * @param sessionID 会话ID.
     * @param request 是否请求包.
     * @param needAnswer 是否需要对方回复.
     * @param keepAlive 保持连接.
     * @param dictate 命令字.
     * @param note 节点ID.
     * @param timestamp 时间截(默认为当前时间).
     * 
     */
	public Head(int sessionID, boolean request, boolean needAnswer, boolean keepAlive, short dictate, byte node, long timestamp) {
		super();
		this.sessionID = sessionID;
		this.request = request;
		this.needAnswer = needAnswer;
        this.keepAlive = keepAlive;
		this.dictate = dictate;
		this.node = node;
		this.timestamp = timestamp;
	}
	
    /**
     * 构造一个包头对象.
     * 
     * @param sessionID 会话ID.
     * @param request 是否请求包.
     * @param needAnswer 是否需要对方回复.
     * @param keepAlive 保持连接.
     * @param dictate 命令字.
     * @param note 节点ID.
     * @param timestamp 时间截(默认为当前时间).
     * 
     */
    public Head(int sessionID, boolean request, boolean needAnswer, short dictate, byte node, long timestamp) {
        this(sessionID,request,needAnswer,true,dictate,node,timestamp);
    }
	
    /**
     * 构造一个包头对象.
     * 
     * @param sessionID 会话ID.
     * @param request 是否请求包.
     * @param needAnswer 是否需要对方回复.
     * @param keepAlive 保持连接.
     * @param dictate 命令字.
     * @param note 节点ID.
     * 
     */
	public Head(int sessionID, boolean request, boolean needAnswer, boolean keepAlive, short dictate, byte node) {
		this(sessionID,request,needAnswer,keepAlive,dictate,node,System.currentTimeMillis());
	}
	
    /**
     * 构造一个包头对象.
     * 
     * @param sessionID 会话ID.
     * @param request 是否请求包.
     * @param needAnswer 是否需要对方回复.
     * @param dictate 命令字.
     * @param note 节点ID.
     * 
     */
    public Head(int sessionID, boolean request, boolean needAnswer, short dictate, byte node) {
        this(sessionID,request,needAnswer,true,dictate,node,System.currentTimeMillis());
    }
	
	public int getSessionID() {
		return sessionID;
	}
	public boolean isRequest() {
		return request;
	}
	public boolean isNeedAnswer() {
		return needAnswer;
	}
	public short getLength() {
		return length;
	}
	public void setLength(short bodyLength) {
		this.length = bodyLength;
	}
	public byte getNode() {
		return node;
	}
	public short getDictate() {
		return dictate;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public boolean isKeepAlive() {
        return keepAlive;
    }
	
    @Override
	public String toString() {
		return "Head [id=" + sessionID + ", req=" + request + ", res=" + needAnswer + ", alive=" + keepAlive + ", dictate=" + dictate + ", len=" + length + ", node=" + node + "]";
	}
	

	@Override
	public byte[] toBytes() {
		return toBytes((ByteOrder)null);
	}
    @Override
    public byte[] toBytes(ByteOrder order) {
        byte[] hb = new byte[24];
        putShort(hb,0,length,order);
        short complex = dictate;
        if (needAnswer) complex = (short)(complex | 0x4000);
        if (request) complex = (short)(complex | 0x8000);
        putShort(hb,2,complex,order);
        putInt(hb,4,sessionID,order);
        hb[8] = node;
        putLong(hb,9,timestamp,order);
        if (keepAlive) hb[17] = (byte)0x80;
        return hb;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + length;
		result = prime * result + dictate;
		result = prime * result + (request ? 1231 : 1237);
		result = prime * result + (needAnswer ? 1231 : 1237);
		result = prime * result + node;
		result = prime * result + sessionID;
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
        result = prime * result + (keepAlive?1:0);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Head other = (Head) obj;
		if (length != other.length) return false;
		if (dictate != other.dictate) return false;
		if (request != other.request) return false;
		if (needAnswer != other.needAnswer) return false;
		if (node != other.node) return false;
		if (sessionID != other.sessionID) return false;
		if (timestamp != other.timestamp) return false;
        if (keepAlive != other.keepAlive) return false;
		return true;
	}

	
	public static void main(String[] args) {

		Head head =  new Head(new Random().nextInt(), true, true, (short)10000,(byte)1);
		System.out.println("原报文头:"+head);
        System.out.println("保存连接(head.keepAlive): "+head.keepAlive);
		System.out.println("编译后"); 
		byte[]bs = head.toBytes();
		for(int i =0 ;i<bs.length;i++){
			 System.out.print(bs[i]+" ");
		 }
		Head head1 = new Head(bs);
		System.out.println();
		System.out.println("解码之后："+ head1);
		System.out.println(head.equals(head1));
		System.out.println("保存连接(head1.keepAlive): "+head1.keepAlive);
	}

}
