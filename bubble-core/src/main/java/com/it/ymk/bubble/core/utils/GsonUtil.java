package com.it.ymk.bubble.core.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  处理JSON数据工具类
 */
public class GsonUtil {

    /**
     * Bean转换json
     */
    public static String beanToJson(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    /**
     * @param o          转换对象
     * @param dateFormat 时间格式
     * @return String  返回的json字符串
     */
    public static String beanToJson(Object o, String dateFormat) {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Gson gson = builder.create();
        return gson.toJson(o);
    }

    /**
     * @param o 转换对象
     * @return String  返回的json字符串
     */
    public static String beanHasHtmlToJson(Object o) {
        //这种对象默认对Html进行转义，如果不想转义使用下面的方法
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        Gson gson = builder.create();
        return gson.toJson(o);
    }

    /**
     * json转普通bean
     *
     * @param json 待转换json字符串
     * @return Object 转换后的对象
     */
    public static Object jsonToBean(String json, Class<Object> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    /**
     * json转HashMap
     *
     * @param json 待转换json字符串
     * @return HashMap<String, Object> 转换后的对象
     */
    public static HashMap<String, Object> jsonToMap(String json, TypeToken<HashMap> hashMapTypeToken) {
        Gson gson = new Gson();
        return gson.fromJson(json, hashMapTypeToken.getType());
    }

    /**
     * json 转List
     *
     * @param json 待转换json字符串
     * @return List<Map < String, String>> 转换后的对象
     */
    public static List<Map<String, String>> jsonToList(String json, TypeToken<List> listTypeToken) {
        Gson gson = new Gson();
        return gson.fromJson(json, listTypeToken.getType());
    }

    /**
     * 判断字符串是否是json,通过捕捉的异常来判断是否是json
     *
     * @param json 待转换json字符串
     * @return boolean 是否是json
     * @
     */
    public static boolean jsonOrNot(String json) {
        boolean jsonFlag;
        try {
            new JsonParser().parse(json).getAsJsonObject();
            jsonFlag = true;
        } catch (Exception e) {
            jsonFlag = false;
        }
        return jsonFlag;
    }

    /**
     * @param json         待转换json字符串
     * @param propertyName 属性名称
     * @return String 属性值
     */
    public static String jsonGetProp(String json, String propertyName) {
        String propertyValue;
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(json);
        JsonObject jsonObj = element.getAsJsonObject();
        propertyValue = jsonObj.get(propertyName).toString();
        return propertyValue;
    }

    /**
     * @param json         待转换json字符串
     * @param propertyName 属性名称
     * @return String 转换后的json字符串
     */
    public static String jsonRemoveProp(String json, String propertyName) {
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(json);
        JsonObject jsonObj = element.getAsJsonObject();
        jsonObj.remove(propertyName);
        json = jsonObj.toString();
        return json;
    }

    /**
     * @param json          待转换json字符串
     * @param propertyName  属性名称
     * @param propertyValue 属性值
     * @return String 转换后的json字符串
     */
    public static String jsonAddProp(String json, String propertyName, String propertyValue) {
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(json);
        JsonObject jsonObj = element.getAsJsonObject();
        jsonObj.addProperty(propertyName, new Gson().toJson(propertyValue));
        json = jsonObj.toString();
        return json;
    }

    /**
     * @param json          待转换json字符串
     * @param propertyName  属性名称
     * @param propertyValue 属性值
     * @return String 转换后的json字符串
     */
    public static String jsonModifyProp(String json, String propertyName, String propertyValue) {
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(json);
        JsonObject jsonObj = element.getAsJsonObject();
        jsonObj.remove(propertyName);
        jsonObj.addProperty(propertyName, new Gson().toJson(propertyValue));
        return json;
    }

    /**
     * @param json         待转换json字符串
     * @param propertyName 属性名称
     * @return boolean 属性值是否存在，布尔值
     */
    public static boolean isJsonPropExist(String json, String propertyName) {
        boolean isContains;
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(json);
        JsonObject jsonObj = element.getAsJsonObject();
        isContains = jsonObj.has(propertyName);
        return isContains;
    }
}
