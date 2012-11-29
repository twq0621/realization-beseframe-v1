package com.realization.framework.messaging.message.utils;


public class ArrayUtils {

    public static boolean contains(String[] keys, String name) {
        for (String s: keys) {
            if (null!=s && s.equals(name)) return true;
        }
        return false;
    }

    public static Object[] addFirst(Object[] array, Object obj) {
        Object[] results = new Object[array.length+1];
        results[0] = obj;
        System.arraycopy(array, 0, results, 1, array.length);
        return results;
    }

}