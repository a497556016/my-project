package com.heshaowei.myproj.utils;

import java.lang.reflect.Field;
import java.util.Objects;

public class PropertyCopys {

    public static void copyNotnull(Object target, Object source) {
        String targetClassName = target.getClass().getTypeName();
        String sourceClassName = source.getClass().getTypeName();
        if (targetClassName.contains("$")) {
            targetClassName = targetClassName.substring(0, targetClassName.indexOf("$"));
        }
        if (sourceClassName.contains("$")) {
            sourceClassName = sourceClassName.substring(0, sourceClassName.indexOf("$"));
        }
        if (!targetClassName.equals(sourceClassName)) {
            throw new RuntimeException("必须为相同的类型！");
        }
        Class clz = source.getClass();
        for (Field field : clz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object v = field.get(source);
                if (!Objects.isNull(v)) {
                    field.set(target, v);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
