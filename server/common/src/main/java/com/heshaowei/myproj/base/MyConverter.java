package com.heshaowei.myproj.base;

import com.google.common.base.Converter;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.ParameterizedType;

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

    @Override
    protected B doForward(A a) {
        Class<B> clz = (Class<B>) getType(1);
        B b = newInstance(clz);
        BeanUtils.copyProperties(a, b);
        return b;
    }

    @Override
    protected A doBackward(B b) {
        Class<A> clz = (Class<A>) getType(0);
        A a = newInstance(clz);
        BeanUtils.copyProperties(b, a);
        return a;
    }

}
