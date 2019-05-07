package com.heshaowei.myproj.bean.response;

import java.util.List;

public class PageResult<T> extends Result<List<T>> {
    private long total;
    private long pages;
    private long current;
    private long size;

    private PageResult(){}


    public static <T> PageResult<T> of(Class<T> clz, long current, long size){
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCurrent(current);
        pageResult.setSize(size);
        return pageResult;
    }

    public static <T> PageResult<T> of(Class<T> clz){
        PageResult<T> pageResult = new PageResult<>();
        return pageResult;
    }

    public PageResult<T> success(List<T> data, long total){
        PageResult<T> pageResult = this.setTotal(total).setData(data);
        pageResult.setCode(SUCCESS);
        return pageResult;
    }

    public long getTotal() {
        return total;
    }

    public PageResult setTotal(long total) {
        this.total = total;
        return this;
    }

    public long getPages() {
        if (this.size == 0) {
            this.pages = 0L;
        } else {
            long pages = this.total / this.size;
            if (this.total % this.size != 0L) {
                ++pages;
            }

            this.pages = pages;
        }
        return this.pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public PageResult<T> setData(List<T> data) {
        super.setData(data);
        return this;
    }
}
