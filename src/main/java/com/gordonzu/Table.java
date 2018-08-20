package com.gordonzu;

import java.util.Hashtable;
import java.util.List;

public class Table<RowHeaderType, ColumnHeaderType, ValueType> {

	private List<RowHeaderType> rowHeaders;
	private List<ColumnHeaderType> columnHeaders;
	private Hashtable<RowHeaderType, Hashtable<ColumnHeaderType, ValueType>> rows;

    public Table() {}

	public Table(List<RowHeaderType> rowHeaders, List<ColumnHeaderType> columnHeaders) {

		this.rowHeaders = rowHeaders;
		this.columnHeaders = columnHeaders;
		this.rows = new Hashtable<RowHeaderType, Hashtable<ColumnHeaderType, ValueType>>();

		for (RowHeaderType rowHeader : rowHeaders) {
			rows.put(rowHeader, new Hashtable<ColumnHeaderType, ValueType>());
		}
	}

	public void set(RowHeaderType r, ColumnHeaderType c, ValueType v) {
		rows.get(r).put(c, v);
	}

	public ValueType get(RowHeaderType r, ColumnHeaderType c) {
		Hashtable<ColumnHeaderType, ValueType> rowValues = rows.get(r);
		return rowValues == null ? null : rowValues.get(c);

	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (RowHeaderType r : rowHeaders) {
			for (ColumnHeaderType c : columnHeaders) {
				buf.append(get(r, c).toString());
				buf.append(" ");
			}
			buf.append("\n");
		}
		return buf.toString();
	}
}


















