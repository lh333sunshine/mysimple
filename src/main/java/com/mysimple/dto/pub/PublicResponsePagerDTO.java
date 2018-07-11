package com.mysimple.dto.pub;

import java.util.List;

import com.mysimple.common.autodoc.DtoFiledDescribe;
import com.mysimple.common.util.Page;

/**
 * 返回分页的DTO
 */
public class PublicResponsePagerDTO <T> extends PublicResponseDTO {

	private List<T> data = null;
	
	@DtoFiledDescribe(value="总条数",demo="1")
	private Integer records = 0;

	@DtoFiledDescribe(value="当前页",demo="1")
	private Integer pageNum = 1;

	@DtoFiledDescribe(value="每页条数",demo="10")
	private Integer pageSize = 0;
	
	public Integer getRecords() {
		return records;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	public PublicResponsePagerDTO(List<T> data,Page pageObj) {
		this.data = data;
		this.setPageObj(pageObj);
	}
	 
	public PublicResponsePagerDTO() {
		
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	public void setPageObj(Page page){
		if(page != null){
			this.setRecords(page.getTotalCount());
			this.setPageNum(page.getPageNum());
			this.setPageSize(page.getPageSize());
		}
	}
}
