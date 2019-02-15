package com.mark.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private BeanUtil() {}
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
                    /*if (value.equals("null")) {
                        value = "";
                    } else if (value.equals("true")) {
                        value = "1";
                    } else if (value.equals("false")) {
                        value = "0";
                    }*/
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

    public static <T, K, V> T mapToBean(Map<K, V> map, Class<T> pojoClass) {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, pojoClass);
    }
    public static <T> Map<String, String> beanToMap(Object obj, Class<T> clazz) {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue((T)obj, Map.class);
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

    /**
     * 将Json字符串信息转换成对应的Java对象
     *
     * @param json json字符串对象
     * @param c    对应的类型
     */
    public static <T> T parseJsonToObj(String json, Class<T> c) {
        try {
            //两个都是可行的，起码我测试的时候是没问题的。
            //JSONObject jsonObject = JSONObject.parseObject(json);
            JSONObject jsonObject = JSON.parseObject(json);
            return JSON.toJavaObject(jsonObject, c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 把Java对象转换成json字符串
     *
     * @param object 待转化为JSON字符串的Java对象
     * @return json 串 or null
     */
    public static String parseObjToJson(Object object) {
        String string = null;
        try {
            //string = JSON.toJSONString(object);
            string = JSONObject.toJSONString(object);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return string;
    }
}
