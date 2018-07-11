package com.mysimple.dto.pub;

import java.io.Serializable;

import com.mysimple.common.autodoc.DemoData;
import com.mysimple.common.autodoc.DtoFiledDescribe;
import com.mysimple.common.util.FuncTool;
import com.mysimple.common.util.SysConstants;

public class PublicResponseDTO implements Serializable{

	private static final long serialVersionUID = -3783199459021802877L;

	@DtoFiledDescribe(value="错误描述",demo ="操作成功")
	private String msg = "";
	
	//错误码
	@DtoFiledDescribe(value="错误代码",demo="0")
	private Integer code = SysConstants.API_CODE_SUCCESS;
	
	@DtoFiledDescribe(value="请求序列，每次请求会产生唯一编号,接口错误时请记录该编号",demo="RQ"+DemoData.UUID)
	private String reqId=null;
	
	public String getMsg() {
		return msg;
	}
	public PublicResponseDTO setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	public PublicResponseDTO addMsg(String msg){
		this.msg += msg;
		return this;
	}
	
	public Integer getCode() {
		return code;
	}
	public PublicResponseDTO setCode(Integer code) {
		this.code = code;
		return this;
	}
	
	public static PublicResponseDTO init(){
		return new PublicResponseDTO();
	}
	public static PublicResponseDTO init(String message){
		PublicResponseDTO dr = new PublicResponseDTO();
		dr.setMsg(message);
		return dr;
	}
	
	public static PublicResponseDTO fail(String message){
	    return fail(SysConstants.API_CODE_SERVER_ERROR, message);
	}
	
	public static PublicResponseDTO fail(int code, String message){	
		PublicResponseDTO dr = new PublicResponseDTO();
		dr.setCode(code);
		dr.setMsg(message);
		return dr;
	}
	
	public void setFail(String message){
		this.setCode(SysConstants.API_CODE_SERVER_ERROR);
		this.setMsg(message);
	}
	public String toJson(){
		return FuncTool.jsonToString(this);
	}
	
	public Object initDemoData(Class genClass){
		FuncTool.createDemoData(this.getClass(), genClass ,this,false); 
		return this;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	
	
}
