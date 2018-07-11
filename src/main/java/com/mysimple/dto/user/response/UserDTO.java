package com.mysimple.dto.user.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mysimple.common.autodoc.DtoFiledDescribe;

public class UserDTO {
	private Integer id;
	
	@DtoFiledDescribe("账号")
	private String account;
	
	@DtoFiledDescribe("昵称")
	private String nickName;
	
	@DtoFiledDescribe("注册日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date registDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Date getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	public void setCreateTime(Date date) {
		this.registDate = date;
	}
}	
