package com.heshaowei.myproj.account.controller.base;

import com.google.common.base.Converter;
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
import java.util.List;
import java.util.Map;

public class CurdController<S extends JpaRepository<E, Long>, E, D extends Converter<D, E>> extends BaseController {

    @Autowired
    private S repository;

    @GetMapping("/selectPage")
    public PageResult<D> selectPage(int current, int size, HttpServletRequest request){
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();//获取当前new对象的泛型的父类类型
        Class<E> entityClz = (Class<E>) parameterizedType.getActualTypeArguments()[1];
        Class<D> dtoClz = (Class<D>) parameterizedType.getActualTypeArguments()[2];
        D d = null; E e = null;
        try {
            d = dtoClz.newInstance();
            e = entityClz.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }

        ExampleMatcher matcher = ExampleMatcher.matchingAny();
        Map<String, String[]> map = request.getParameterMap();
        Field[] fields = entityClz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String[] values = map.get(fieldName);
            if(null != values && values.length > 0 && StringUtils.isNotBlank(values[0])) {
                field.setAccessible(true);
                try {
                    field.set(e, values[0]);
                    if(field.getType().equals(String.class)) {
                        matcher.withMatcher(fieldName, ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
                    }
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            }
        }
        Example<E> example = Example.of(e, matcher);

        Sort sort = Sort.unsorted();
        String sortField = request.getParameter("sortField");
        String sortDirection = request.getParameter("sortDirection");
        if(StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortDirection)) {
            sort.and(Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        }

        Page<E> page = this.repository.findAll(example, PageRequest.of(current-1, size, sort));

        return pageConvert(d, page);
    }

    @PostMapping("/save")
    public Result<D> save(@RequestBody D d){
        E e = d.convert(d);
        e = this.repository.save(e);
        return Result.success("保存成功！", d.reverse().convert(e));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathParam("id") Long id){
        this.repository.deleteById(id);
        return Result.success();
    }

    @PutMapping("/delete")
    public Result delete(@RequestBody D d){
        E e = d.convert(d);
        this.repository.delete(e);
        return Result.success();
    }

    @PutMapping("/batchDelete")
    public Result batchDelete(@RequestBody List<D> list){
        if(null != list){
            D d = list.get(0);
            Iterable<E> iterable = d.convertAll(list);
            this.repository.deleteInBatch(iterable);
            return Result.success();
        }
        return Result.error();
    }
}
