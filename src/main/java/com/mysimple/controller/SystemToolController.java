package com.mysimple.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysimple.common.util.DateTool;
import com.mysimple.common.util.FuncTool;
import com.mysimple.common.util.Page;
import com.mysimple.dao.SysLogsRequest;
import com.mysimple.dao.SysLogsRequestWithBLOBs;
import com.mysimple.dto.pub.PublicResponseObjDTO;
import com.mysimple.dto.pub.PublicResponsePagerDTO;
import com.mysimple.dto.systemtool.request.QueryLogDetailDTO;
import com.mysimple.dto.systemtool.request.QueryRequestLogDTO;
import com.mysimple.dto.systemtool.response.RequestLogInfoDTO;
import com.mysimple.service.RequestLogService;

@Controller
@RequestMapping("/systemtool")
public class SystemToolController {
	 
	@Autowired
	private RequestLogService requestLogService;
	
	@RequestMapping("/queryRequestLog")
	@ResponseBody
	public PublicResponsePagerDTO<RequestLogInfoDTO> queryRequestLog(@RequestBody @Valid QueryRequestLogDTO dtoRequest) throws Exception {
		Page p = dtoRequest.getPageObj();
		SysLogsRequest log = FuncTool.copyPropertiesClass(dtoRequest, SysLogsRequest.class);
		List<SysLogsRequest> list = requestLogService.query( p,log,DateTool.parseDateTime(dtoRequest.getStartTime()), DateTool.parseDateTime(dtoRequest.getEndTime()));
		List<RequestLogInfoDTO> listDto = FuncTool.copyPropertiesList(list, RequestLogInfoDTO.class);
		return new PublicResponsePagerDTO(listDto,p);
	}
	 
	@RequestMapping("/queryLogDetail")
	@ResponseBody
	public PublicResponseObjDTO<SysLogsRequestWithBLOBs> queryLogDetail(@RequestBody @Valid QueryLogDetailDTO dtoRequest) throws Exception {
		
		return new PublicResponseObjDTO(requestLogService.queryFullInfo(dtoRequest.getReqId()));
	}
	
}
