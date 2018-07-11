package com.mysimple.common.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysimple.common.autodoc.DemoData;
import com.mysimple.common.autodoc.DtoFiledDescribe;
import com.mysimple.common.autodoc.DtoFiledLoop;

public class FuncTool {
	private static Logger log = LoggerFactory.getLogger(FuncTool.class);
	
	public static synchronized String createUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static void copyProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target);
	}
	
	
	/**
	 * 将字符按照指定regex分隔成List<Int>
	 * @param str
	 * @param tag
	 * @return
	 */
	public static List<Integer> splitToIntList(String str, String regex){
		if(StringUtils.isEmpty(str)){
			return new ArrayList<>();
		}
		String[] ss = str.trim().split(regex);
		List<Integer> list = new ArrayList<>();
		for(String item : ss){
			list.add(Integer.parseInt(item));
		}
		return list;
	}
	
	public static List<String> splitToList(String str , String regex){
		if(StringUtils.isEmpty(str)){
			return new ArrayList<>();
		}
		String[] ss = str.trim().split(regex);
		return Arrays.asList(ss);
	}
	
	public static List<String> splitToList(String str ){
		return splitToList(str,",");
	}
	
	public static Set<String> splitToMap(String str){
		String[] ss = str.split(",");
		Set<String> set = new HashSet<>();
		for(String item : ss){
			set.add(item);
		}
		return set;
	}
	
	public static <T> T copyPropertiesClass(Object source, Class<T> c) {
		try {
			if (source == null) {
				return null;
			}
			Object o = c.newInstance();
			BeanUtils.copyProperties(source, o);
			return (T) o;
		} catch (Exception e) {
			
		}

		return null;
	}

	public static List copyPropertiesList(List source, Class c) {
		try {
			if (source == null) {
				return null;
			}
			List list = new ArrayList<>();
			for (Object o : source) {
				Object t = c.newInstance();
				BeanUtils.copyProperties(o, t);
				list.add(t);
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}


	public static List<String> copyPropertiesByName(List list, String propertyName) {
		List<String> myList = new ArrayList<>();
		Set<Object> myset = new HashSet<>();
		if (list != null) {
			for (Object o : list) {
				try {
					Method m = o.getClass().getMethod("get" + StringUtils.capitalize(propertyName));
					Object returnO = m.invoke(o);
					if (m != null && !StringUtils.isEmpty(returnO) && !myset.contains(returnO)) {
						myList.add(String.valueOf(returnO));
						myset.add(returnO);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return myList;
	}

	public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
	
	public static void getClassField(Class aClazz, List<Field> list) {
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
	
	public static <T> T createDemoData(Class<T> clazz, Class genClass,boolean isLoop){
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
	
	public static void createDemoData(Class clazz, Class genClass, Object o,boolean isLoop){
		try {
			List<Field> listFileds = new ArrayList<>();
			FuncTool.getClassField(clazz, listFileds);
			for (Field filed : listFileds) {
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
						setFiledValue(o,filed,12121);
					}else if(Float.class.equals(filed.getType())){
						setFiledValue(o,filed,12221);
					}else if(Date.class.equals(filed.getType())){
						setFiledValue(o,filed,new Date());
					}else if(Boolean.class.equals(filed.getType())){
						setFiledValue(o,filed,true);
					}else if(Long.class.equals(filed.getType())){
						setFiledValue(o,filed,Long.parseLong(DemoData.STMAP));
					}else if(Byte.class.equals(filed.getType())){
						setFiledValue(o,filed,Byte.parseByte("1"));
					}else if(String.class.equals(filed.getType())){
						setFiledValue(o,filed,"");
					}
				}
				//设置默认值防止空异常
				if (filed.getType().getName().contains("com.huaao")) {
					setFiledValue(o, filed, filed.getType().newInstance());
				}
				
				if(filed.getType().getPackage().toString().contains("com.huaao") && !isLoop){
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
							if(pname.contains("com.huaao") && !isLoop){
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
					if(pname.contains("com.huaao") && !isLoop){
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
	
	/**
	 * 根据List集合中对象的method返回值进行排序， 正序
	 * @param list
	 * @param method
	 */
	public static void sortList(List list, final String method){
		if(list == null){
			return ;
		}
       //排序
       Collections.sort(list, new Comparator() {        
           public int compare(Object a, Object b) {
           int ret = 0;
           try{
               Method m1 = (a).getClass().getMethod(method, null);
               Method m2 = (b).getClass().getMethod(method, null);
               Object r1 = m1.invoke((a), null);
               Object r2 = m2.invoke((b), null);
               if(r1 == null && r2 == null){
            	   return 0;
               }
               if(r1 == null){
            	   return -1;
               }
               if(r2 == null){
            	   return 1;
               }
               if(r1 instanceof Integer && r2 instanceof Integer){
            	  return new Integer(r1.toString()).compareTo(new Integer(r2.toString()));
               }
               ret = r1.toString().compareTo(r2.toString());                  
           }catch(NoSuchMethodException ne){
               ne.printStackTrace();
              }catch(IllegalAccessException ie){
            	  ie.printStackTrace();
              }catch(InvocationTargetException it){
            	  it.printStackTrace();
              }
           return ret;
           }
        });
    }
	public static void sortList(List list){
		sortList(list, "toString");
	}
	
	/**
	 * 根据List集合中对象的method返回值进行排序， 倒序
	 * @param list
	 * @param method
	 */
	public static void sortDescList(List list, final String method){
		if(list == null){
			return ;
		}
		sortList(list,method);
		Collections.reverse(list);
	}
	
	/**
	 * 对list进行分页, 按照 sortMethod 顺序排序, 总数已填入到page对象
	 * @param list
	 * @param page
	 * @param sortMethod 排序的方法，无排序时，可为空
	 * @param 正序，倒序， asc 或 
	 * @return
	 */
	public static List pagerList(List list,Page page, final String sortMethod, String ascOrDesc){
		
		if(list == null){
			list = new ArrayList<>();
		}
		if(sortMethod != null){
			if(ascOrDesc == null || ascOrDesc.equals("asc")){
				sortList(list,sortMethod);
			}else{
				sortDescList(list,sortMethod);
			}
		}
		page.setTotalCount(list.size());
		if(list.size()>page.getOffset()){
			if(page.getOffset()+page.getPageSize() <= list.size()){
				return list.subList(page.getOffset(), page.getOffset()+page.getPageSize());
			}else{
				return list.subList(page.getOffset(), list.size());
			}
		}else{
			return new ArrayList<>();
		}
	}
	
	/**
	 * 对list进行分页,
	 * @param list
	 * @param page
	 * @return
	 */
	public static List pagerList(List list,Page page){
		
		if(list == null){
			list = new ArrayList<>();
		}
		page.setTotalCount(list.size());
		if(list.size()>page.getOffset()){
			if(page.getOffset()+page.getPageSize() <= list.size()){
				return list.subList(page.getOffset(), page.getOffset()+page.getPageSize());
			}else{
				return list.subList(page.getOffset(), list.size());
			}
		}else{
			return new ArrayList<>();
		}
	}
	 
	public static String getErrorInfoFromException(Exception e) {  
        try {  
            StringWriter sw = new StringWriter();  
            PrintWriter pw = new PrintWriter(sw);  
            e.printStackTrace(pw);  
            return sw.toString() ;  
        } catch (Exception e2) {  
            return "bad getErrorInfoFromException";  
        }  
    }
	
	/**
	 * 获取指定文件夹下的所有文件路径
	 * @param path
	 * @return
	 */
	public static List<String> getFolderFiles(String path){   
		List<String> list = new ArrayList<>();
        File file = new File(path);   
        File[] array = file.listFiles();   
          
        for(int i=0;i<array.length;i++){   
            if(array[i].isFile()){   
            	list.add(array[i].getPath());
            }else if(array[i].isDirectory()){   
            	list.addAll(getFolderFiles(array[i].getPath()));   
            }   
        }   
        return list;
    }   
	
	public static void sortListMap(List<? extends Map> list, final String mapKey) {
		if (list == null) {
			return;
		}
		Collections.sort(list, new Comparator<Map>() {
			public int compare(Map o1, Map o2) {
				Object a = o1.get(mapKey);
				Object b = o2.get(mapKey);
				if (a == null && b == null) {
					return 0;
				}
				if (a == null) {
					return -1;
				}
				if (b == null) {
					return 1;
				}
				// 升序
				if (a instanceof Integer && b instanceof Integer) {
					return new Integer(a.toString()).compareTo(new Integer(b.toString()));
				}
				return a.toString().compareTo(b.toString());
			}
		});
	}
	public static void sortDescListMap(List<? extends Map> list, final String mapKey) {
		if (list == null) {
			return;
		}
		Collections.sort(list, new Comparator<Map>() {
			public int compare(Map o1, Map o2) {
				Object a = o1.get(mapKey);
				Object b = o2.get(mapKey);
				if (a == null && b == null) {
					return 0;
				}
				if (a == null) {
					return 1;
				}
				if (b == null) {
					return -1;
				}
				// 升序
				if (a instanceof Integer && b instanceof Integer) {
					return new Integer(b.toString()).compareTo(new Integer(a.toString()));
				}
				return b.toString().compareTo(a.toString());
			}
		});
	}
	
	public static <T> T jsonToObject(String json,Class<T> valueType) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, valueType);
		} catch (Exception e) {
			log.error("", e);
			return null;
		}
	}
	
	public static String jsonToString(Object o) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(o);
		} catch (Exception e) {
			log.error("", e);
			return null;
		}
	}
}

