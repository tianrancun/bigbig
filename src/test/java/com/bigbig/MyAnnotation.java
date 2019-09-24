package com.bigbig;

import java.lang.annotation.*;

/**
 * 自定义注解
 * @interface
 * @Target 作用目标
 * ElementType.TYPE Class, interface (including annotation type), or enum declaration 类,接口 注解,枚举
 * ElementType.FIELD Field declaration (includes enum constants) 字段 ,枚举的常量
 * ElementType.METHOD
 * @Retention 作用策略
 * @Inherited 注解继承 @Target(ElementType.TYPE)才可以使用，对其他无效
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MyAnnotation {


}