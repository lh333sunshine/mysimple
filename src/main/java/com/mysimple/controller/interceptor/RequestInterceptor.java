package com.mysimple.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mysimple.common.util.DateTool;
import com.mysimple.common.util.FuncTool;
import com.mysimple.common.util.SysConstants;
import com.mysimple.common.util.SystemException;
import com.mysimple.dto.pub.PublicResponseDTO;
import com.mysimple.service.ConfigBean;
import com.mysimple.service.TokenService;

@Component
public class RequestInterceptor implements HandlerInterceptor{
	 
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
		
		if(!configBean.getCheckToken()) {
			return true;
		}
		
		String uri = request.getRequestURI();
		//过滤掉后缀
		for(String item : configBean.getFilterSuffix()) {
			if(uri.endsWith(item)) {
				return true;
			}
		}
		
		//过滤掉不需校验的url
//		for(String item : configBean.getFilterTokenUrl()) {
//			if(uri.startsWith(item)) {
//				return true;
//			}
//		}
//		if(!tokenService.checkToken()) {
//			throw SystemException.init("token invalid");
//		} 
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
