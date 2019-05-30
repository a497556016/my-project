package com.heshaowei.myproj.base;

import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.data.util.ReflectionUtils;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.List;

public class MyConverter<A extends MyConverter, B> extends Converter<A, B> {

    private Class<?> getType(int index) {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();//获取当前new对象的泛型的父类类型
        Class<?> type = (Class<?>) parameterizedType.getActualTypeArguments()[index];
        return type;
    }

    private <T> T newInstance(Class<T> clz) {
        try {
            return clz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object getValueByProperty(Object obj, String propertyName) {
        Method readMethod = BeanUtils.getPropertyDescriptor(obj.getClass(), propertyName).getReadMethod();
        try {
            return readMethod.invoke(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setValueByProperty(Object obj, String propertyName, Object ...args) {
        Method writeMethod = BeanUtils.getPropertyDescriptor(obj.getClass(), propertyName).getWriteMethod();
        try {
            writeMethod.invoke(obj, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private boolean isDTO(Field field){
        return MyConverter.class.equals(field.getType().getSuperclass());
    }

    private Class isListDTO(Field field){
        if(field.getType().isAssignableFrom(List.class)) {
            Type type = field.getGenericType();
            //得到泛型类型的类名
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] types = parameterizedType.getActualTypeArguments();

//            ParameterizedType pt = (ParameterizedType) field.getType().getGenericSuperclass();
//            Type[] types = pt.getActualTypeArguments();
            //判断List泛型的父类是MyConverter
            Class typeClz = (Class)types[0];
            Class superClz = typeClz.getSuperclass();
            if(superClz.equals(MyConverter.class)) {
                return typeClz;
            }
        }
        return null;
    }

    @Override
    protected B doForward(A a) {
        Class<B> clz = (Class<B>) getType(1);
        B b = newInstance(clz);

        this.copy(a, b, a.getClass(), false);
        return b;
    }

    @Override
    protected A doBackward(B b) {
        Class<A> clz = (Class<A>) getType(0);
        A a = newInstance(clz);

        this.copy(b, a, clz, false);
        return a;
    }

    private void copy(Object source, Object target, Class dtoClz, boolean ignoreDtoField){
        boolean sourceDto = true;
        Class dtoClass = source.getClass();
        if(target.getClass().equals(dtoClz)) {
            dtoClass = target.getClass();
            sourceDto = false;
        }
        for (Field field : dtoClass.getDeclaredFields()) {
            if(isDTO(field)) {
                if(!ignoreDtoField) {
                    Object v = this.getValueByProperty(source, field.getName());
                    if(null != v) {
                        //目标的类型
                        Class targetClz = BeanUtils.getPropertyDescriptor(target.getClass(), field.getName()).getPropertyType();
                        Object t = newInstance(targetClz);
                        this.copy(v, t, sourceDto ? v.getClass() : t.getClass(), true);
                        this.setValueByProperty(target, field.getName(), t);
                    }
                }
            }else {
                Class typeClz = isListDTO(field);
                if(null != typeClz) {
                    if(!ignoreDtoField) {
                        List<?> list = (List<?>) this.getValueByProperty(source, field.getName());
                        if (null != list && !list.isEmpty()) {
                            List<Object> newList = Lists.newArrayList();
                            for (Object v : list) {
                                Object t = newInstance(typeClz);
                                this.copy(v, t, sourceDto ? v.getClass() : t.getClass(), true);
                                newList.add(t);
                            }

                            this.setValueByProperty(target, field.getName(), newList);
                        }
                    }
                }else {
                    Object value = this.getValueByProperty(source, field.getName());
                    this.setValueByProperty(target, field.getName(), value);
                }
            }
        }
    }
}
