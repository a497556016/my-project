package com.heshaowei.myproj.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Converter;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.heshaowei.myproj.bean.response.PageResult;
import com.heshaowei.myproj.bean.response.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CurdController<R extends JpaRepository<E, Long>, E, D extends Converter<D, E>> extends BaseController {

    @Autowired
    protected R repository;

    @GetMapping("/selectPage")
    public PageResult<D> selectPage(int current, int size, HttpServletRequest request) {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();//获取当前new对象的泛型的父类类型
        Class<D> dtoClz = (Class<D>) parameterizedType.getActualTypeArguments()[2];
        D d = null;
        try {
            d = dtoClz.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }

        Page<E> page = this.repository.findAll(buildExample(request, d), PageRequest.of(current - 1, size, buildSort(request)));

        return pageConvert(d, page);
    }

    @PostMapping("/save")
    public Result<D> save(@RequestBody D d) {
        E e = d.convert(d);
        e = this.repository.save(e);
        return Result.success("保存成功！", d.reverse().convert(e));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathParam("id") Long id) {
        this.repository.deleteById(id);
        return Result.success();
    }

    @PutMapping("/delete")
    public Result delete(@RequestBody D d) {
        E e = d.convert(d);
        this.repository.delete(e);
        return Result.success();
    }

    @PutMapping("/batchDelete")
    public Result batchDelete(@RequestBody List<D> list) {
        if (null != list) {
            D d = list.get(0);
            Iterable<E> iterable = d.convertAll(list);
            this.repository.deleteInBatch(iterable);
            return Result.success();
        }
        return Result.error();
    }

    protected Example<E> buildExample(HttpServletRequest request, D d) {
        Map<String, String[]> map = request.getParameterMap();

        ExampleMatcher matcher = this.createMatcher(map);
        try {
            this.createExample(this.cleanParamsMap(map), d);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        matcher = matcher.withMatcher("catalog.catalog", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        E probe = d.convert(d);
        Example<E> example = Example.of(probe, matcher);

        return example;
    }

    protected Sort buildSort(HttpServletRequest request) {
        Sort sort = Sort.unsorted();
        String sortField = request.getParameter("sortField");
        String sortDirection = request.getParameter("sortDirection");
        if (StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortDirection)) {
            sort = sort.and(Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        }

        return sort;
    }

    private ExampleMatcher createMatcher(Map<String, String[]> paramsMap) {
        ExampleMatcher matcher = ExampleMatcher.matching();

        for (String key : paramsMap.keySet()) {
            if (key.startsWith("like@")) {
                matcher = matcher.withMatcher(key.substring(5), ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
            }
        }

        return matcher;
    }

    private Map<String, String[]> cleanParamsMap(Map<String, String[]> paramsMap) {
        Map<String, String[]> newMap = Maps.newHashMap();
        for (String key : paramsMap.keySet()) {
            String[] values = paramsMap.get(key);

            if (key.contains("@")) {
                String newKey = key.substring(key.lastIndexOf("@") + 1);
                newMap.put(newKey, values);
            } else {
                newMap.put(key, values);
            }
        }
        return newMap;
    }

    private void createExample(Map<String, String[]> paramsMap, Object probe) throws Exception {
        Class clz = probe.getClass();
        for (String key : paramsMap.keySet()) {
            try {
                if (key.contains(".")) {
                    String[] keys = key.split("\\.");

                    Field field = clz.getDeclaredField(keys[0]);
                    Object value = field.getType().newInstance();
                    field.setAccessible(true);
                    field.set(probe, value);

                    Map<String, String[]> map = Maps.newHashMap();
                    map.put(Stream.of(Arrays.copyOfRange(keys, 1, keys.length)).collect(Collectors.joining(".")), paramsMap.get(key));
                    this.createExample(map, value);
                } else {
                    Field field = clz.getDeclaredField(key);
                    String[] values = paramsMap.get(key);
                    if (null != field && null != values && values.length > 0 && StringUtils.isNotBlank(values[0])) {
                        field.setAccessible(true);
                        String value = values[0];
                        try {
                            Object obj = new Gson().fromJson(value, field.getType());
                            field.set(probe, obj);
                        } catch (Exception e) {
                            field.set(probe, value);
                        }
                    }
                }
            } catch (NoSuchFieldException e) {
                continue;
            }
        }
    }
}
