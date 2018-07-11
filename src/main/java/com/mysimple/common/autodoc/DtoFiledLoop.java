package com.mysimple.common.autodoc;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 如果在DTO中某个字段会形成循环，在字段上添加此标签。避免生成文档时形成循环
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DtoFiledLoop {

}
