package com.it.ymk.bubble.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.*;

/**
 *  处理JSON数据工具类
 * <p>
 * Fastjson的SerializerFeature序列化属性
 * <p>
 * QuoteFieldNames———-输出key时是否使用双引号,默认为true
 * WriteMapNullValue——–是否输出值为null的字段,默认为false
 * WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null
 * WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
 * WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null
 * WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
 *
 * @author yanmingkun
 */
public class FastJsonUtil {

    /**
     * Bean转换json，默认格式化
     */
    public static String beanToJson(Object o) {
        return JSON.toJSONString(o, true);
    }

    /**
     * Bean转换json,是否格式化
     */
    public static String beanToJson(Object o, boolean format) {
        return JSON.toJSONString(o, false);
    }

    /**
     * @param o          转换对象
     * @param dateFormat 时间格式
     * @return
     * @
     */
    public static String beanToJson(Object o, String dateFormat) {
        return JSON.toJSONStringWithDateFormat(o, dateFormat);
    }

    /**
     * json转普通bean
     *
     * @param json 待转换json字符串
     * @return Object 转换后的对象
     */
    public static Object jsonToBean(String json, Class<Object> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * json转HashMap
     *
     * @param json 待转换json字符串
     * @return HashMap<String, Object 转换后的对象
            * @
     */
    public static HashMap<String, Object> jsonToMap(String json, TypeReference<HashMap> typeReference) {
        return JSON.parseObject(json, typeReference);
    }

    /**
     * json 转List
     *
     * @param json 待转换json字符串
     * @return List<Map < String, String>> 转换后的对象
     */
    public static List<Map<String, String>> jsonToList(String json, TypeReference<ArrayList> typeReference) {
        return JSON.parseObject(json, typeReference);
    }

    /**
     * @param json         待转换json字符串
     * @param propertyName 属性名称
     * @return String 返回的json字符串
     */
    public static String jsonRemoveProp(String json, String propertyName) {
        JSONObject obj = JSON.parseObject(json);
        Set set = obj.keySet();
        set.remove(propertyName);
        json = obj.toString();
        return json;
    }

    /**
     * @param json          待转换json字符串
     * @param propertyName  属性名称
     * @param propertyValue 属性值
     * @return String 返回的json字符串
     */
    public static String jsonAddProp(String json, String propertyName, String propertyValue) {
        JSONObject obj = JSON.parseObject(json);
        obj.put(propertyName, propertyValue);
        json = obj.toString();
        return json;
    }

    /**
     * @param json          待转换json字符串
     * @param propertyName  属性名称
     * @param propertyValue 属性值
     * @return String 返回的json字符串
     */
    public static String jsonModifyProp(String json, String propertyName, String propertyValue) {
        JSONObject obj = JSON.parseObject(json);
        Set set = obj.keySet();
        if (set.contains(propertyName)) {
            obj.put(propertyName, JSON.toJSONString(propertyValue));
        }
        json = obj.toString();
        return json;
    }

    /**
     * @param json         待转换json字符串
     * @param propertyName 属性名称
     * @return boolean 属性值是否存在，布尔值
     */
    public static boolean isJsonPropExist(String json, String propertyName) {
        boolean isContain;
        JSONObject obj = JSON.parseObject(json);
        Set set = obj.keySet();
        isContain = set.contains(propertyName);
        return isContain;
    }

}
