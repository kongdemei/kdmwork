package com.example.java;

/**
 * 自定义异常类
 * @description:
 * @author: kdm
 * @createDate: 2023/4/3
 */
public class CustomException extends RuntimeException{
    private String msg;

    public CustomException(String msg) {
        super(msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
