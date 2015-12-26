package com.mitty.common.utils;

import com.mitty.common.annotation.MapperAnnotation;
import com.mitty.common.annotation.processor.MapperAnnotationProcessor;

import java.lang.annotation.Annotation;

/**
 * 处理对象映射
 *
 * Created by PILIANG on 2015/12/26.
 */

public class MapperUtils {

    /**
     * 通过源对象获取一个MAP
     *
     * @param srcObject
     *      源对象
     * @param mapperAnnotationClass
     *      映射的注解类
     * @return
     */
    public static Object getResultMap(Object srcObject, Class<? extends Annotation> mapperAnnotationClass) {

        MapperAnnotationProcessor mapperAnnotationProcessor = new MapperAnnotationProcessor(mapperAnnotationClass);

        return mapperAnnotationProcessor.process(srcObject);
    }

    public static Object getResultMap(Object srcObject) {

        return getResultMap(srcObject, MapperAnnotation.class);

    }
}
