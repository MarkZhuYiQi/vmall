package com.mark.common.exception;

//public class CategoryException extends RuntimeException{
public class CartException extends Exception{
    private String msg;
    private Integer code;

    public CartException(String msg, Integer code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public CartException(String msg) {
        super(msg);
        this.msg = msg;
    }
    public CartException(String msg, Throwable ex) {
        super(msg, ex);
        this.msg = msg;
    }

    public CartException(Throwable ex) {
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
