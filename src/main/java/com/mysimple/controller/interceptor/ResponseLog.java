package com.mysimple.controller.interceptor;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.mysimple.common.util.DateTool;
import com.mysimple.common.util.FuncTool;
import com.mysimple.common.util.SysConstants;
import com.mysimple.dao.SysLogsRequestWithBLOBs;
import com.mysimple.dao.mapper.SysLogsRequestMapper;
import com.mysimple.dto.pub.PublicResponseDTO;

/**
 * 请求返回之前，记录请求日志到数据库
 *
 */
@ControllerAdvice
public class ResponseLog  implements ResponseBodyAdvice<Object>{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	 
	@Autowired
	private SysLogsRequestMapper mapper;
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return PublicResponseDTO.class.isAssignableFrom(returnType.getMethod().getReturnType()); 
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response)  {
		HttpServletRequest httpRequest = ((ServletServerHttpRequest)request).getServletRequest();
		PublicResponseDTO dpr = (PublicResponseDTO) body;
		try {
			dpr.setReqId(String.valueOf(httpRequest.getAttribute(SysConstants.REQUEST_ID)));
			 
			//此处将请求和输出结果存入数据库，可使用缓存异步存储
			SysLogsRequestWithBLOBs slr = new SysLogsRequestWithBLOBs();
			slr.setReqId(dpr.getReqId());
			slr.setApi(httpRequest.getRequestURI());
			slr.setIp(FuncTool.getIp(httpRequest));
			slr.setReturnCode(dpr.getCode());
			slr.setUserId(String.valueOf(httpRequest.getAttribute(SysConstants.USER_ID)));
			int runtime = (int)(DateTool.nowTimeStap() - Long.parseLong(httpRequest.getAttribute(SysConstants.REQUEST_TIME).toString()));
			slr.setRunTime(runtime);
			slr.setCreateTime(new Date());
			
			String requestParams = showParams(httpRequest).toString();
			//太长的请求或输出结果，放弃存储
			int requestLen = (requestParams.length()/1024) ;
			slr.setRequestLen(requestLen);
			if(requestLen < 5){
				slr.setRequestParams(requestParams);
			}
			String responseParams = dpr.toJson();
			int responseLen = (responseParams.length()/1024);
			slr.setResponseLen(responseLen);
			if(responseLen < 5){
				slr.setResponseContent(responseParams);
			}
			//错误信息存储
			if(httpRequest.getAttribute(SysConstants.ERROR_TRACE) != null){
				slr.setErrorTrace(httpRequest.getAttribute(SysConstants.ERROR_TRACE).toString());
			}
			mapper.insertSelective(slr);
		} catch (Exception e) {
			log.error(String.valueOf(httpRequest.getAttribute(SysConstants.REQUEST_ID)), e);
		}
		return dpr;
		
	}
	
	private Map<String,String> showParams(HttpServletRequest request) {
		Map<String,String> jo = new HashMap<>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String str = request.getParameter(paramName);
            jo.put(paramName, str);
        }
        return jo;
    } 
	
}

