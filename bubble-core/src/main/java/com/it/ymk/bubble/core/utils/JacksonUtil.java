package com.it.ymk.bubble.core.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

/**
 *  处理JSON数据工具类
 *
 */
public class JacksonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
    }

    private static final JsonFactory JSONFACTORY = new JsonFactory();

    /**
     * 转换Java Bean 为 json
     */
    public static String beanToJson(Object o) throws Exception {
        if (o == null) {
            return null;
        }

        StringWriter sw = new StringWriter();
        JsonGenerator jsonGenerator = null;

        try {
            jsonGenerator = JSONFACTORY.createJsonGenerator(sw);
            MAPPER.writeValue(jsonGenerator, o);
            return sw.toString();

        } catch (Exception e) {
            throw new Exception("转换Java Bean 为 json错误");

        } finally {
            if (jsonGenerator != null) {
                try {
                    jsonGenerator.close();
                } catch (Exception e) {
                    //                    throw new BaseException("转换Java Bean 为 json错误");
                }
            }
        }
    }

    /**
     * json 转 javabean
     *
     * @param json
     * @return
     */
    public static Object jsonToBean(String json, Class clazz) throws Exception {
        if (null == json || clazz == null) {
            return null;
        }

        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new Exception("json 转 javabean错误");
        }
    }

    /**
     * 转换Java Bean 为 HashMap
     */
    public static Map<String, Object> beanToMap(Object o) throws Exception {
        if (o == null) {
            return null;
        }

        try {
            return MAPPER.readValue(beanToJson(o), HashMap.class);
        } catch (Exception e) {
            throw new Exception("转换Java Bean 为 HashMap错误");
        }
    }

    /**
     * 转换Json String 为 HashMap
     */
    public static HashMap<String, Object> jsonToMap(String json, boolean collToString) throws Exception {
        if (null == json) {
            return null;
        }

        HashMap<String, Object> map = null;
        try {
            map = MAPPER.readValue(json, HashMap.class);
        } catch (IOException e) {
            throw new Exception("转换Java Bean 为 HashMap错误");
        }
        if (collToString) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof Collection || entry.getValue() instanceof Map) {
                    entry.setValue(beanToJson(entry.getValue()));
                }
            }
        }
        return map;
    }

    /**
     * @param json
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> jsonToMap(String json) throws Exception {
        if (null == json) {
            return null;
        }

        HashMap<String, Object> map = null;
        try {
            map = MAPPER.readValue(json, HashMap.class);
        } catch (IOException e) {
            throw new Exception("转换Java Bean 为 HashMap错误");
        }
        return map;
    }

    /**
     * 获取json串内属性值
     *
     * @param json
     * @param key
     * @return
     */
    public static Object getPropertyInJson(String json, String key) throws Exception {
        if (null == json || null == key) {
            return null;
        }

        HashMap<String, Object> map = jsonToMap(json);
        return map.get(key);
    }

    /**
     * 获取json串内属性值
     * @param json
     * @param nodePath   获取属性值KEY路径 如:xx/xx/xx
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> getPropertyInJsonByPath(String json, String nodePath)
        throws Exception {
        if (null == json || null == nodePath) {
            return null;
        }
        HashMap<String, Object> map = jsonToMap(json);

        if (null == map) {
            return null;
        }
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        String[] nodes = nodePath.split("/");
        int i = 0;
        for (String node : nodes) {
            if (i == 0) {
                returnMap = (HashMap<String, Object>) map.get(node);
            }
            else {
                returnMap = (HashMap<String, Object>) returnMap.get(node);
            }
            i++;
            if (null == returnMap || returnMap.isEmpty()) {
                continue;
            }

        }
        return returnMap;
    }

    /**
     * 修改或添加json串内属性值
     *
     * @param json
     * @param key
     * @return
     */
    public static String setPropertyInJson(String json, String key, Object value) throws Exception {
        if (null == key || value == null) {
            return json;
        }
        HashMap<String, Object> map = null;
        if (null == json) {
            map = new HashMap<String, Object>();
        }
        else {
            map = jsonToMap(json);
        }
        map.put(key, value);
        return beanToJson(map);
    }

    /**
     * List 转换成json
     *
     * @param list
     * @return
     */
    public static String listToJson(List<Map<String, String>> list) throws Exception {
        if (null == list) {
            return null;
        }

        JsonGenerator jsonGenerator = null;
        StringWriter sw = new StringWriter();
        try {
            jsonGenerator = JSONFACTORY.createJsonGenerator(sw);
            new ObjectMapper().writeValue(jsonGenerator, list);
            jsonGenerator.flush();
            return sw.toString();
        } catch (Exception e) {
            throw new Exception("List 转换成json错误");
        } finally {
            if (jsonGenerator != null) {
                try {
                    jsonGenerator.flush();
                    jsonGenerator.close();
                } catch (Exception e) {
                    //                    throw new BaseException("List 转换成json错误");
                }
            }
        }
    }

    /**
     * json 转List
     *
     * @param json
     * @return
     */
    public static List<Map<String, String>> jsonToList(String json) throws Exception {
        if (null == json) {
            return null;
        }

        try {
            if (json != null && !"".equals(json.trim())) {
                JsonParser jsonParse = JSONFACTORY.createJsonParser(new StringReader(json));

                ArrayList<Map<String, String>> arrayList = (ArrayList<Map<String, String>>) new ObjectMapper()
                    .readValue(jsonParse, ArrayList.class);
                return arrayList;
            }
            else {
                throw new Exception("json 转List错误");
            }
        } catch (Exception e) {
            throw new Exception("json 转List错误");
        }
    }
}
