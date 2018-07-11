package com.mysimple.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlPageController {

	@RequestMapping("/page/*.html")
	public String page(HttpServletRequest request) {
		String page = request.getRequestURI().replace(".html","").replace("/page/","");
		return page;
	}
}
