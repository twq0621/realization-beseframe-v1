package com.realization.framework.messaging.message.array;

import static com.realization.framework.messaging.message.utils.MessageUtils.value;
import static com.realization.framework.messaging.message.utils.MessageUtils.wrap;

import java.nio.ByteOrder;

import com.realization.framework.communicate.nio.Bytes;
import com.realization.framework.messaging.message.Element;


/**
 * 
 * @author xianghao
 *
 */
public class ArrayElement extends Bytes implements Element {
	private short length;
    private byte[] buffer;
    
	private final ArrayParam container;
	
	private final Object value;
	
	public ArrayElement(ArrayParam container, Object value) {
	    this.container = container;
		this.value = wrap(value);
	}
    public ArrayElement(ArrayParam container, byte[] bs) {
        this(container,bs,null);
    }
    public ArrayElement(ArrayParam container, byte[] bs, ByteOrder order) {
        this.container = container;
        this.length = getShort(bs,0,order);
        value = value(container.getType(), getBytes(bs,2), order);
    }
    @Override
	public short getLength() {
	    if (length==0 && null!=value) {
	        if (null==buffer) buffer = toBytes(value);
	        if (null!=buffer) length = (short)buffer.length;
	    }
		return length;
	}
    @Override
    public byte getType() {
        return container.getType();
    }
    @Override
	public Object getValue() {
		return value(value,getType());
	}
	@Override
	public String toString() {
		return "ArrayElement [length=" + length + ", value=" + value + "]";
	}
	@Override
	public byte[] toBytes() {
	    return toBytes((ByteOrder)null);
	}

    @Override
    public byte[] toBytes(ByteOrder order) {
        byte[] elementBytes = new byte[2+getLength()];
        if (null!=order || null==buffer) buffer = toBytes(value,length,order);
        putShort(elementBytes,0,length,order);
        return putBytes(elementBytes,2,buffer);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + length;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this==obj) return true;
		if (null==obj) return false;
		if (getClass() != obj.getClass()) return false;
		ArrayElement other = (ArrayElement) obj;
		if (length != other.length) return false;
		if (value == null) {
			if (other.value != null) return false;
		}
		else if (!value.equals(other.value)) return false;
		return true;
	}
	
}
