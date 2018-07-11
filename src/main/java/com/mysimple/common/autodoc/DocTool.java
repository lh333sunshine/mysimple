package com.mysimple.common.autodoc;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jayway.jsonpath.internal.JsonFormatter;
import com.mysimple.common.util.ClassUtils;
import com.mysimple.common.util.FuncTool;


public class DocTool {
	
	private static String PROJECT_PACKAGE = "";
	private static Set<String> FILTER_FILED = new HashSet<>();
	static {
		FILTER_FILED.add("serialVersionUID");
		
		String packageName = DocTool.class.getPackage().getName();
		int index = packageName.indexOf(".", packageName.indexOf(".")+1);
		PROJECT_PACKAGE = packageName.substring(0, index);
	}
	/**
	 * 根据Controller中的mapping方法，创建接口文档 
	 * @param path , 文档保存的路径
	 * @param controlClass , Controller中的control类
	 * @param requestMapping , RequestMapping值
	 */
	public static void createDocByMapping(String path, Class controlClass , String requestMapping) throws Exception{
		Map jo = new HashMap<>();
		scanControlMethodToJson(controlClass ,requestMapping,jo);
		createMdContent(jo,path);
 	}
	
	private static void getClassField(Class aClazz, List<Field> list) {
		if (aClazz == null) {
			return;
		}
		Class superclass = aClazz.getSuperclass();
		if (superclass != Object.class) {// 简单的递归一下
			getClassField(superclass, list);
		}
		
		Field[] declaredFields = aClazz.getDeclaredFields();
		for (Field field : declaredFields) {
			list.add(field);
		}
	}
	 
	private static void readDtoFiledToJson(Class clazz, Map dataJson, Class genClass , String parentName,boolean isLoop) {
		if(parentName == null || "".equals(parentName)){
			parentName = "";
		}else {
			parentName = parentName + ".";
		}
 		List<Field> listFileds = new ArrayList<>();
		getClassField(clazz, listFileds);
		for (Field filed : listFileds) {
			if(FILTER_FILED.contains(filed.getName())){
				continue;
			}
			if(filed.isAnnotationPresent(DtoFiledDocHide.class)){
				continue;
			}
			filed.setAccessible(true);
			String desc = "";
			String demo = "";
			DtoFiledDescribe filedDesc = filed.getAnnotation(DtoFiledDescribe.class);
			JsonFormat jf = filed.getAnnotation(JsonFormat.class);
			if (filedDesc != null) {
				desc = filedDesc.value();
				if(jf != null) {
					desc += " 格式:"+jf.pattern();
				}
				demo = filedDesc.demo();
			}
			 
			if (!List.class.equals(filed.getType())) {
				Map jo = new HashMap();
				jo.put("_desc", desc);
				jo.put("_demo", demo);
				String name = filed.getType().getName();
				//泛型为包装类型特殊情况
				if (genClass != null && "data".equals(parentName + filed.getName()) && genClass.getName().contains("java.lang")) {
					jo.put("_type", genClass.getSimpleName());
				} else if (name.contains("java.lang") || name.contains("java.util")) {
					jo.put("_type", filed.getType().getSimpleName());
				}else {
					jo.put("_type", "Object");  //自定义的类型，如 *Response等全归为Object
				}
				
				if(filed.getAnnotation(NotBlank.class) != null || filed.getAnnotation(NotEmpty.class) != null || filed.getAnnotation(NotNull.class) != null || filed.getName().equals("token")){
					jo.put("_notNull", "Y");
				}else{
					jo.put("_notNull", "N");
				}
				if(filedDesc!=null && filedDesc.notNull()) {
					jo.put("_notNull", "Y");
				}
				
				dataJson.put(parentName + filed.getName(), jo);
				if(!filed.getType().isPrimitive() && filed.getType().getPackage().toString().contains(PROJECT_PACKAGE) && !isLoop){
					readDtoFiledToJson(filed.getType(), dataJson,null,parentName+filed.getName() , filed.getAnnotation(DtoFiledLoop.class) != null);
				}
				
			}else{
				Map jo = new HashMap();
				jo.put("_desc", desc);
				jo.put("_demo", demo);
				jo.put("_type", "List");
				Type fc = filed.getGenericType();
				Class c = null;
				try {
					ParameterizedType pt = (ParameterizedType) fc;
					c = (Class)pt.getActualTypeArguments()[0];
				} catch (Exception e) {
 				}
				dataJson.put(parentName + filed.getName(), jo);
				if(c != null ){
					if(c.getPackage().toString().contains(PROJECT_PACKAGE) && !isLoop){
						readDtoFiledToJson(c, dataJson,null,parentName + filed.getName(),  filed.getAnnotation(DtoFiledLoop.class) != null);
					}
				} 
			}
		}
		
		if(genClass != null &&  !genClass.isPrimitive() && !String.class.equals(genClass) 
				&& !genClass.getPackage().toString().contains("java.lang")){
			readDtoFiledToJson(genClass, dataJson,null,"data",  false);
		}
	}
	
	private static <T> T createDemoData(Class<T> clazz, Class genClass,boolean isLoop){
		try {
			T o = null;
			o = clazz.newInstance();
			createDemoData(clazz, genClass, o,isLoop);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void createDemoData(Class clazz, Class genClass, Object o,boolean isLoop){
		try {
			List<Field> listFileds = new ArrayList<>();
			FuncTool.getClassField(clazz, listFileds);
			for (Field filed : listFileds) {
				if(FILTER_FILED.contains(filed.getName())){
					continue;
				}
				filed.setAccessible(true);
				DtoFiledDescribe filedDemoData = filed.getAnnotation(DtoFiledDescribe.class);
				if (filedDemoData != null && !StringUtils.isEmpty(filedDemoData.demo())) {
					if(Integer.class.equals(filed.getType())){
						setFiledValue(o,filed,Integer.parseInt( filedDemoData.demo()));
					}else if(Double.class.equals(filed.getType())){
						setFiledValue(o,filed,Double.parseDouble( filedDemoData.demo()));
					}else if(Float.class.equals(filed.getType())){
						setFiledValue(o,filed,Float.parseFloat( filedDemoData.demo()));
					}else if(Date.class.equals(filed.getType())){
						setFiledValue(o,filed,new Date());
					}else if(Boolean.class.equals(filed.getType())){
						setFiledValue(o,filed,true);
					}else if(Long.class.equals(filed.getType())){
						setFiledValue(o,filed,Long.parseLong( filedDemoData.demo()));
					}else if(Byte.class.equals(filed.getType())){
						setFiledValue(o,filed,Byte.parseByte( filedDemoData.demo()));
					}else if(String.class.equals(filed.getType())){
						setFiledValue(o,filed,filedDemoData.demo());
					}
				}else{
					if(Integer.class.equals(filed.getType())){
						setFiledValue(o,filed,1);
					}else if(Double.class.equals(filed.getType())){
						setFiledValue(o,filed,121.21d);
					}else if(Float.class.equals(filed.getType())){
						setFiledValue(o,filed,12221f);
					}else if(Date.class.equals(filed.getType())){
						setFiledValue(o,filed,new Date());
					}else if(Boolean.class.equals(filed.getType())){
						setFiledValue(o,filed,true);
					}else if(Long.class.equals(filed.getType())){
						setFiledValue(o,filed,1);
					}else if(Byte.class.equals(filed.getType())){
						setFiledValue(o,filed,Byte.parseByte("1"));
					}else if(String.class.equals(filed.getType())){
						setFiledValue(o,filed,"");
					}
				}
				//设置默认值防止空异常
				if (filed.getType().getName().contains(PROJECT_PACKAGE)) {
					setFiledValue(o, filed, filed.getType().newInstance());
				}
				if(filed.getType().getPackage().toString().contains(PROJECT_PACKAGE) && !isLoop){
					Object o1 =createDemoData(filed.getType(), null,filed.getAnnotation(DtoFiledLoop.class) != null);
					setFiledValue(o, filed, o1);
				}
				//list 
				if(List.class.equals(filed.getType()) ){
					List list = new ArrayList<>();
					if(filed.getGenericType().toString().contains("List<T>") && !isLoop){
						Object o1 = createDemoData(genClass, null,filed.getAnnotation(DtoFiledLoop.class) != null);
						list.add(o1);
					}else{
						Type fc = filed.getGenericType();
						Class c = null;
						try {
							ParameterizedType pt = (ParameterizedType) fc;
							c = (Class)pt.getActualTypeArguments()[0];
						} catch (Exception e) {}
						if(c != null){
							String pname = c.getPackage().toString();
							if(pname.contains(PROJECT_PACKAGE) && !isLoop){
								Object o1 = createDemoData(c, null,filed.getAnnotation(DtoFiledLoop.class) != null);
								list.add(o1);
							}
							if(String.class.equals(c)){
								if (filedDemoData != null){
									String[] str = filedDemoData.value().split(";");
									for(String item : str){
										list.add(item);	
									}
								}
							}else if(Integer.class.equals(c)){
								if (filedDemoData != null){
									String[] str = filedDemoData.value().split(";");
									for(String item : str){
										list.add(Integer.parseInt(item));	
									}
								}
							}
						}
					}
					setFiledValue(o,filed,list);
				}
				//obj
				if("T".equals(filed.getGenericType().toString()) && genClass != null){
					String pname = genClass.getPackage().toString();
					if(pname.contains(PROJECT_PACKAGE) && !isLoop){
						setFiledValue(o,filed,createDemoData(genClass, null,filed.getAnnotation(DtoFiledLoop.class) != null));
					}
				}
				//泛型擦除了，或者方法头没写泛型
				if (genClass == null && Object.class.equals(filed.getType())) {
					setFiledValue(o, filed, new Object());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void setFiledValue(Object o , Field field, Object value ){
		try {
			Method m = o.getClass().getMethod("set"+StringUtils.capitalize(field.getName()) , field.getType());
			m.invoke(o, value);
		} catch (Exception e) {
			System.out.println(e.getMessage()); 
		}
	}
	
	private static void scanControlMethodToJson(Class c , String requestMapping,Map jo) {
		scanControlClassToJson(c, jo, requestMapping);
	}
	
	private static void scanControlClassToJson(Class clazzApi,Map jo,String includeRequestMapping) {
		RequestMapping clazzRm = (RequestMapping) clazzApi.getAnnotation(RequestMapping.class);
		if (clazzRm == null) {
			return;
		}
		ApiDescribe clazzAd = (ApiDescribe) clazzApi.getAnnotation(ApiDescribe.class);
		String catalog = "";
		if (clazzAd != null) {
			catalog = clazzAd.value();
		}
		String mapApi = clazzRm.value()[0];
		if (!mapApi.startsWith("/")) {
			mapApi = "/" + mapApi;
		}
		// 反射API
		Method[] methods = clazzApi.getMethods();
		for (Method itemMethod : methods) {
			RequestMapping rm = itemMethod.getAnnotation(RequestMapping.class);
			ApiDescribe ad = itemMethod.getAnnotation(ApiDescribe.class);
			if (rm != null) {
				if(includeRequestMapping !=null && !includeRequestMapping.equals(rm.value()[0])) {
					continue;
				}
				
				String tmp = rm.value()[0];
				if (!tmp.startsWith("/")) {
					tmp = "/" + rm.value()[0];
				}
				 
				String apiUrl = mapApi + tmp;
				 
				Map apiJson = new HashMap();

				if (ad != null) {
					apiJson.put("_name", ad.value());
					apiJson.put("_desc", ad.detail());
				} else {
					apiJson.put("_desc", "");
				}
				String method = "";
				for (RequestMethod item : rm.method()) {
					method += item.name();
				}
				if (StringUtils.isEmpty(method)) {
					method = "POST GET";
				}
				apiJson.put("_method", method);

				Map requestJson = new HashMap();
				Map responseJson = new HashMap();

				// 获取传入,传出参数
				Class requestDto = itemMethod.getParameterTypes()[0];
				Class responseDto = itemMethod.getReturnType();
				
				readDtoFiledToJson(requestDto, requestJson, null, null,false);
				
				Type fc = itemMethod.getGenericReturnType();
				String demoData = null;
				if (fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型
				{
					ParameterizedType pt = (ParameterizedType) fc;
					Type t = pt.getActualTypeArguments()[0];
					readDtoFiledToJson(responseDto, responseJson, (Class) t, null,false);
					demoData = FuncTool.jsonToString(createDemoData(responseDto, (Class) t,false)) ;
				} else {
					readDtoFiledToJson(responseDto, responseJson, null,null,false);
					demoData = FuncTool.jsonToString(createDemoData(responseDto, null,false));
				}
				
				String demoRequestData = FuncTool.jsonToString(createDemoData(requestDto, null,false) );
				apiJson.put("_request", requestJson);
				apiJson.put("_response", responseJson);
				apiJson.put("_demoData", demoData );
				apiJson.put("_demoRequestData", demoRequestData );
				apiJson.put("_catalog", catalog);
				jo.put(apiUrl, apiJson);
			}
		}
	}
	
	private static void scanControlPackagePathToJson(String packagePath,Map jo) {
		// 扫描包
		List<Class> listApiClass = ClassUtils.scanPackage(packagePath);
		for (Class clazzApi : listApiClass) {
			scanControlClassToJson(clazzApi,jo,null);
		}
	}
	
	private static void  createMdContent(Map jo,String path) throws Exception{
		List<String> listKey = new ArrayList<>();
		listKey.addAll(jo.keySet());
		FuncTool.sortList(listKey, "toString");
		for (String key : listKey) {
			Map<String, Object> map = new HashMap<>();
			map.put("url", key );
			Map apiJson = (Map)jo.get(String.valueOf(key));

			String desc = (String)apiJson.get("_desc");
			String apiName = (String)apiJson.get("_name");
			map.put("name", apiName);
			map.put("desc", desc);
			map.put("method", (String)apiJson.get("_method"));
			map.put("demoData", JsonFormatter.prettyPrint((String)apiJson.get("_demoData")));
			map.put("demoRequestData", JsonFormatter.prettyPrint((String)apiJson.get("_demoRequestData")));
			
			if (StringUtils.isEmpty(apiName)) {
				System.out.println("erro: " + key.toString() + " don't define api chinese name;");
				continue;
			}  

			Map requestJson = (Map)apiJson.get("_request");
			Map responseJson = (Map)apiJson.get("_response");
			List<Map<String, String>> requestList = new ArrayList<>();
			for (Object itemKey : requestJson.keySet()) {
				Map tmpjo = (Map)requestJson.get(String.valueOf(itemKey));
				Map<String, String> itemmap = new HashMap<>();
				itemmap.put("name", String.valueOf(itemKey));
				itemmap.put("notNull", (String)tmpjo.get("_notNull"));
				itemmap.put("type", (String)tmpjo.get("_type"));
				itemmap.put("desc", (String)tmpjo.get("_desc"));
				requestList.add(itemmap);
			}  
			FuncTool.sortListMap(requestList, "name");
			FuncTool.sortDescListMap(requestList, "notNull");
			map.put("requestList", requestList);

			List<Map<String, String>> responseList = new ArrayList<>();
			for (Object itemKey : responseJson.keySet()) {
				Map tmpjo = (Map)responseJson.get(String.valueOf(itemKey));
				Map<String, String> itemmap = new HashMap<>();
				itemmap.put("name", String.valueOf(itemKey));
				itemmap.put("type", (String)tmpjo.get("_type"));
				itemmap.put("desc", (String)tmpjo.get("_desc"));
				responseList.add(itemmap);
			}
			FuncTool.sortListMap(responseList, "name");
			map.put("responseList", responseList);

			String packageStr = DocTool.class.getPackage().toString().replace("package ", "").replace(".", "/");
			String content = VelocityTool.generate(packageStr+"/api_template.vm", map);
			
			File f = new File(path+"/"+map.get("name")+".md");
			FileUtils.writeStringToFile(f, content, "UTF-8");
			System.out.println("file ok:"+f.getAbsolutePath()+" ");
		}
	}
}
