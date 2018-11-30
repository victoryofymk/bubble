package com.it.ymk.bubble.core.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author yanmingkun
 * @date 2018-11-30 17:38
 */
public class CollectionUtil {
    public static List<Object> convert(Object[] strArray) {
        ArrayList<Object> arrayList = new ArrayList<Object>(strArray.length);
        Collections.addAll(arrayList, strArray);
        return arrayList;
    }
}
