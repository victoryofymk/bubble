package com.it.ymk.bubble.core.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  bean工具类
 *
 * @author Yanmingkun
 * @version $v:1.0, $time:2017-08-01, $id:BeanUtil.java, Exp $
 */
public class BeanUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtil.class);

    /**
     *
     * @param resultDo
     */
    public static String[] voHelper(Object resultDo, Class voclass) {
        String[] excel = new String[voclass.getDeclaredFields().length - 1];
        Field[] fields = voclass.getDeclaredFields();
        voclass.getDeclaredMethods();
        for (int i = 0; i < fields.length; i++) {
            Method method;
            Object o = null;
            try {
                if (fields[i].getName() == "serialVersionUID") {
                    continue;
                }
                method = voclass.getMethod("get" + StringUtil.UpStr(fields[i].getName()), (Class<?>[]) null);
                o = method.invoke(resultDo);
                if (o != null) {
                    if (o.getClass() == String.class) {
                        excel[i - 1] = (String) o;
                    }
                    else if (o.getClass() == Date.class) {
                        excel[i - 1] = DateUtil.dateToDateString((Date) o, DateUtil.YYYY_MM_DD_HH_MM_SS_EN);
                    }
                    else if (o.getClass() == java.math.BigDecimal.class) {
                        excel[i - 1] = o.toString();
                    }
                    else {
                        excel[i - 1] = o.toString();
                    }
                }
                else {
                    excel[i - 1] = "";
                }

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return excel;
    }

    /**
     * 获取动态map
     *
     * @param className 类名
     * @param object 对象
     * @return Map<String, Object> 对象
     */
    public static Map<String, Object> convertToMap(Class className, Object object) {
        Map<String, Object> dynamicQueryFields = Collections.EMPTY_MAP;
        try {
            BeanInfo bi = Introspector.getBeanInfo(className);
            PropertyDescriptor[] pd = bi.getPropertyDescriptors();
            for (int i = 0; i < pd.length; i++) {
                String name = pd[i].getName();
                Method method = pd[i].getReadMethod();
                Object value = method.invoke(object);
                if (!"class".equals(name)) {
                    dynamicQueryFields.put(name, value);
                }
            }
        } catch (IntrospectionException ie) {
            LOGGER.error("className=" + className + "BeanInfo error : ", ie);
        } catch (Exception e) {
            LOGGER.error("className=" + className + "Method error :", e);
        }
        return dynamicQueryFields;
    }

    public static void main(String[] args) {
        /*ReceiveMailVO receiveMailVO = new ReceiveMailVO();
        receiveMailVO.setAttachmentId("123");
        String[] excel = BeanUtil.voHelper(receiveMailVO, ReceiveMailVO.class);
        System.out.println(excel.length);*/
    }
}