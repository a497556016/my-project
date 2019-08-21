package com.heshaowei.myproj.base;

import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.data.util.ReflectionUtils;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyConverter<A extends MyConverter, B> extends Converter<A, B> {

    public Class<?> getMyConverterClass(Class<?> calzz){
        Class<?> superclass = calzz.getSuperclass();
        while (superclass != null) {
            if(superclass.getName().equals("java.lang.Object")){
                break;
            }
            if(superclass.getName().equals("com.heshaowei.myproj.base.MyConverter")) {
                return calzz;
            }
            calzz = superclass;
            superclass = superclass.getSuperclass();
        }
        return null;
    }

    private Class<?> getEntityType(Class clz) {
        Class<?> classes = getMyConverterClass(clz);

        if(null == classes) {
            return null;
        }

        ParameterizedType parameterizedType = (ParameterizedType) classes.getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        if(types.length > 1) {
            if(types[1] instanceof Class) {
                return (Class<?>) types[1];
            }
        }

        return null;
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
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(obj.getClass(), propertyName);
        if(null == propertyDescriptor) {
            return null;
        }
        Method readMethod = propertyDescriptor.getReadMethod();
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
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(obj.getClass(), propertyName);
        if(null == propertyDescriptor) {
            return;
        }
        Method writeMethod = propertyDescriptor.getWriteMethod();
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

    private Class isListDTO(Field field, boolean sourceDto){
        if(field.getType().isAssignableFrom(List.class)) {
            Type type = field.getGenericType();
            //得到泛型类型的类名
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] types = parameterizedType.getActualTypeArguments();

//            ParameterizedType pt = (ParameterizedType) field.getType().getGenericSuperclass();
//            Type[] types = pt.getActualTypeArguments();
            //判断List泛型的父类是MyConverter

            Class typeClz = (Class)types[0];

            //如果是DTO转Entity，获取目标实体的类型
            if(sourceDto){
                return getEntityType(typeClz);
            }else {
                //如果是entity转DTO，获取DTO类型，即当前集合的泛型
                if(null != getMyConverterClass(typeClz)){
                    return typeClz;
                }
            }

        }
        return null;
    }

    @Override
    protected B doForward(A a) {
        Class<B> clz = (Class<B>) getEntityType(this.getClass());

        if(null == clz) {
            return null;
        }

        B b = newInstance(clz);

        this.copy(a, b, a.getClass(), false, 0);
        return b;
    }

    @Override
    protected A doBackward(B b) {
        Class<A> clz = (Class<A>) this.getClass();

        if(null == clz) {
            return null;
        }

        A a = newInstance(clz);

        this.copy(b, a, clz, false, 0);
        return a;
    }

    private void copy(Object source, Object target, Class dtoClz, boolean ignoreDtoField, int deep){
        boolean sourceDto = true;
        Class dtoClass = source.getClass();
        if(target.getClass().equals(dtoClz)) {
            dtoClass = target.getClass();
            sourceDto = false;
        }
        List<Field> fields = Lists.newArrayList(dtoClass.getDeclaredFields());
        Class superClz = dtoClass.getSuperclass();
        while(null != superClz && !superClz.equals(MyConverter.class)){
            fields.addAll(Lists.newArrayList(superClz.getDeclaredFields()));
            superClz = superClz.getSuperclass();
        }


        for (Field field : fields) {
            if(isDTO(field)) {
                if(!ignoreDtoField) {
                    Object v = this.getValueByProperty(source, field.getName());
                    if(null != v) {
                        //目标的类型
                        Class targetClz = BeanUtils.getPropertyDescriptor(target.getClass(), field.getName()).getPropertyType();
                        Object t = newInstance(targetClz);
                        if(deep < 1) {
                            this.copy(v, t, sourceDto ? v.getClass() : t.getClass(), true, deep + 1);
                        }
                        this.setValueByProperty(target, field.getName(), t);
                    }
                }
            }else {
                Class typeClz = isListDTO(field, sourceDto);
                if(null != typeClz) {
                    if(!ignoreDtoField) {
                        List<?> list = (List<?>) this.getValueByProperty(source, field.getName());
                        if (null != list && !list.isEmpty()) {
                            List<Object> newList = Lists.newArrayList();
                            for (Object v : list) {
                                Object t = newInstance(typeClz);
                                if(deep < 1) {
                                    this.copy(v, t, sourceDto ? v.getClass() : t.getClass(), true, deep + 1);
                                }
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
