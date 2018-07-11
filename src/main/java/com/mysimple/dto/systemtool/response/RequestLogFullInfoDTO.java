package com.mysimple.dto.systemtool.response;

public class RequestLogFullInfoDTO {

	 
    private String requestParams;

    
    private String responseContent;

    
    private String errorTrace;


    private String userId;
    
	public String getRequestParams() {
		return requestParams;
	}


	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}


	public String getResponseContent() {
		return responseContent;
	}


	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}


	public String getErrorTrace() {
		return errorTrace;
	}


	public void setErrorTrace(String errorTrace) {
		this.errorTrace = errorTrace;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}
    
    
}
