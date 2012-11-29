package com.realization.framework.messaging.message.array;

import static com.realization.framework.messaging.message.utils.MessageUtils.type;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Collection;

import com.realization.framework.communicate.nio.Bytes;
import com.realization.framework.messaging.message.Element;


/**
 * 
 * @author xianghao
 *
 */
public class ArrayParam extends Bytes implements Element {
	
	protected final byte type;
	protected final short num;
	protected final ArrayElement[] elements;

    public ArrayParam(Object... array) {
        array = array(array);
        this.type = type(array);
        this.num = (short)array.length;
        this.elements = new ArrayElement[num];
        for (int i=0; i<num; i++) {
            elements[i] = new ArrayElement(this,array[i]);
        }
    }
	public ArrayParam(byte paramType, Object... array) {
        array = array(array);
		this.type = paramType;
		this.num = (short)array.length;
		this.elements = new ArrayElement[num];
		for (int i=0; i<num; i++) {
		    elements[i] = new ArrayElement(this,array[i]);
		}
	}
    public ArrayParam(byte[] bs, ByteOrder order) {
        short complex = getShort(bs,0,order);
        type = (byte)((complex >> 13) & 0x0007);
        num = (short)(complex & 0x1fff);
        elements = new ArrayElement[num];
        int position = 2;
        for (int i=0; i<num; i++) {
            int lastPosition = position+2+getShort(bs,position,order); 
            elements[i] = new ArrayElement(this,getBytes(bs,position,lastPosition-position),order);
            position = lastPosition; 
        }
    }
    public ArrayParam(byte[] bs) {
        this(bs,null);
    }
    public short getNum() {
        return num;
    }
    public ArrayElement[] getElements() {
        return elements;
    }
    @Override
	public byte getType() {
		return type;
	}
    @Override
    public short getLength(){
        short length = 0;
        for(int i =0 ;i<num;i++){
            length = (short)(elements[i].getLength()+2 +length);
        }
        length = (short)(length +2);
        return  length;
    }
    @Override
    public Object getValue() {
        Object[] result = new Object[elements.length];
        for (int i=0; i<elements.length; i++) {
            result[i] = elements[i].getValue();
        }
        return result;
    }
	@Override
	public byte[] toBytes() {
	    return toBytes((ByteOrder)null);
	}
    @Override
    public byte[] toBytes(ByteOrder order) {
        byte[] arrayBytes = new byte[getLength()];
        putShort(arrayBytes,0,(short)((type<<13)+num),order);
        int position = 2;
        for (int i=0; i<num; i++) {
            byte[] ebs = elements[i].toBytes(order);
            putBytes(arrayBytes,position,ebs);
            position += ebs.length;
        }
        return arrayBytes;
    }

	@Override
	public String toString() {
		return "ArrayParam [paramType=" + type + ", num=" + num + ", elements=" + byteArrayToString() + "]";
	}
	
	private String byteArrayToString(){
		String buf = "";
		for (int i=0; i<elements.length; i++) {
			buf = buf+elements[i];
		}
		return buf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(elements);
		result = prime * result + num;
		result = prime * result + type;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ArrayParam other = (ArrayParam) obj;
		if (!Arrays.equals(elements, other.elements)) return false;
		if (num != other.num) return false;
		if (type != other.type) return false;
		return true;
	}

	private static Object[] array(Object[] array) {
	    if (null!=array && array.length==1 && array[0] instanceof Collection<?>) {
	        array = ((Collection<?>)array[0]).toArray();
	    }
	    return array;
	}

}
