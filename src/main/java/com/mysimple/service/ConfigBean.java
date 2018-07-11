package com.mysimple.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "com.mysimple.configbean")
public class ConfigBean {

	private String host;
	/**
	 * 是否校验Token
	 */
	private boolean checkToken=false;
	
	/**
	 * 当checkToken为true时，设定不需要进行token校验的url。配置时逗号分割;
	 */
	private String[] filterTokenUrl=null;
	
	private String[] filterSuffix;
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setFilterTokenUrl(String str) {
		if(str!=null) {
			this.filterTokenUrl = str.split(",");
		}else {
			filterTokenUrl = new String[0];
		}
	}
	
	public void setFilterSuffix(String str) {
		if(str!=null) {
			this.filterSuffix = str.split(",");
		}else {
			filterSuffix = new String[0];
		}
	}
	
	public void setCheckToken(String str) {
		if(str!=null && "true".equals(str)) {
			this.checkToken = true;
		}
	}

	public boolean getCheckToken() {
		return checkToken;
	}

	public String[] getFilterTokenUrl() {
		return filterTokenUrl;
	}

	public String[] getFilterSuffix() {
		return filterSuffix;
	}
}
