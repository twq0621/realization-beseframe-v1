package com.realization.framework.messaging.message.table;

import java.nio.ByteOrder;

import com.realization.framework.communicate.nio.Bytes;
import com.realization.framework.messaging.message.Element;

/**
 * 表头对象
 * 
 * @author xianghao
 * 
 */
public class TableHead extends Bytes implements Element {
	/**
	 * 表头的序号
	 */
	protected final byte index;

	/**
	 * 表头对应的列的参数类型
	 */
	protected final byte type;

	/**
	 * 表头名字的长度
	 */
	protected final int length;
	
	/**
	 * 表头名字
	 */
	protected final String headName;

	public TableHead(byte index, byte paramType, String headName) {
		this.index = index;
		this.type = paramType;
		this.length = headName.length();
		this.headName = headName;
	}
    public TableHead(byte[] bs, ByteOrder order) {
        byte complex = bs[0];
        index = (byte) ((complex >> 4) & 0x0f);
        type = (byte) (complex & 0x07);
        length = bs[1];
        headName = getString(bs,2,length);
    }
    public TableHead(byte[] bs) {
        this(bs,null);
    }

	public byte getIndex() {
		return index;
	}
    public String getHeadName() {
        return headName;
    }
    
	@Override
	public byte getType() {
		return type;
	}
	@Override
	public short getLength() {
		return (short)length;
	}
    @Override
    public Object getValue() {
        return headName;
    }
	@Override
	public String toString() {
		return "TableHead[index=" + index + ", type=" + type + ", length=" + length +  ", name=" + headName + "]";
	}

	@Override
	public byte[] toBytes() {
		return concat(new byte[]{(byte)((index<<4)+type),(byte)length}, toBytes(headName));

	}
    @Override
    public byte[] toBytes(ByteOrder order) {
        return toBytes();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + length;
		result = prime * result + ((headName == null) ? 0 : headName.hashCode());
		result = prime * result + index;
		result = prime * result + type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;
		TableHead other = (TableHead) obj;
		if (length != other.length) return false;
		if (headName == null) {
			if (other.headName!=null) return false;
		}
		else if (!headName.equals(other.headName)) return false;
		if (index != other.index) return false;
		if (type != other.type) return false;
		return true;
	}
}
