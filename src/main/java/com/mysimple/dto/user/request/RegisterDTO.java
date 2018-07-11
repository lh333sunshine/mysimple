package com.mysimple.dto.user.request;

import javax.validation.constraints.NotBlank;

import com.mysimple.common.autodoc.DtoFiledDescribe;

public class RegisterDTO {
	
	@NotBlank
	@DtoFiledDescribe(value="登录帐号",demo ="aaa")
	private String account;
	
	@NotBlank
	@DtoFiledDescribe(value="登录密码",demo ="123456")
	private String password;
	
	@NotBlank
	@DtoFiledDescribe(value="昵称",demo ="AAA")
	private String nickName;


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
