package com.mysimple.demo;

import com.mysimple.common.autodoc.DocTool;
import com.mysimple.controller.UserController;

public class DemoCreateDoc {

	public static void main(String[] args) throws Exception{
		//设定生成文档需要保存到的本地路径
		String path = "c:/test/";
		
		//设定需要生成文档的接口
//		DocTool.createDocByMapping(path, UserController.class, "/register"); 
//		DocTool.createDocByMapping(path, UserController.class, "/login"); 
		DocTool.createDocByMapping(path, UserController.class, "/queryUsers"); 
		System.exit(0);
	}
	
	
}
