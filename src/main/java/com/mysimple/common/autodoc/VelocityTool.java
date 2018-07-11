package com.mysimple.common.autodoc;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 静态化工具
 */
public class VelocityTool {
	
	private static Logger log = LoggerFactory.getLogger(VelocityTool.class);
	private static VelocityEngine ve = new VelocityEngine();
	static {
		ve.setProperty("resource.loader", "classpath");
		ve.setProperty("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		ve.setProperty("input.encoding","UTF-8");
		ve.setProperty("output.encoding","UTF-8");
		ve.init();
	}
	/**
	 * 生成静态页面
	 * @param templateUrl 模板地址
	 * @param outputUrl 输出地址
	 * @param reqMap 请求参数
	 */
	public static void generate(String templateUrl, String outputUrl, Map<String, String> reqMap){
		try {
			VelocityContext context = new VelocityContext();
			String key = null;
			for(Iterator<String> iter = reqMap.keySet().iterator(); iter.hasNext();){
				key = iter.next();
				context.put(key, reqMap.get(key));
			}
			Template template = ve.getTemplate(templateUrl);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputUrl), "UTF-8"));  
			template.merge(context, writer);  
			writer.close();
		} catch (IOException e) {
			log.error("generate fail", e);
		}  
	}
	
	
	public static String generate(String templateUrl,Map<String,Object> reqMap){
		try {
			VelocityContext context = new VelocityContext();
			String key = null;
			for(Iterator<String> iter = reqMap.keySet().iterator(); iter.hasNext();){
				key = iter.next();
				context.put(key, reqMap.get(key));
			}
			Template template = ve.getTemplate(templateUrl);
			StringWriter sw = new StringWriter();
			template.merge(context, sw);  
			sw.close();
			return sw.toString();
		} catch (IOException e) {
			log.error("generate fail", e);
			return null;
		}  
	}
	
}
