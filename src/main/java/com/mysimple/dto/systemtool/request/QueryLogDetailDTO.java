package com.mysimple.dto.systemtool.request;

import javax.validation.constraints.NotBlank;

public class QueryLogDetailDTO {

	@NotBlank
	private String reqId;

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
}
