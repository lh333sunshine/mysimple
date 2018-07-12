package com.mysimple.controller.interceptor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.mysimple.common.util.DateTool;
import com.mysimple.common.util.FuncTool;
import com.mysimple.common.util.SysConstants;
import com.mysimple.common.util.SystemException;
import com.mysimple.service.ConfigBean;
import com.mysimple.service.TokenService;

@Component
public class RequestInterceptor implements HandlerInterceptor{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TokenService tokenService ;
	@Autowired
	private ConfigBean configBean;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//设置每次访问接口的唯一编码，用于错误查询.并记录访问时间
		request.setAttribute(SysConstants.REQUEST_ID,"RQ"+FuncTool.createUUID());
		request.setAttribute(SysConstants.REQUEST_TIME,DateTool.nowTimeStap());
		
		//是否校验TOKEN
		if(!configBean.getCheckToken()) {
			return true;
		}
		
		String uri = request.getRequestURI();
		//静态网页页面不校验token
		if(uri.indexOf(".") > -1) {
			return true;
		}
		
		//过滤掉不需校验TOKEN的url
		for(String item : configBean.getFilterTokenUrl()) {
			if(uri.startsWith(item)) {
				return true;
			}
		}
		
		//校验TOKEN
		if(!tokenService.checkToken()) {
			throw SystemException.init("token invalid");
		} 
		return true;
		
	}
	protected String getCurrentUid(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); 
		String uid = (String) request.getAttribute("");
		return uid;
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
 	}

}
