package com.mysimple.service;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mysimple.common.util.ClassUtils;
import com.mysimple.common.util.FuncTool;
import com.mysimple.common.util.Page;
import com.mysimple.common.util.SystemException;
import com.mysimple.dao.SysLogsRequest;
import com.mysimple.dao.SysLogsRequestExample;
import com.mysimple.dao.SysLogsRequestWithBLOBs;
import com.mysimple.dao.UserTest;
import com.mysimple.dao.UserTestExample;
import com.mysimple.dao.mapper.SysLogsRequestMapper;
import com.mysimple.dao.mapper.UserTestMapper;
 
/**
 * mybatis 数据库操作工具类
 *
 */
@Service
public class CommonDbService {
	//映射mybatis中 DAO类 to Example类
	private static Map<Class,Class> exampleMap = new HashMap<>();
	//映射mybatis中 DAO类 to Mapper类
	private Map<Class,Object> myMapperMap = new HashMap<>();
	
	private static Map<Class,Object> autoMapperMap = new HashMap<>();
	
	//非自动生成的dao填写在此函数中
	private Map<Class,Object> getMyMapper(){
		if(myMapperMap.isEmpty()){
		}
		return myMapperMap;
	}
	
	private Object getMapper(Class clazz) throws Exception{
		if(this.getMyMapper().containsKey(clazz)){
			return this.getMyMapper().get(clazz);
		}
		if(this.getAutoMapperMap().containsKey(clazz)){
			return this.getAutoMapperMap().get(clazz);
		}
		throw SystemException.init("DbService dao can't find:" + clazz.getName() );
	}
	
	public <T> T getById(Object id, Class<T> daoClass) throws Exception{
		Object mapper = getMapper(daoClass) ;
		Method m = mapper.getClass().getMethod("selectByPrimaryKey", id.getClass());
		Object o = m.invoke(mapper, id);
		return (T) o;
	}
	
	public <T> List<T> getByIds(Set<Object> ids, Class<T> daoClass)throws Exception{
		if(ids == null || ids.size() == 0){
			return new ArrayList<>();
		}
		Object example = exampleMap.get(daoClass).newInstance();
		Object criteria = example.getClass().getMethod("createCriteria").invoke(example);
		criteria.getClass().getMethod("andIdIn", List.class).invoke(criteria,new ArrayList<Object>(ids));
		Object mapper = getMapper(daoClass) ;
		Method m = mapper.getClass().getMethod("selectByExample", example.getClass());
		Object o = m.invoke(mapper, example);
		return (List<T>) o;
	}
	
	
	public <T> List<T> getByIds(List<Object> ids, Class<T> daoClass)throws Exception{
		if(ids == null || ids.size() == 0){
			return new ArrayList<>();
		}
		List list = getByIds(new HashSet<Object>(ids) , daoClass);
		List result = new ArrayList<>();
		for(Object id : ids){
			for(Object item : list){
				if(id.equals(item.getClass().getMethod("getId").invoke(item))){
					result.add(item);
				}
			}
		}
		return (List<T>)result;
	}
	
	
	public <T> List<T> getByIds(Object[] ids, Class<T> daoClass)throws Exception{
		if(ids == null || ids.length == 0){
			return new ArrayList<>();
		}
		return getByIds(Arrays.asList(ids) , daoClass);
	}
	
	public <T> List<T> selectByExample(Object example, Class<T> daoClass) throws Exception{
		if(!exampleMap.get(daoClass).equals(example.getClass())){
			throw SystemException.init(" example is not match the daoClass"+example.getClass().getName()+","+daoClass.getName());
		}
		Object mapper = getMapper(daoClass) ;
		Method m = mapper.getClass().getMethod("selectByExample", example.getClass());
		Object o = m.invoke(mapper, example);
		return (List<T>) o;
	}
 	
	public <T> T selectOneByExample(Object example, Class<T> daoClass) throws Exception{
		Page page = new Page(1,1);
		List<T> list = selectByExample(example,daoClass,page);
		if(list == null || list.size()==0) {
			return null;
		}
		return list.get(0);
	}
	
	public <T> List<T> selectByExample(Object example, Class<T> daoClass, Page page) throws Exception{
		if(!exampleMap.get(daoClass).equals(example.getClass())){
			throw SystemException.init(" example is not match the daoClass:"+example.getClass().getName()+","+daoClass.getName());
		}
		if(page != null){
			example.getClass().getMethod("setLimit", Integer.class).invoke(example, page.getPageSize());
			example.getClass().getMethod("setOffset", Integer.class).invoke(example, page.getOffset());
			if(!StringUtils.isEmpty(page.getSortby())){
				example.getClass().getMethod("setOrderByClause", String.class).invoke(example, page.getSortby());
			}
			page.setTotalCount(countByExample(example,daoClass));
		}
		Object mapper = getMapper(daoClass) ;
		Method m = mapper.getClass().getMethod("selectByExample", example.getClass());
		Object o = m.invoke(mapper, example);
		return (List<T>) o;
	}
	
	
	public Integer countByExample(Object example, Class daoClass) throws Exception{
		if(!exampleMap.get(daoClass).equals(example.getClass())){
			throw SystemException.init(" example is not match the daoClass"+example.getClass().getName()+","+daoClass.getName());
		}
		Object mapper = getMapper(daoClass) ;
		Method m = mapper.getClass().getMethod("countByExample", example.getClass());
		Object o = m.invoke(mapper, example);
		return (Integer) o;
	}
	
	public Integer insert(Object dao ) throws Exception{
		Class daoClass = dao.getClass();
		Object mapper = getMapper(daoClass) ;
		Method m = mapper.getClass().getMethod("insertSelective", daoClass);
		Object o = m.invoke(mapper, dao);
		return (Integer) o;
	}
	
	public Integer updateByKey(Object dao) throws Exception{
		Class daoClass = dao.getClass();
		Object mapper = getMapper(daoClass) ;
		Method m = mapper.getClass().getMethod("updateByPrimaryKeySelective", daoClass);
		Object o = m.invoke(mapper, dao);
		return (Integer) o;
	}
	
	public Integer updateByExample(Object dao,Object example)  throws Exception{
		Class daoClass = dao.getClass();
		Object mapper = getMapper(daoClass) ;
		Method m = mapper.getClass().getMethod("updateByExampleSelective", daoClass, example.getClass());
		Object o = m.invoke(mapper, dao, example);
		return (Integer) o;
	}
	
 
	public Integer deleteByKey(Object key, Class daoClass) throws Exception{
		Object mapper = getMapper(daoClass) ;
		Method m = mapper.getClass().getMethod("deleteByPrimaryKey", key.getClass());
		Object o = m.invoke(mapper, key);
		return (Integer) o;
	}
	 
	
	public Integer deleteByExample(Class daoClass ,Object example)  throws Exception{
		Object mapper = getMapper(daoClass) ;
		Method m = mapper.getClass().getMethod("deleteByExample", example.getClass());
		Object o = m.invoke(mapper, example);
		return (Integer) o;
	}
	
	// 很多表时，可以使用此函数生成 entity,map,example
	public static void main(String[] args) {
		//扫描dao文件包，生成代码
		String package1 = "com.example.dao";
		List<Class> listClass = ClassUtils.scanPackage(package1);
		Set<String> set = new HashSet<>();
		Set<String> set1 = new HashSet<>();
		Set<String> set2 = new HashSet<>();
		Set<String> setFull = new HashSet<>();
		for(Class c : listClass){
			setFull.add(c.getName());
			String s = c.getName().replace(package1+".", "")
					.replace("mapper.", "")
					.replace("Example", "")
					.replace("$Criterion", "")
					.replace("$GeneratedCriteria", "");
			set2.add(s);
			
			s = s.replace("WithBLOBs", "");
			set1.add(s);
			s = s.replace("Mapper", "");
			if(!s.contains(".") && setFull.contains(package1+"."+s)){
				set.add(s);
			}
		}
		//过滤自定义的类
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			if(!set1.contains(it.next()+"Mapper")){
				it.remove();
			}
		}
		
		List<String> list = new ArrayList<>();
		list.addAll(set);
		FuncTool.sortList(list,"toString");
		System.out.println("\tstatic{");
		for(String s: list){
			System.out.println(MessageFormat.format("\t\texampleMap.put({0}.class, {0}Example.class);", s));
			if(set2.contains(s+"WithBLOBs")){
				System.out.println(MessageFormat.format("\t\texampleMap.put({0}WithBLOBs.class, {0}Example.class);", s));
			}
		}
		System.out.println("\t}");

		for(String s: list){
			System.out.println(MessageFormat.format("\t@Autowired private {0}Mapper {1}Mapper;", s,StringUtils.uncapitalize(s)));
		}
		
		System.out.println("\tprivate Map<Class,Object> getAutoMapperMap(){");
		System.out.println("\t\tif(autoMapperMap.isEmpty()){");
		for(String s: list){
			System.out.println(MessageFormat.format("\t\t\tautoMapperMap.put({0}.class, {1}Mapper);", s,StringUtils.uncapitalize(s)));
			if(set2.contains(s+"WithBLOBs")){
				System.out.println(MessageFormat.format("\t\t\tautoMapperMap.put({0}WithBLOBs.class, {1}Mapper);", s,StringUtils.uncapitalize(s)));
			}
		}
		System.out.println("\t\t}");
		System.out.println("\t\treturn autoMapperMap;");
		System.out.println("\t}");
	}
	
	//自动生成生成的代码结果
	static{
		exampleMap.put(SysLogsRequest.class, SysLogsRequestExample.class);
		exampleMap.put(SysLogsRequestWithBLOBs.class, SysLogsRequestExample.class);
		exampleMap.put(UserTest.class, UserTestExample.class);
	}
	@Autowired private SysLogsRequestMapper sysLogsRequestMapper;
	@Autowired private UserTestMapper userTestMapper;
	private Map<Class,Object> getAutoMapperMap(){
		if(autoMapperMap.isEmpty()){
			autoMapperMap.put(SysLogsRequest.class, sysLogsRequestMapper);
			autoMapperMap.put(SysLogsRequestWithBLOBs.class, sysLogsRequestMapper);
			autoMapperMap.put(UserTest.class, userTestMapper);
		}
		return autoMapperMap;
	}

	
}
