package com.mitty.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by PILIANG on 2015/12/26.
 */
public class JSONUtils {


    public static String format(Object object) {

        if (object == null)
            return null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // TODO 2015/12/26 异常未作处理
        }
        return object.toString();
    }
}
