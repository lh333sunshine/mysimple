package com.mysimple.dto.user.request;


import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mysimple.common.autodoc.DtoFiledDescribe;
import com.mysimple.dto.pub.PublicRequestPagerDTO;

public class QueryUsersDTO extends PublicRequestPagerDTO{

	@NotNull
	private String token;

	@DtoFiledDescribe("关键词")
	private String keywords;

	@DtoFiledDescribe("注册日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date registDate;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
}
