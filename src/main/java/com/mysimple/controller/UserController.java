package com.mysimple.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysimple.common.autodoc.ApiDescribe;
import com.mysimple.common.util.DateTool;
import com.mysimple.common.util.FuncTool;
import com.mysimple.common.util.Page;
import com.mysimple.common.util.SystemException;
import com.mysimple.dao.UserTest;
import com.mysimple.dao.UserTestExample;
import com.mysimple.dto.pub.PublicResponseDTO;
import com.mysimple.dto.pub.PublicResponseObjDTO;
import com.mysimple.dto.pub.PublicResponsePagerDTO;
import com.mysimple.dto.user.request.LoginReqDTO;
import com.mysimple.dto.user.request.QueryUsersDTO;
import com.mysimple.dto.user.request.RegisterDTO;
import com.mysimple.dto.user.response.LoginRespDTO;
import com.mysimple.dto.user.response.UserDTO;
import com.mysimple.service.CommonDbService;
import com.mysimple.service.TokenService;

@RestController
@RequestMapping("/user")
@ApiDescribe("用户接口")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommonDbService dbService;
	
	@Autowired
	private TokenService tokenService;
	
	@ApiDescribe("用户注册")
    @RequestMapping("/register")
    public PublicResponseDTO register(@RequestBody @Valid RegisterDTO requestDTO) throws Exception {
    	UserTest ut = FuncTool.copyPropertiesClass(requestDTO, UserTest.class);
    	dbService.insert(ut);
    	return PublicResponseDTO.init();
    }
    
	@ApiDescribe("用户登录")
    @RequestMapping("/login")
    public PublicResponseObjDTO<LoginRespDTO> login(@RequestBody @Valid LoginReqDTO requestDTO) throws Exception {
    	UserTestExample example = new UserTestExample();
    	example.createCriteria().andAccountEqualTo(requestDTO.getAccount()).andPasswordEqualTo(requestDTO.getPassword());
    	UserTest user = dbService.selectOneByExample(example, UserTest.class);
    	if(user == null) {
    		throw SystemException.init("用户名密码错误");
    	}
    	LoginRespDTO lr = FuncTool.copyPropertiesClass(user, LoginRespDTO.class);
    	lr.setToken(tokenService.createToken(user.getId().toString()));
    	
    	return new PublicResponseObjDTO<LoginRespDTO>(lr);
    }
    
	@ApiDescribe("用户查询")
    @RequestMapping("/queryUsers")
    public PublicResponsePagerDTO<UserDTO> queryUsers(@RequestBody @Valid QueryUsersDTO requestDTO) throws Exception{
    	UserTestExample example = new UserTestExample();
    	example.createCriteria();
    	if(!StringUtils.isEmpty(requestDTO.getKeywords())) {
    		example.getOredCriteria().get(0).andNickNameLike("%"+requestDTO.getKeywords()+"%");
    	}
    	if(!StringUtils.isEmpty(requestDTO.getRegistDate())) {
    		example.getOredCriteria().get(0).andCreateTimeBetween(requestDTO.getRegistDate(), DateTool.addDays(requestDTO.getRegistDate(), 1));
    	}
    	Page p = requestDTO.getPageObj();
    	List<UserTest> list = dbService.selectByExample(example, UserTest.class,p);
    	List<UserDTO> listDTO = FuncTool.copyPropertiesList(list, UserDTO.class);
    	return new PublicResponsePagerDTO<UserDTO>(listDTO,p);
    	
    }
  
}
