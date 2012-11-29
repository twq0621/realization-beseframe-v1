package com.realization.framework.messaging.message.utils;

import java.lang.reflect.Field;
import java.util.Map;


public class PropertyUtils {

    public static Object getProperty(Object bean, String propName) {
        if (null==bean) return null;
        if (bean instanceof Map<?,?>) return ((Map<?,?>)bean).get(propName);
        try {
            Field f = bean.getClass().getDeclaredField(propName);
            if (!f.isAccessible()) f.setAccessible(true);
            return f.get(bean);
        }
        catch(Exception e) {}
        return null;
    }
    public static void setProperty(Object bean, String propName, Object value) {
        if (null==bean) return;
        if (bean instanceof Map<?,?>) {
            @SuppressWarnings("unchecked")
            Map<String,Object> m = (Map<String,Object>)bean;
            m.put(propName, value);
        }
        else try {
            Field f = bean.getClass().getDeclaredField(propName);
            if (!f.isAccessible()) f.setAccessible(true);
            f.set(bean,value);
        }
        catch(Exception e) {}
    }
    public static void copyProperties(Object src, Object dist, String... propNames) {
        for (String pn: propNames) {
            setProperty(dist,pn,getProperty(src,pn));
        }
    }
}
