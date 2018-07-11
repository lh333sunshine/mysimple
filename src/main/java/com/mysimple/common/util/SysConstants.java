package com.mysimple.common.util;

/**
 * 接口通用定义
 */
public class SysConstants {
	
	/*request.attribute中通用名称*/
	public static final String TOKEN = "_token";
    public static final String REQUEST_ID = "_reqid";
    public static final String ERROR_TRACE = "_errorTrace";
    public static final String REQUEST_TIME = "_reqtime";
    public static final String USER_ID = "_currentUID";
    
    /*系统返回的错误码定义*/
    public static final Integer API_CODE_SUCCESS = 0;
    public static final Integer API_CODE_ERROR = 500; //程序未捕获到的异常
    public static final Integer API_CODE_SERVER_ERROR = 501; //程序已捕获异常，手动抛出
    
    public static final Integer API_CODE_PARAMS_INVALID = 551;
    public static final Integer API_CODE_SQL_GRAMMAR = 552;
    
}
