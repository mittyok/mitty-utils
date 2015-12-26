package com.mitty.common.annotation;

import com.mitty.common.annotation.MapperAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解对应的映射
 *
 * Created by PILIANG on 2015/12/26.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@MapperAnnotation
public @interface MyMapperAnnotation {

    String value();

}
