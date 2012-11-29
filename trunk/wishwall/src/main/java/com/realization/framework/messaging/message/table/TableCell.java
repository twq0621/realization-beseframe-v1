package com.realization.framework.messaging.message.table;

import static com.realization.framework.messaging.message.utils.MessageUtils.value;
import static com.realization.framework.messaging.message.utils.MessageUtils.wrap;

import java.nio.ByteOrder;

import com.realization.framework.communicate.nio.Bytes;
import com.realization.framework.messaging.message.Element;


public class TableCell extends Bytes implements Element {
	private final TableHead tableHead;
	private final short rowIndex;
	private final short colIndex;
    private final Object value;
	
	/**
	 * 值域的长度
	 */
    private short length;
    private byte[] buffer;
	
	public TableCell(TableHead tableHead, short rowIndex, Object value) {
		this.tableHead = tableHead;
		this.rowIndex = rowIndex;
		this.colIndex = tableHead.index;
		this.value = wrap(value);
	}
	public TableCell(TableHead tableHead, byte[] bs) {
	    this(tableHead,bs,null);
	}
    public TableCell(TableHead tableHead, byte[] bs, ByteOrder order) {
        this.tableHead = tableHead;
        short complex = getShort(bs,0,order);
        colIndex = (short)((complex >> 12) & 0x000f);
        rowIndex = (short)(complex & 0x0fff);
        length = getShort(bs,2,order);
        value = value(tableHead.getType(), getBytes(bs,4,length), order);
    }



	public short getRowIndex() {
		return rowIndex;
	}

	public short getColIndex() {
		return colIndex;
	}
	@Override
	public short getLength() {
        if (length==(byte)0 && null!=value) {
            if (null==buffer) buffer = toBytes(value);
            if (null!=buffer) length = (short)buffer.length;
        }
		return length;
	}
    @Override
    public byte getType() {
        return tableHead.getType();
    }
	@Override
	public Object getValue() {
		return value(value,getType());
	}
	
	@Override
	public String toString() {
		return "DataCell [row=" + rowIndex + ", col=" + colIndex + ", name="+tableHead.headName+", valueType="+tableHead.type+", valueLen=" + length + ", value=" + value + "]";
	}

	@Override
	public byte[] toBytes() {
		return toBytes((ByteOrder)null);//cellBytes;
	}
    @Override
    public byte[] toBytes(ByteOrder order) {
        byte[] cellBytes = new byte[4+getLength()];
        short complex = rowIndex;
        putShort(cellBytes,0,(short)((colIndex<<12)+complex),order);
        putShort(cellBytes,2,length,order);
        if (null!=order || null==buffer) buffer = toBytes(value,length,order);
        return putBytes(cellBytes,4,buffer);
    }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + colIndex;
		result = prime * result + rowIndex;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + length;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (null == obj) return false;
		if (getClass() != obj.getClass()) return false;
		TableCell other = (TableCell) obj;
		if (colIndex != other.colIndex) return false;
		if (rowIndex != other.rowIndex) return false;
		if (value == null) {
			if (other.value != null) return false;
		}
		else if (!value.equals(other.value)) return false;
		if (length != other.length) return false;
		return true;
	}
}
