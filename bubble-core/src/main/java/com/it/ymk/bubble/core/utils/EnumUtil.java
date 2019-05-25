package com.it.ymk.bubble.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanmingkun
 * @version 1.0
 * @Title
 * @date 2016/12/26-22:33
 */
public class EnumUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnumUtil.class);

    /**
     * 枚举帮助类，获取list合集，下拉框使用
     *
     * @param className
     * @return
     */
    public static List<Map<String, String>> getEnumMap(String className) throws Exception {
        return getEnumMap(Class.forName(className));
    }

    /**
     * 枚举帮助类，获取list合集，下拉框使用
     *
     * @param enumClass
     * @return
     */
    public static List<Map<String, String>> getEnumMap(Class enumClass) {
        return getEnumMap(enumClass, "getCode", "getDesc");
    }

    /**
     * 枚举帮助类，获取list合集，下拉框使用
     *
     * @param enumClass m枚举类
     * @param keyMethod 枚举类取得key的方法名
     * @param valueMethod 枚举类取得value的方法名
     * @return
     */
    public static List<Map<String, String>> getEnumMap(Class enumClass, String keyMethod, String valueMethod) {
        return getEnumMap(enumClass, "getCode", "getDesc", "id", "text");
    }

    /**
     * 枚举帮助类，获取list合集，下拉框使用
     *
     * @param enumClass
     * @return
     */
    public static List<Map<String, String>> getEnumMap(Class enumClass, String keyMethod, String valueMethod, String id,
        String text) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            Method toCode = enumClass.getMethod(keyMethod);
            Method toDesc = enumClass.getMethod(valueMethod);
            //得到enum的所有实例
            Object[] objs = enumClass.getEnumConstants();
            for (Object obj : objs) {
                Map<String, String> map = new HashMap<>();
                map.put(id, (String) toCode.invoke(obj));
                map.put(text, (String) toDesc.invoke(obj));
                list.add(map);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 枚举帮助类，grid列表使用
     *
     * @param className 类名，包含包名
     * @return ComplaintItemAbnorDo 到账异常类投诉条目表业务实体
     * @throws Exception   异常
     */
    public Map<String, String> findEnumMap(String className) throws Exception {
        return findEnumMap(className, "getCode", "getDesc");
    }

    /**
     * 枚举帮助类，grid列表使用
     *
     * @param className 类名，包含包名
     * @return ComplaintItemAbnorDo 到账异常类投诉条目表业务实体
     * @throws Exception   异常
     */
    public Map<String, String> findEnumMap(String className, String keyMethod, String valueMethod) throws Exception {
        Class<?> enumClass = Class.forName(className);
        Method toCode = enumClass.getMethod(keyMethod);
        Method toName = enumClass.getMethod(valueMethod);
        //得到enum的所有实例
        Object[] objs = enumClass.getEnumConstants();
        Map<String, String> map = new HashMap<String, String>();
        for (Object obj : objs) {
            map.put((String) toCode.invoke(obj), (String) toName.invoke(obj));
        }
        return map;
    }

    /**
     * 获取所有枚举
     *
     * @param path
     * @param packageName
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getAllEnum(String path, String packageName) throws Exception {
        File dicFile = new File(path);
        /*if(!dicFile.isDirectory()){
            throw new Exception("不是路经");
        }*/
        Package enumPackage = Package.getPackage(packageName);
        //        enumPackage.
        File[] list = dicFile.listFiles();
        /*for (File file : list) {
            String name=file.getName();
        }*/
        List<Map<String, String>> mapList = new ArrayList<>();
        List<Class<?>> classList = ClassUtil.getClasses(packageName);
        for (Class<?> enumClass : classList) {
            mapList.addAll(EnumUtil.getEnumMap(enumClass));
        }
        return mapList;
    }


    public static void main(String[] args) throws Exception {
        EnumUtil.getAllEnum("", "com.it.ymk.core.common.reflect.constenum");
    }
}
