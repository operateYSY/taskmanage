package com.ysy.task.common.lang;

import lombok.Data;

/**
 * @author Yaosy5
 * @description： 统一结果封装
 * @date 2021/2/27 17:07
 */
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> succ(T data) {
        return succ(200, "操作成功", data);
    }

    public static <T> Result<T> succ(int code, String msg, T data) {
        Result<T> r = new Result();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }

    public static Result fail(String msg) {
        return fail(400, msg, null);
    }

    public static <T> Result<T> fail(String msg, T data) {
        return fail(400, msg, data);
    }

    public static <T> Result<T> fail(int code, String msg, T data) {
        Result<T> r = new Result();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }
}
