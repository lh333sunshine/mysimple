package com.mysimple.dto.pub;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysimple.common.autodoc.DtoFiledDescribe;
import com.mysimple.common.autodoc.DtoFiledDocHide;
import com.mysimple.common.util.Page;

/**
 * 分页请求
 */
public class PublicRequestPagerDTO  {
	
	@DtoFiledDescribe("查询第几页，默认第一页")
	private Integer page = 1;
	
	@DtoFiledDescribe("每页显示多少条数据，默认返回所有,默认最大500")
	private Integer pageSize = 500;
	
	@DtoFiledDescribe("排序字段")
	private String sortby;
	
	@DtoFiledDocHide
	@JsonIgnore
	private Page pageObj;
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		if(page != null){
			this.page = page;
		}
	}
	 
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if(pageSize != null){
			this.pageSize = pageSize;
		}
		
	}
	
	public String getSortby() {
		return sortby;
	}

	public void setSortby(String sortby) {
		this.sortby = sortby;
	}

	public Page getPageObj(){
		if(pageObj == null){
			pageObj = new Page(this.getPage(), this.getPageSize());
			pageObj.setSortby(this.sortby);
		}
		return this.pageObj;
	}
	
}
