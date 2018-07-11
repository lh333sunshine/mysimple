package com.mysimple.common.autodoc;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在Control方法上添加此描述，用于生成文档。
 */

@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiDescribe {
	
	//接口名称
	String value();
	
	//接口描述，数组每条表示一行说明
	String detail()  default "";
}
