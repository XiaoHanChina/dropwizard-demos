package com.hanxs.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> T snakeJsonToJavaBean(String json, Class<T> clazz) throws IOException {
        if (json == null) return null;
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return MAPPER.readValue(json, clazz);
    }

    public static <T> T snakeJsonToJavaBean(String json, TypeReference<T> type) throws IOException {
        if (json == null) return null;
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return MAPPER.readValue(json, type);
    }

    /**
     * 将一些统计类的json，转成map
     *
     * @param fieldName json中分类字段的名字
     * @param json      json内容，需要有一个分类字段、一个count字段
     * @return 返回整理好的不同状态的数值
     */
    public static Map<Integer, Integer> generateCountGroupByInt(String fieldName, String json) throws IOException {
        ArrayNode arr = (ArrayNode) new ObjectMapper().readTree(json);
        HashMap<Integer, Integer> result = new HashMap<>();
        for (JsonNode j : arr) {
            result.put(j.get(fieldName).asInt(), j.get("count").asInt());
        }
        return result;
    }
}
