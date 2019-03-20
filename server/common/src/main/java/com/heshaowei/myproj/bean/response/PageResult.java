package com.heshaowei.myproj.bean.response;

import java.util.List;

public class PageResult<T> extends Result<List<T>> {
    private long total;
    private long pages;
    private long current;
    private long size;

    public PageResult(){}

    private PageResult(List<T> data, long total) {
        this.setData(data);
        this.setTotal(total);
    }

    public static <T> PageResult<T> success(List<T> data, long total){
        return new PageResult<>(data, total);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPages() {
        if (this.size == 0) {
            this.pages = 0L;
        } else {
            long pages = this.total / (long)this.size;
            if (this.total % (long)this.size != 0L) {
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
}
