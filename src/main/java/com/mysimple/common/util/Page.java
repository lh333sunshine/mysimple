package com.mysimple.common.util;

public class Page {
	
	//当前页
	private Integer pageNum = 1;
	
	//分页索引
	private Integer offset = 0;
	
	//每页条数
	private Integer pageSize = 10;

	//总条数 
	private Integer totalCount = 0;
	
	private String sortby;
	
	public Page(int pageNum, int pageSize){
		this.pageNum = pageNum<1?1:pageNum;
		this.pageSize = pageSize<0?10:pageSize;
		this.offset = (this.pageNum-1)*this.pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public Integer getOffset() {
		return offset;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getSortby() {
		return sortby;
	}

	public void setSortby(String sortby) {
		this.sortby = sortby;
	}
	
	public Page() {
		
	}
}
