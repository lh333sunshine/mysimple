package com.mysimple.dto.systemtool.request;

import java.util.Calendar;

import com.mysimple.dto.pub.PublicRequestPagerDTO;

public class QueryRequestLogDTO extends PublicRequestPagerDTO{

	private String reqId;
	
	private String startTime;
	
	private String endTime;
	
	private Integer returnCode;

	private String api;
	
	private Integer runTime;
	
	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		if(endTime != null){
			endTime = endTime.trim();
		}
		if(endTime !=null && endTime.length() == 16 ){
			endTime = endTime + ":00";
		}
		if(endTime != null && endTime.length() == 14){
			endTime = Calendar.getInstance().get(Calendar.YEAR)+"-"+ endTime ;
		}
		if(endTime != null && endTime.length() == 11){
			endTime = Calendar.getInstance().get(Calendar.YEAR)+"-"+ endTime +":00";
		}
		this.endTime = endTime;
	}

	public Integer getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public Integer getRunTime() {
		return runTime;
	}

	public void setRunTime(Integer runTime) {
		this.runTime = runTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		if(startTime != null){
			startTime = startTime.trim();
		}
		if(startTime !=null && startTime.length() == 16 ){
			startTime = startTime + ":00";
		}
		if(startTime != null && startTime.length() == 14){
			startTime = Calendar.getInstance().get(Calendar.YEAR)+"-"+ startTime ;
		}
		if(startTime != null && startTime.length() == 11){
			startTime = Calendar.getInstance().get(Calendar.YEAR)+"-"+ startTime +":00";
		}
		
		this.startTime = startTime;
	}

	
	
}
