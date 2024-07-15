package com.shopping.cart.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.cart.exception.JsonParseException;

public class JsonUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        }catch (JsonProcessingException ex){
            throw new JsonParseException(ex.getMessage(),ex.getCause().getMessage());
        }
    }

    public static Object toJsonObject(Object object) {
        return objectMapper.convertValue(object,Object.class);
    }

    public static <T> T fromJsonObject(Object json, Class<T> valueType){
        return objectMapper.convertValue(json, valueType);
    }

    public static <T> T fromJson(String json, Class<T> valueType){
        try {
            return objectMapper.readValue(json, valueType);
        }catch (JsonProcessingException ex){
            throw new JsonParseException(ex.getMessage(),ex.getCause().getMessage());
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> valueTypeRef){
        try {
            return objectMapper.readValue(json, valueTypeRef);
        }catch (JsonProcessingException ex){
            throw new JsonParseException(ex.getMessage(),ex.getCause().getMessage());
        }
    }
}

