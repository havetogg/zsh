package org.jumutang.zsh.model;

/**
 * 分页查询实体类
 * @author Administrator
 *
 */
public class PageMode {
	
	private Integer pageIndex;
	private Integer pageSize;
	/**
	 * 当前页
	 */
	private Integer pageNow;
	private Integer total;
	private Object list;
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Object getList() {
		return list;
	}
	public void setList(Object list) {
		this.list = list;
	}
	public Integer getPageNow() {
		return pageNow;
	}
	public void setPageNow(Integer pageNow) {
		this.pageNow = pageNow;
	}
	
	
}
