package com.mark.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {
    public static <T, K, V> T map2bean(Map<K, V> map, Class<T> pojoClass) throws IntrospectionException, IllegalAccessException, InstantiationException {
        T t = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(pojoClass, Object.class);
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            // VproCoursesLessonList
            t = pojoClass.newInstance();
            for (PropertyDescriptor propertyDescriptor : descriptors) {
                String key = propertyDescriptor.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 内部提供的访问属性的方法
                    Method setter = propertyDescriptor.getWriteMethod();
                    setter.invoke(t, value);
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static Map<String, String> bean2map(Object rs) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(rs.getClass());
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        Map<String, String> map = new HashMap<String, String>();
        Arrays.stream(descriptors).forEach(x -> {
            if (!x.getName().equals("class")) {
                try {
                    Method getter = x.getReadMethod();
                    String value = String.valueOf(getter.invoke(rs));
                    if (value.equals("null")) {
                        value = "";
                    } else if (value.equals("true")) {
                        value = "1";
                    } else if (value.equals("false")) {
                        value = "0";
                    }
                    map.put(String.valueOf(x.getName()), value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        return map;
    }
    public static Map<String, Object> bean2map4criteria(Object obj) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        Map<String, Object> map = new HashMap<String, Object>();
        Arrays.stream(descriptors).forEach(x -> {
            if (!x.getName().equals("class") && x.getPropertyType().toString().contains("java")) {
                try {
                    Method getter = x.getReadMethod();
                    String value = String.valueOf(getter.invoke(obj));
                    if (!value.equals("null") && !value.equals("") && !value.equals("-1")) map.put(String.valueOf(x.getName()), value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        return map;
    }
}
