package com.realization.framework.messaging.message.map;

import static com.realization.framework.messaging.message.utils.MessageUtils.value;
import static com.realization.framework.messaging.message.utils.MessageUtils.wrap;
import static com.realization.framework.messaging.message.utils.MessageUtils.type;

import java.nio.ByteOrder;
import java.util.Map;

import com.realization.framework.communicate.nio.Bytes;
import com.realization.framework.messaging.message.Element;


public class MapElement extends Bytes implements Map.Entry<String,Object>,Element {

    private byte nameLength;
    private short valueLength;
    private byte[] buffer;
	private final byte type;
	
	private final byte index;
	private final String name ;
	private final Object value;

	protected MapElement(byte parmaType, byte paramIndex, byte nameLength, short valueLength, String name, Object value) {
		super();
		this.type = parmaType;
		this.index = paramIndex;
		this.nameLength = nameLength;
		this.valueLength = valueLength;
		this.name = name;
		this.value = value;
	}
    public MapElement(byte paramIndex, String name, Object value) {
        super();
        this.index = paramIndex;
        this.name = name;
        this.type = type(value);
        this.value = wrap(value);
    }
    @Override
    public short getLength(){
		return  (short)(4+getNameLength()+getValueLength());
	}
    @Override
	public byte getType() {
		return type;
	}
	public byte getIndex() {
		return index;
	}
	public byte getNameLength() {
	    if (nameLength==(byte)0) {
	        nameLength = (byte)name.length();
	    }
		return nameLength;
	}
	public short getValueLength() {
	    if (valueLength==(byte)0 && null!=value) {
	        if (null==buffer) buffer = toBytes(value);
            if (null!=buffer) valueLength = (short)buffer.length;
	    }
		return valueLength;
	}
    @Override
    public String getKey() {
        return name;
    }
	@Override
	public Object getValue() {
		return value(value,type);
	}
    @Override
    public Object setValue(Object value) {
        throw new UnsupportedOperationException();
    }

	@Override
	public String toString() {
		return "MapElement [type=" + type + ", index=" + index + ", nameLen=" + getNameLength() + ", valueLen=" + getValueLength() + ", name=" + name + ", value=" + value + "]";
	}

	@Override
	public byte[] toBytes() {
		return toBytes((ByteOrder)null);
	}

    @Override
    public byte[] toBytes(ByteOrder order) {
        byte [] bs = new byte[getLength()];
        byte complex = (byte)index;
        complex = (byte)(complex + (type << 5));
        bs[0] = complex;
        bs[1] = nameLength;
        putShort(bs,2,getValueLength(),order);
        return putBytes(bs,4,null==order? concat(name,null==buffer?value:buffer): concat(order,name,value));
    }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + nameLength;
		result = prime * result + index;
		result = prime * result + type;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + getValueLength();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		MapElement other = (MapElement) obj;
		if (name == null) {
			if (other.name != null) return false;
		}
		else if (!name.equals(other.name)) return false;
		if (nameLength != other.nameLength) return false;
		//if (paramIndex != other.paramIndex) return false;
		if (type != other.type) return false;
		if (value == null) {
			if (other.value != null) return false;
		}
		if (!value.equals(other.value)) {
		    
			return false;
		}
		if (valueLength != other.valueLength) return false;
		return true;
	}
}
