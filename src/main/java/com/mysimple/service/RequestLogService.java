package com.mysimple.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mysimple.common.util.Page;
import com.mysimple.dao.SysLogsRequest;
import com.mysimple.dao.SysLogsRequestExample;
import com.mysimple.dao.SysLogsRequestWithBLOBs;
import com.mysimple.dao.SysLogsRequestExample.Criteria;
import com.mysimple.dao.mapper.SysLogsRequestMapper;

@Service
public class RequestLogService {

	@Autowired
	private CommonDbService dbService;
	
	public List<SysLogsRequest> query(Page p ,SysLogsRequest log, Date startTime, Date endTime) throws Exception{
		SysLogsRequestExample example = new SysLogsRequestExample();
		Criteria c = example.createCriteria();
		if(!StringUtils.isEmpty(log.getReqId())){
			if(log.getReqId().length() < 30){
				c.andReqIdLike("%"+log.getReqId()+"%");
			}else{
				c.andReqIdEqualTo(log.getReqId());
			}
		}
		if(!StringUtils.isEmpty(log.getReturnCode())  ){
			if(log.getReturnCode() == -1){
				c.andReturnCodeNotEqualTo(0);
			}else{
				c.andReturnCodeEqualTo(log.getReturnCode());
			}
		}
		if(!StringUtils.isEmpty(startTime)){
			c.andCreateTimeGreaterThanOrEqualTo(startTime);
		}
		if(!StringUtils.isEmpty(endTime)){
			c.andCreateTimeLessThan(endTime);
		}
		if(!StringUtils.isEmpty(log.getApi())){
			if(log.getApi().startsWith("api") || log.getApi().startsWith("/api")){
				c.andApiEqualTo(log.getApi());
			}else{
				c.andApiLike("%"+log.getApi()+"%");
			}
		}
		if(log.getRunTime() != null){
			c.andRunTimeGreaterThan(log.getRunTime());
		}
		
		return dbService.selectByExample(example, SysLogsRequest.class, p);
	}
	
	
	public SysLogsRequestWithBLOBs queryFullInfo(String reqId) throws Exception{
		return dbService.getById(reqId, SysLogsRequestWithBLOBs.class);
	}
}
