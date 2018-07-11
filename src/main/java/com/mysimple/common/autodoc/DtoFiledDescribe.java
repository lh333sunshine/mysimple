package com.mysimple.common.autodoc;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在Dto类中的字段上添加此描述，用于生成文档。
 */

@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DtoFiledDescribe {
	//字段描述说明
	String value();
	
	//字段示例值
	String demo() default "";
	
	//字段是否必填
	boolean notNull() default false;
}
