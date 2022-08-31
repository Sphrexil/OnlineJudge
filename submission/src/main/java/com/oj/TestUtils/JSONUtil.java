package com.oj.TestUtils;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zzy
 */
public class JSONUtil {
    private static final Logger log = LoggerFactory.getLogger(JSONUtil.class);

    public JSONUtil() {
    }

    public static String toJSONString(Object data) {
        if (data == null) {
            return "";
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(Include.NON_NULL);
            objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
            objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);

            try {
                return objectMapper.writeValueAsString(data);
            } catch (JsonProcessingException var3) {
                log.error("JSONUtil.toJSONString.error", var3);
                return "";
            }
        }
    }

    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        if (jsonStr == null || "".equals(jsonStr)) {
            return null;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                return objectMapper.readValue(jsonStr, clazz);
            } catch (JsonProcessingException var4) {
                log.error("JSONUtil.parseObject.error", var4);
                return null;
            }
        }
    }

    public static <T> T parseObject(String jsonStr, TypeReference<T> valueTypeRef) {
        if (jsonStr == null || "".equals(jsonStr)) {
            return null;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                return objectMapper.readValue(jsonStr, valueTypeRef);
            } catch (Exception var4) {
                log.error("JSONUtil.parseObject.TypeReference.error", var4);
                return null;
            }
        }
    }

    public static <T> T parseSnakeObject(String json, Class<T> clazz) {
        if (json == null || "".equals(json)) {
            return null;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

            try {
                return objectMapper.readValue(json, clazz);
            } catch (JsonProcessingException var4) {
                log.error("JSONUtil.parseSnakeObject.error", var4);
                return null;
            }
        }
    }
}
