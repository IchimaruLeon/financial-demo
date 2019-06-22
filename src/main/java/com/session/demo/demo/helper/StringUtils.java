package com.session.demo.demo.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringUtils {

    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

    public static String convertObjToStringValues(Object object){
        String values = null;
        try {
            values = OBJ_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("failed to parse object to String : {} with error : {}", object, e.getMessage());
        }
        return values;
    }
}
