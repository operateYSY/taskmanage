package com.ysy.task.common.lang;

import lombok.Data;

/**
 * @author Yaosy5
 * @description： 统一结果封装
 * @date 2021/2/27 17:07
 */
@Data
public class Result {
    private int code;
    private  String msg;
    private  Object data;

    public static Result succ(Object data) {
        return succ(200, "操作成功", data);
    }
    public static Result succ(int code,String msg,Object data){
        Result r=new Result();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }
    public static Result fail(String msg){
        return fail(400,msg,null);
    }
    public static Result fail(String msg,Object data){
        return fail(400,msg,data);
    }
    public static Result fail(int code,String msg,Object data){
        Result r=new Result();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }
}
