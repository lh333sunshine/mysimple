package com.mysimple.controller.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysimple.common.util.FuncTool;
import com.mysimple.common.util.SysConstants;
import com.mysimple.common.util.SystemException;
import com.mysimple.dto.pub.PublicResponseDTO;

@ControllerAdvice
public class SystemExceptionHandler {
	@Autowired
	private HttpServletRequest request;
	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 输入参数不合法异常
	 */
	@ExceptionHandler(BindException.class)
	@ResponseBody
	public PublicResponseDTO processValidationError(BindException ex) {
		StringBuilder sb = new StringBuilder(256);
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			sb.append(fieldError.getField());
			sb.append(":");
			sb.append(fieldError.getDefaultMessage());
			sb.append(", ");
		}
		return PublicResponseDTO.fail(SysConstants.API_CODE_PARAMS_INVALID,sb.toString());
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public PublicResponseDTO processValidationError(MethodArgumentNotValidException ex) {
		StringBuilder sb = new StringBuilder(256);
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			sb.append(fieldError.getField());
			sb.append(":");
			sb.append(fieldError.getDefaultMessage());
			sb.append(", ");
		}
		return PublicResponseDTO.fail(SysConstants.API_CODE_PARAMS_INVALID,sb.toString());
	}
	
	/**
	 * 系统自定义异常
	 */
	@ExceptionHandler(SystemException.class)
	@ResponseBody
	public PublicResponseDTO processSystemError(SystemException ex) {
		return PublicResponseDTO.fail(ex.getErrorCode(), ex.getMessage());
	}

	/**
	 * 数据库SQL异常
	 */
	@ExceptionHandler(BadSqlGrammarException.class)
	@ResponseBody
	public PublicResponseDTO processSqlError(BadSqlGrammarException ex) {
		log.error(String.valueOf(request.getAttribute(SysConstants.REQUEST_ID)), ex);
		request.setAttribute(SysConstants.ERROR_TRACE, FuncTool.getErrorInfoFromException(ex));
		return PublicResponseDTO.fail(SysConstants.API_CODE_SQL_GRAMMAR, "数据库访问异常");
	}

	/**
	 * 未知异常
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public PublicResponseDTO processError(Exception ex) {
		log.error(String.valueOf(request.getAttribute(SysConstants.REQUEST_ID)), ex);
		request.setAttribute(SysConstants.ERROR_TRACE, FuncTool.getErrorInfoFromException(ex));
		String msg = ex.getMessage();
		if (msg == null) {
			msg = "未知异常";
		}
		return PublicResponseDTO.fail(SysConstants.API_CODE_ERROR, msg);
	}

}
