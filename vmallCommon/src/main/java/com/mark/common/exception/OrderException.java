package com.mark.common.exception;

//public class CourseException extends RuntimeException{
public class OrderException extends Exception{
    private String msg;
    private Integer code;

    public OrderException(String msg, Integer code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public OrderException(String msg) {
        super(msg);
        this.msg = msg;
    }
    public OrderException(String msg, Throwable ex) {
        super(msg, ex);
        this.msg = msg;
    }

    public OrderException(Throwable ex) {
        super(ex);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
