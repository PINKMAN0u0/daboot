package com.pinkman.dtboot.utils;

import java.util.List;

public class DataGridResult {

	private Integer total;
	private Object rows;
	public DataGridResult(List<?> rows, Integer total) {
		this.total = total;
		this.rows = rows;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Object getRows() {
		return rows;
	}
	public void setRows(Object rows) {
		this.rows = rows;
	}
}
