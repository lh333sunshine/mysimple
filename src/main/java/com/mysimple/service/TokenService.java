package com.mysimple.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mysimple.common.util.EnDecryptUtil;
import com.mysimple.common.util.SysConstants;


/**
 * Token校验服务,简单示例
 * 
 */
@Service
public class TokenService {

	private HashMap<String, String> cacheToken = new HashMap<>();
	
	public String createToken(String userId) {
		String token = EnDecryptUtil.SHA1(userId);
		cacheToken.put(token,userId);
		return token;
	}
	
	public boolean checkToken() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); 
		
		String token = request.getParameter(SysConstants.TOKEN);
		String userId = cacheToken.get(token);
		if(userId == null) {
			return false;
		}
		request.setAttribute(SysConstants.USER_ID, userId);
		return true;
	}
	
	public String currentUID() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); 
		return (String) request.getAttribute(SysConstants.USER_ID);
	}
}
