package com.heshaowei.myproj.bean.response;

import com.google.common.base.Preconditions;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public static int SUCCESS = 1;
    public static int ERROR = 0;

    public final int getCode() {
        return this.code;
    }

    public final Result setCode(int var1) {
        this.code = var1;
        return this;
    }

    public final String getMsg() {
        return this.msg;
    }

    public final Result setMsg(String var1) {
        this.msg = var1;
        return this;
    }

    public final T getData() {
        return this.data;
    }

    public Result setData(T var1) {
        this.data = var1;
        return this;
    }

    public static <T> Result<T> success(String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(SUCCESS);
        result.setData(data);
        if (msg == null) {
            msg = "执行成功";
        }

        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> success(String msg) {
        Preconditions.checkNotNull(msg);
        return Result.success(msg, null);
    }

    public static <T> Result<T> success(T data) {
        return Result.success(null, data);
    }

    public static <T> Result<T> success() {
        return Result.success(null, null);
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(ERROR);
        result.setMsg(msg == null ? "执行失败！" : msg);
        return result;
    }

    public static <T> Result<T> error(String msg, String... args){
        return error(String.format(msg, args));
    }

    public static <T> Result<T> error() {
        return Result.error("");
    }

    public static Result error(BindingResult br) {
        StringBuffer errorMsg = new StringBuffer();
        for (FieldError fieldError : br.getFieldErrors()) {
            errorMsg.append(fieldError.getDefaultMessage()+"\n");
        }

        return Result.error(errorMsg.toString());
    }

    public void setResult(Result<T> result) {
        this.msg = result.getMsg();
        this.code = result.getCode();
        this.data = result.getData();
    }
}
