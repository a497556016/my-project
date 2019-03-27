package com.heshaowei.myproj.bean.request;

import java.util.Arrays;
import java.util.List;

public class PageReq {
    private int current = 1;
    private int size = 10;

    private List<Order> orders;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setOrders(Order... orders) {
        this.orders = Arrays.asList(orders);
    }
}
