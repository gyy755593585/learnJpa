package com.wuji.learn.jpa.model;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 *
 * @author Administrator
 *
 * @param <T>
 */
public class Pager<T> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7296229731252364352L;

	/**
	 * 分页的大小
	 */
	private int size;

	/**
	 * 分页的起始页
	 */
	private int offset;

	/**
	 * 总记录数
	 */
	private long total;

	/**
	 * 分页的数据
	 */
	private List<T> rows;

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getOffset() {
		return this.offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public long getTotal() {
		return this.total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return this.rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
