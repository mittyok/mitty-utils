package com.mitty.common.annotation.processor;

import com.mitty.common.annotation.MapperAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 对象映射处理器
 *
 * Created by PILIANG on 2015/12/26.
 */
public class MapperAnnotationProcessor {

//    private static final String ROOT = "ROOT_OBJECT";
    Map<String,Object> valuesCache = new HashMap();
    Map resultMap = new HashMap();
    private Class<? extends Annotation> mapperAnnotationClass;

    public MapperAnnotationProcessor(Class<? extends Annotation> mapperAnnotationClass) {

        if ((mapperAnnotationClass != MapperAnnotation.class) && !mapperAnnotationClass.isAnnotationPresent(MapperAnnotation.class)) {
            throw new RuntimeException("指定的注解必须有" + MapperAnnotation.class.getName() + "标识！");
        }

        this.mapperAnnotationClass = mapperAnnotationClass;
    }

    public Object process(Object srcObject) {
        return process(srcObject, true);
    }

    public Object process(Object srcObject, boolean isRootObject) {

        MapperAnnotationProcessor processor = this;

        if (srcObject == null)
            return null;

        boolean isNotList = !(srcObject instanceof List);
        boolean isMapObject = srcObject instanceof Map;

        boolean hasMapperAnnotation = hasMapperAnnotation(srcObject);

        if (!hasMapperAnnotation && isNotList) { // 普通单个值得处理
            return srcObject;
        }

        if (!isRootObject && hasMapperAnnotation) { // 说明不是根，是对象内部的属性，此属性标明要进行映射，则需要使用心得处理器进行处理
            processor = new MapperAnnotationProcessor(mapperAnnotationClass);
            return processor.process(srcObject);
        }

        if (!isNotList) { // 是列表

            List newList = new ArrayList();

            processor = new MapperAnnotationProcessor(mapperAnnotationClass);

            // 遍历所有对象
            List dataList = (List)srcObject;
            for (Object data :
                    dataList) {
                newList.add(processor.process(data));
            }
            return newList;
        }

        if (isMapObject) {
            // 如果是一个MAP，我们必须要当根处理
            if (!isRootObject) {
                processor = new MapperAnnotationProcessor(mapperAnnotationClass);
                return processor.process(srcObject);
            } else {
                Map map = (Map)srcObject;
                Set keys = map.keySet();
                for (Object key : keys) {
                    resultMap.put(key, process(map.get(key), false));
                }
                return resultMap;
            }



        }


        // TODO: 2015/12/26 后续这个处理应该做缓存
        // 获取对应的注解，交给对应的处理器处理
        Field[] fields = srcObject.getClass().getFields();
        for (Field field:
                fields) {
            // 如果有注解，交给注解处理器处理
            Annotation  mapperAnnotation = field.getAnnotation(mapperAnnotationClass);
            if (mapperAnnotation == null)
                mapperAnnotation = field.getAnnotation(MapperAnnotation.class);
            // 如果没有需要映射的注解，则不考虑了
            if (mapperAnnotation == null)
                continue;

            /**
             * 如果查询到了注解，则交给处理器处理
             */
            // 获取对应的Key
            String key = null;
            try {
                key = (String) mapperAnnotation.annotationType().getMethod("value").invoke(mapperAnnotation);
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // TODO 2015/12/26 异常未作处理
            } catch (InvocationTargetException e) {
                e.printStackTrace(); // TODO 2015/12/26 异常未作处理
            } catch (NoSuchMethodException e) {
                e.printStackTrace(); // TODO 2015/12/26 异常未作处理
            }


            // 取得对应字段的值
            Object fieldValue = null;
            try {
                fieldValue = field.get(srcObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // TODO 2015/12/26 异常未作处理
            }
            putValue(key, fieldValue);

        }

        return resultMap;
    }

    private boolean hasMapperAnnotation(Object object) {
        return object.getClass().getAnnotation(mapperAnnotationClass) != null || object.getClass().getAnnotation(MapperAnnotation.class) != null;
    }

    private void putValue(String key, Object fieldValue) {

        Object processObj = process(fieldValue, false);
        if (key.indexOf('.') == -1) {
            resultMap.put(key, processObj);
        } else {


            Object parent = getValue(key.substring(0, key.lastIndexOf('.')));
            if (parent instanceof List) {
                List list = (List) parent;
                list.add(processObj);
            }
            Map map = (Map) parent;

            map.put(key.substring(key.lastIndexOf('.') + 1), processObj);
        }

        valuesCache.put(key, processObj);

    }

    /**
     * 通过key获取对应的值
     *
     * @param key
     * @return
     */
    private Object getValue(String key) {

        if (valuesCache.get(key) == null) {
            putValue(key, new HashMap());
        }

        return valuesCache.get(key);
    }
}
