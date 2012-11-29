package com.realization.framework.messaging.message;

import static com.realization.framework.messaging.message.Message.PARAM_TYPE_STRING;

import java.util.Arrays;

import com.realization.framework.messaging.message.utils.MessageUtils;


/**
 * 用来封装一个table类型的参数，面向用户
 * 表头名为String类型，表头对应的列的数值类型保持一致。
 * 设置值的顺序是从左往右，从上到下
 * 不支持空元素
 * @author xianghao
 */
public class Table {

	private Object[][] cells ;
	
	/** 行数,包括表头行 */
	private final int rows;
	private final int cols;

	public Table(int rows, int cols){
		this.rows = rows;
		this.cols = cols;
		cells = new Object[rows][cols];
	}
    public Table(Object[] data, String... colKeys) {
        this.cols = colKeys.length;
        this.rows = data.length/cols+1;
        this.cells = new Object[rows][cols];
        int i=0, j=0;
        for (String s: colKeys) {
            cells[i][j++] = s;
        }
        for (Object v: data) {
            if (j>=cols) {
                j = 0;
                i++;
            }
            cells[i][j++] = v;
        }
    }
	
	public void set(int rowIndex, int colIndex, Object value) {
		cells[rowIndex][colIndex] = value;
	}
	public Object get(int rowIndex, int colIndex) {
		return cells[rowIndex][colIndex];
	}
	public Object[][] getCells() {
		return cells;
	}
	public void setCells(Object[][] cells) {
		this.cells = cells;
	}
	public int getRows() {
		return rows;
	}
	public int getCols() {
		return cols;
	}
	
	public byte colType(int colIndex) {
	    for (int i=1; i<cells.length; i++) {
	        Object[] cols = cells[i];
	        Object value = cols[colIndex];
	        if (null!=value) {
	            return MessageUtils.type(value);
	        }
	    }
	    return PARAM_TYPE_STRING;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cells);
		result = prime * result + cols;
		result = prime * result + rows;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Table other = (Table) obj;
		
		if (cols != other.cols) return false;
		if (rows != other.rows) return false;
		
		Object[] arr1 = new Object[rows*cols];
		Object[] arr2 = new Object[rows*cols];
		
		for(int i = 0 ;i<rows;i++){
			for(int j =0 ;j<cols;j++){
				arr1[i*cols+j] = cells[i][j];
				arr2[i*cols+j] = other.getCells()[i][j];
			}
		}
		if (!Arrays.equals(arr1, arr2)) return false;
		return true;
	}

	@Override
	public String toString(){
		String buf = "Table[";
		for (int i=0; i<rows; i++){
			for (int j=0; j<cols; j++) {
				buf = buf + "row:"+i+",col:"+j+". "+cells[i][j] +"\t";
			}
			buf = buf +"\n";
		}
		return buf;
	}
	public static void main(String[] args) {
		Table t =new Table(2, 2);
		t.set(0, 0, 1);
		t.set(0, 1, 2);
		t.set(1, 0, 43);
		t.set(1, 1, 22);
		Table t2 =new Table(2, 2);
		t2.set(0, 0, 1);
		t2.set(0, 1, 2);
		t2.set(1, 0, 43);
		t2.set(1, 1, 22);
		System.out.println(t);
		System.out.println(t2);
		System.out.println(t.equals(t2));
	}
}
