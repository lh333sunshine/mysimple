package com.mysimple.dto.user.request;

import javax.validation.constraints.NotBlank;

import com.mysimple.common.autodoc.DtoFiledDescribe;

public class LoginReqDTO {
	@NotBlank
	@DtoFiledDescribe("登录帐号")
	private String account;
	
	@NotBlank
	@DtoFiledDescribe("密码")
	private String password;
	
	
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
}
