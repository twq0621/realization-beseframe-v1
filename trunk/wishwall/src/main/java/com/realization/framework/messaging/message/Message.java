package com.realization.framework.messaging.message;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


import com.realization.framework.communicate.nio.Bytes;
import com.realization.framework.messaging.message.array.ArrayParam;
import com.realization.framework.messaging.message.map.MapElement;
import com.realization.framework.messaging.message.map.MapParam;
import com.realization.framework.messaging.message.table.TableCell;
import com.realization.framework.messaging.message.table.TableHead;
import com.realization.framework.messaging.message.table.TableParam;
import com.realization.framework.messaging.message.utils.ArrayUtils;
import com.realization.framework.messaging.message.utils.MessageUtils;
import com.realization.framework.messaging.message.utils.PropertyUtils;

/**
 * 消息对象.
 * 
 * @author xianghao
 * 
 */
public class Message extends Bytes implements Map<String,Object> {
	
	public final static byte PARAM_TYPE_STRING = 0;
	public final static byte PARAM_TYPE_INTEGER =1;
    public final static byte PARAM_TYPE_FLOAT = 2;
    public final static byte PARAM_TYPE_DATE = 3;
    public final static byte PARAM_TYPE_TABLE = 4;
    public final static byte PARAM_TYPE_ARRAY = 5;
    public final static byte PARAM_TYPE_COMPLEX = 6;
    /** Boolean/Object 类型,如果长度为1,则为Boolean类型, 否则为Object类型(通过序列化和反序列化进行编码和解码) */
    public final static byte PARAM_TYPE_OBJECT = 7;

    /** 超时消息的命令字 */
    public final static short TIME_OUT = 9999;
    
	/**
	 * 报文头
	 */
	private final Head head;

	/**
	 * 报文体
	 */
	private final Map<String,Map.Entry<String,Object>> bodys = new LinkedHashMap<String,Map.Entry<String,Object>>();

	/**
	 * 参数的序号
	 */
	private byte paramIndex = 0;
	
	/**
	 * 如果超时, 此属性将指向原消息的命令字.
	 * 
	 */
	private short source;

	public Message(Head head) {
		this.head = head;
	}
    public Message(Head head, Map<String, Object> data) {
        this.head = head;
        if (null!=data) {
            for (Map.Entry<String,Object> me: data.entrySet()) {
                addParamToMessage(me.getKey(), me.getValue());
            }
        }
    }

	/**
	 * 构造一个新消息.
	 * 
	 * <p>用于发送消息</p>
	 * 
	 * @param isResquest 是否请求包 true - 请求包, false - 回复包.
	 * @param needAnswer 是否需要对方回复.
	 * @param sessionId 会话ID.
	 * @param dictate 命令字.
	 * @param remoteSystem 系统节点ID.
	 * @param data 包体.
	 * 
	 * @throws MessageMakeException 组包失败
	 * 
	 */
	public Message(boolean isResquest, boolean needAnswer, int sessionId, int dictate, int remoteSystem, Map<String, Object> data) {
		this(new Head(sessionId, isResquest, needAnswer, (short)dictate, (byte)remoteSystem), data);
	}

    /**
     * 构造一个新消息.
     * 
     * <p>用于发送消息</p>
     * 
     * @param isResquest 是否请求包 true - 请求包, false - 回复包.
     * @param needAnswer 是否需要对方回复.
     * @param sessionId 会话ID.
     * @param dictate 命令字.
     * @param remoteSystem 系统节点ID.
     * 
     * @throws MessageMakeException 组包失败
     * 
     */
	public Message(boolean isResquest, boolean needAnswer, int sessionId, int dictate, int remoteSystem) {
		head = new Head(sessionId, isResquest, needAnswer, (short)dictate, (byte)remoteSystem);
	}

    /**
     * 根据包头和包体的字节流包装成一个消息对象.
     * 
     * <p>用于接收消息</p>
     * 
     * @param head 包头对象.
     * @param bytes 包体字节流.
     * 
     * 
     */
    public Message(Head head, byte[] bytes) {
        this(head,bytes,null);
    }

    /**
     * 根据包头和包体的字节流包装成一个消息对象.
     * 
     * <p>用于接收消息</p>
     * 
     * @param head 包头对象.
     * @param bytes 包体字节流.
     * @param order 字节编码顺序(小端或大端, 默认为大端).
     * 
     * 
     */
    public Message(Head head, byte[] bytes, ByteOrder order) {
        this.head = head;
        short length = head.getLength();
        if (length > 24) {
            bodys.putAll(new MapParam(copyOf(bytes,length-24),order));
        }
    }

    /**
     * 根据消息字节流包装成一个消息对象.
     * 
     * <p>用于接收消息</p>
     * 
     * @param bytes 消息字节流(包括包头和包体).
     * 
     */
	public Message(byte[] bytes) {
		this(new Head(bytes), copyOfRange(bytes,24,bytes.length));
	}

    /**
     * 根据消息字节流包装成一个消息对象.
     * 
     * <p>用于接收消息</p>
     * 
     * @param length 包长(包头和包体的总长).
     * @param bytes 消息字节流(不包括包长两字节内容).
     * 
     */
    public Message(short length, byte[] bytes) {
        this(new Head(length,bytes), copyOfRange(bytes,22,bytes.length));
    }
	
    /**
     * 根据字节流包装成一个消息对象.
     * 
     * @param bytes 消息字节流(包括包头和包体).
     * @param order 字节编码顺序(小端或大端, 默认为大端).
     * 
     * 
     */
    public Message(byte[] bytes, ByteOrder order) {
        this(new Head(bytes,order), getBytes(bytes,24), order);
    }
    public Message(short length, byte[] bytes, ByteOrder order) {
        this(new Head(length, bytes,order), getBytes(bytes,22), order);
    }

    /** 会话ID */
	public int getSessionID() {
		return head.getSessionID();
	}

	/** 方向, 请求方向 */
	public boolean isRequest() {
		return head.isRequest();
	}

	/** 需要对方应答 */
	public boolean isNeedAnswer() {
		return head.isNeedAnswer();
	}
	
    /** 保持连接 */
    public boolean isKeepAlive() {
        return head.isKeepAlive();
    }

	/** 命令字 */
	public short getDictate() {
		return head.getDictate();
	}

	/** 客户码 */
	public byte getRemoteSystem() {
		return head.getNode();
	}
    /** 客户码 */
    public byte getNode() {
        return head.getNode();
    }

	/** 时间截 */
	public Date getTimestamp() {
		return new Date(head.getTimestamp());
	}

    /** 超时请求消息的命令字 */
    public short getSource() {
        return source;
    }

	/**
	 * 消息的总字节数(包括包头和包体)
	 */
	public short getLength() {
		int length = 0;
		if (bodys != null) {
			for (Iterator<?> ite = bodys.values().iterator(); ite.hasNext();) {
				length += ((MapElement)ite.next()).getLength();
			}
		}
		length += 24;
		return (short)length;
	}


    // --
    
    /**
     * 通过参数名获取参数的值，值类型可能为 整形，浮点数，数组，表格（Table）
     * 
     * @param paramName
     * @param index
     * @return
     */
    private Object getParamValueByName(String paramName) {
        Map.Entry<String,Object> body = bodys.get(paramName);// 通过参数名获取对应的body
        if (body == null) {
            return null;
        }
        return body.getValue();
    }
    
    /**
     * 增加一个参数到报文中
     */
    private void addParamToMessage(String name, Object value) {
        if (value instanceof MapElement) addItem((MapElement)value);
        else addItem(new MapElement(paramIndex++, name, value));
    }

    // --
    
    @Override
    public void clear() {
        bodys.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        if (ArrayUtils.contains(Head.KEYS,(String)key)) return true;
        return bodys.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public Set<Map.Entry<String,Object>> entrySet() {
        Set<Map.Entry<String,Object>> set = new HashSet<Map.Entry<String,Object>>();
        for (String k: bodys.keySet()) {
            Object o = getParamValueByName(k);
            if (null!=o) set.add(new Entry(k, o));
        }
        return set;
    }

    /** 根据键取得一条消息项 */
    @Override
    public Object get(Object key) {
        if (!(key instanceof String)) return null;
        String name = (String)key;
        if ("remoteSystem".equals(name)) name = "node";
        if (ArrayUtils.contains(Head.KEYS,name)) {
            return PropertyUtils.getProperty(head,name);
        }
        return getParamValueByName(name);
    }

    /** 判断消息是否为空 */
    @Override
    public boolean isEmpty() {
        return bodys.isEmpty();
    }

    /** 所有消息键 */
    @Override
    public Set<String> keySet() {
        Set<String> set = bodys.keySet();
        set.addAll(Arrays.asList(Head.KEYS));
        return set;
    }

    /** 设置一个消息项 */
    @Override
    public Object put(String key, Object value) {
        Object old = getParamValueByName(key);
        addParamToMessage(key,value);
        return old;
    }

    @Override
    public void putAll(Map<? extends String, ?> values) {
        for (Map.Entry<? extends String,?> me: values.entrySet()) {
            try {addParamToMessage(me.getKey(),me.getValue());}
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

    /** 移除一个消息项 */
    @Override
    public Object remove(Object key) {
        return bodys.remove(key);
    }

    @Override
    public int size() {
        return bodys.size();
    }

    @Override
    public Collection<Object> values() {
        ArrayList<Object> vs = new ArrayList<Object>();
        for (String k: bodys.keySet()) {
            Object o = getParamValueByName(k);
            if (null!=o) vs.add(o);
        }
        return vs;
    }
	
    /** 取得一个表格项
      *
      *  
      * @param tableKey 表格项所对应的键
      * @param colKey 表格列所对应的键
      * @param rowIndex 第几行
      *
      * @return 返回表格的第row行的key2列的值
      *
      */
    public Object get(String tableKey, String colKey, int rowIndex) {
        Object value = get(tableKey);
        if (value instanceof Map<?,?>[]) {
            return ((Map<?,?>[])value)[rowIndex].get(colKey);
        }
        else throw new IllegalArgumentException("Item \""+tableKey+"\" (value: "+value+") is not a Table!");
    }
    
    /** 设置一个表格项数据.
      * 
      * @param tableKey 表格数据对应的健.
      * @param colKeys 表头.
      * @param data 表格数据.
      * 
      */
    public void put(String tableKey, String[] colKeys, Object... data) {
        TableHead[] ths = new TableHead[colKeys.length];
        TableCell[] cells = new TableCell[data.length];
        for (byte j=0; j<colKeys.length; j++) {
            byte type = PARAM_TYPE_STRING;
            for (int r=j; r<data.length; r+=colKeys.length) {
                if (null!=data[r]) {
                    type = MessageUtils.type(data[r]);
                    break;
                }
            }
            ths[j] = new TableHead(j, type, colKeys[j]);
            int i;
            for (short r=0; (i=r*colKeys.length+j)<data.length; r++) {
                cells[i] = new TableCell(ths[j], r, data[i]);
            }
        }
        addParamToMessage(tableKey, new TableParam(ths,cells));
    }

   /** 设置一个表格项数据.
     * 
     * @param tableKey 表格数据对应的健.
     * @param colKeys 表头,用半角逗号分隔.
     * @param data 表格数据.
     * 
     */
   public void put(String tableKey, String colKeys, Object... data) {
       put(tableKey, colKeys.split(","), data);
   }

   /** 设置一个数组项数据.
     * 
     * @param key 表格数据对应的健.
     * @param data 表格数据.
     * 
     */
   public void put(String key, Object... data) {
       addParamToMessage(key, new ArrayParam(data));
   }
    
    // --	

	@Override
	public byte[] toBytes() {
		return toBytes((ByteOrder)null);
	}
	
    @Override
    public byte[] toBytes(ByteOrder order) {
        if (0==head.getLength()) head.setLength(getLength());
        byte[] bs = new byte[getLength()];
        putObject(bs, 0, head, order);
        int index = 24;
        if (bodys != null) {
            for (Iterator<?> ite = bodys.values().iterator(); ite.hasNext();) {
                byte[] bbs = ((Bytes)ite.next()).toBytes(order);
                putBytes(bs, index, bbs);
                index += bbs.length;
            }
        }
        return bs;
    }

	// --
	
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((head == null) ? 0 : head.hashCode());
        //result = prime * result + ((bodys == null) ? 0 : bodys.hashCode());
        result = prime * result + paramIndex;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Message other = (Message) obj;
        if (head == null) {
            if (other.head != null) return false;
        }
        else if (!head.equals(other.head)) return false;
        if (bodys == null) {
            if (other.bodys != null) {
                //System.out.println(bodys+"\n"+other.bodys);
                return false;
            }
        }
        else if (!bodys.equals(other.bodys)) {
            //System.out.println(bodys+"\n"+other.bodys);
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (0==head.getLength()) head.setLength(getLength());
        return "Message [head=" + head + ", bodys=" + bodys + "]";
    }
	
	// --
	
	/**
     * 往一个报文对象中添加一个body
     * 
     */
    private void addItem(Map.Entry<String,Object> item) {
        bodys.put(item.getKey(), item);
    }


	public static void main(String[] args) throws Exception {

		String[] str = new String[2];
		str[0] = "s1";
		str[1] = "s2";
		Object[] ss = new Object[1];
		ss[0] = str;

		Table t = new Table(2, 3);
		t.set(0, 0, "column0");
		t.set(0, 1, "column1");
		t.set(0, 2, "column2");
		t.set(1, 0, "value");

		t.set(1, 1, 2);
		t.set(1, 2, "xianghao");

		Table[] sss = new Table[1];
		sss[0] = t;

		Table t2 = new Table(2, 2);
		t2.set(0, 0, "col0");
		t2.set(0, 1, "col1");
		t2.set(1, 0, "1");
		t2.set(1, 1, str);

		Map<String, Object> data = new LinkedHashMap<String, Object>();

		data.put("tt", t2);
		data.put("table", t);
		data.put("float", 0.1f);
		data.put("integer", 1);
		data.put("short", (short) 2);
		data.put("long", (long) 2);
		data.put("double", 2.0);
		//
		Message m = new Message(true, true, 0, 0, 0, data);

		m.head.setLength(m.getLength());
		System.out.println("m1 - "+m);
		// System.out.println(((ArrayParam)m.getBodys().get("array").getValue()).getParamType());
		Message m2 = new Message(m.toBytes());
		System.out.println("m2 - "+m2);
		System.out.println("m2.table - "+m2.getParamValueByName("table"));
		System.out.println("m1 = m2 = "+m.equals(m2));
		// System.out.println(m2.toBytes());

		// System.out.println(ts);
	}

	private static class Entry implements Map.Entry<String,Object> {
	    final String key;
	    Object value;
        public Entry(String key, Object value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public String getKey() {
            return key;
        }
        @Override
        public Object getValue() {
            return value;
        }
        @Override
        public Object setValue(Object value) {
            Object old = this.value;
            this.value = value;
            return old;
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((key == null) ? 0 : key.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof Map.Entry<?,?>)) return false;
            Map.Entry<?,?> other = (Map.Entry<?,?>) obj;
            if (key == null) {
                if (other.getKey() != null) return false;
            }
            else if (!key.equals(other.getKey())) return false;
            if (value == null) {
                if (other.getValue() != null) return false;
            }
            else if (!value.equals(other.getValue())) return false;
            return true;
        }
        @Override
        public String toString() {
            return "Entry[key="+key+", value="+value+"]";
        }
	    
	}

}
