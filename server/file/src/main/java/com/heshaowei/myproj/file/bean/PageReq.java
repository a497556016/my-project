package com.heshaowei.myproj.file.bean;

import com.heshaowei.myproj.bean.request.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageReq extends com.heshaowei.myproj.bean.request.PageReq {

    private Sort sort;

    public Sort getSort(){
        if(null != this.sort) {
            return this.sort;
        }

        List<Order> orders = this.getOrders();
        if(null == orders) {
            return null;
        }

        Sort sort = null;
        for (Order order : orders) {
            Sort s = Sort.by(order.getDirection().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, order.getProperties());
            if(null == sort) {
                sort = s;
            }else{
                sort.and(s);
            }
        }
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public PageRequest turn(){
        return PageRequest.of(this.getCurrent()-1, this.getSize(), this.getSort());
    }
}
