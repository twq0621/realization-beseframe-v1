package com.realization.framework.messaging.message.table;

import static com.realization.framework.messaging.message.Message.PARAM_TYPE_TABLE;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.realization.framework.communicate.nio.Bytes;
import com.realization.framework.messaging.message.Element;

/**
 * 表格对象
 * @author xianghao
 *
 */
public class TableParam extends Bytes implements Element {
	/**
	 * 行数（不包含表头）
	 */
	protected final short rows;
	
	/**
	 * 列数
	 */
	protected final short cols;

	/**
	 * 所有的表格元素（表头和单元格）
	 */
	protected final TableCell[] tableCells;
	protected final TableHead[] tableHeads;

    public TableParam(byte[] bs) {
        this(bs,null);
    }
    public TableParam(byte[] bs, ByteOrder order) {
        short complex = getShort(bs,0);
        cols = (short)((complex >> 12) & 0x000f);
        rows = (short)(complex & 0x0fff);
        tableHeads = new TableHead[cols];
        tableCells = new TableCell[rows*cols];
        byte[] content = getBytes(bs,2);//除去表格前面两个字节的剩余字节数组
        int headBytes_num = tableHeadFromBytes(content,order);//所有表头的总字节数
        dataCellFromBytes(getBytes(bs, headBytes_num+2, bs.length-headBytes_num-2),order);
    }
	
	public TableParam(TableHead[] tableHeads, TableCell... tableCells) {
		super();
        this.cols = (short)tableHeads.length;
		this.rows = (short)(tableCells.length/cols);
		this.tableHeads = tableHeads;
		this.tableCells = tableCells;
	}
    public TableParam(TableHead[] tableHeads, Object... data) {
        super();
        this.cols = (short)tableHeads.length;
        this.rows = (short)(data.length/cols);
        this.tableHeads = tableHeads;
        this.tableCells = new TableCell[data.length];
        for (int i=0; i<tableCells.length; i++) {
            tableCells[i] = new TableCell(tableHeads[i%cols], (short)(i/cols), data[i]);
        }
    }



    public TableHead[] getTableHeads() {
        return tableHeads;
    }

	public TableCell[] getTableCells() {
		return tableCells;
	}

	public short getRows() {
		return rows;
	}

	public short getCols() {
		return cols;
	}
	
    /**
     * 返回表格的字节数
     * @return
     */
    @Override
    public short getLength(){
        return (short)(getTableHeadLength()+getDataCellLength()+2);
    }
    @Override
    public byte getType() {
        return PARAM_TYPE_TABLE;
    }
    @Override
    public Object getValue() {
        @SuppressWarnings("unchecked")
        Map<String,Object>[] table = new Map[rows];
        TableHead[] tableHeads = getTableHeads();
        TableCell[] tableCells = getTableCells();
        for (int i=0; i<rows; i++) {
            for (int j = 0; j < cols; j++) {
                TableHead head = tableHeads[j];
                Map<String,Object> map = table[i];
                if (null==map) table[i] = map = new HashMap<String,Object>(); 
                map.put((String)head.getValue(), tableCells[(i*cols)+j].getValue());
            }
        }
        return table;
    }

	@Override
	public byte[] toBytes() {
		return toBytes((ByteOrder)null);
	}
    @Override
    public byte[] toBytes(ByteOrder order) {
        short tableLength = getLength();
        byte[] tableBytes = new byte[tableLength];
        putShort(tableBytes,0, (short)((cols<<12)+rows),order);//行数，列数
        return putBytes(tableBytes,2,concat(tableHeadToBytes(order),dataCellToBytes(order)));
    }

	/**
	 *从字节流中解析出所有的表格头
	 */
	private int tableHeadFromBytes(byte[] bs, ByteOrder order) {
		int position = 0;//当前表格的起始位置
		for (int i =0; i<cols; i++) {
			byte headNameLength = bs[position + 1];
			int tableHeadLength = headNameLength+2;//当前表头的字节数
			tableHeads[i] =  new TableHead(getBytes(bs,position,tableHeadLength),order);
			position += tableHeadLength;
		}
		return position;
	}
	
	private void dataCellFromBytes(byte[] bs, ByteOrder order) {
		int position = 0;//当前表格的位置
		int size = cols*rows;
		for (int m=0; m<size; m++) {
			short valueLength = getShort(bs,2+position,order);
			int dataLength = valueLength + 4; //当前单元格的字节数
			int i = m%cols;
			tableCells[m] = new TableCell(tableHeads[i],getBytes(bs,position,dataLength),order);//dc.fromBytes(dataBytes);
			position += dataLength;//移动下个单元格的起始位置
		}
	}
	
	/**
	 *所有表头的总字节数
	 */
	private short getTableHeadLength() {
		int length  = 0 ;
		for(int i = 0;i<cols;i++){
			length = tableHeads[i].getLength()+2+length;
		}
		return (short)length;
	}
	
	/**
	 * 所有单元格的总字节数
	 */
	private short getDataCellLength(){
		int length  = 0 ;
		int size = cols*rows;
		for (int i=0; i<size; i++) {
			length = ((TableCell)tableCells[i]).getLength()+4+length;
		}
		return (short)length;
	}
	
	/**
	 * 把表头转成byte数组
	 * @return
	 */
	private byte[] tableHeadToBytes(ByteOrder order){
		byte[] tableHeadBytes = new byte[getTableHeadLength()];
		int position = 0;//当前表头的起始位置
		for (int i =0; i<cols; i++) {
			byte[] bs = tableHeads[i].toBytes(order);
			putBytes(tableHeadBytes,position,bs);
			position += bs.length;
		}
		
		return tableHeadBytes;
	}
	
	/**
	 * 把单元格转成byte数组
	 * @param args
	 */
	private byte[] dataCellToBytes(ByteOrder order) {
		byte [] dataCellBytes = new byte[getDataCellLength()];
		int position = 0; //当前单元格的当前位置
		int size = cols*rows;
		for (int i=0; i<size; i++) {
			byte[] bs = tableCells[i].toBytes(order);
			putBytes(dataCellBytes,position,bs);
			position += bs.length;//移到下个单元格的起始位置
		}
		return dataCellBytes;
	}
	
	@Override
	public String toString() {
		return "TableParam [row_num=" + rows + ", column_num=" + cols + ", tableCells=" +arrayToString() + "]";
	}
	private String arrayToString(){
		String buf = "";
		for(int i = 0 ;i<tableCells.length;i++){
			buf =  buf +tableCells[i];
		}
		return buf;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cols;
		result = prime * result + rows;
		result = prime * result + Arrays.hashCode(tableCells);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		TableParam other = (TableParam) obj;
		if (cols != other.cols) return false;
		if (rows != other.rows) return false;
		if (!Arrays.equals(tableCells, other.tableCells)) return false;
		return true;
	}

}
