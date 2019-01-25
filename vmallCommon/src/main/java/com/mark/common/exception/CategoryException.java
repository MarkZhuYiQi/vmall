package com.mark.common.exception;

//public class CategoryException extends RuntimeException{
public class CategoryException extends Exception{
    private String msg;
    private Integer code;

    public CategoryException(String msg, Integer code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public CategoryException(String msg) {
        super(msg);
        this.msg = msg;
    }
    public CategoryException(String msg, Throwable ex) {
        super(msg, ex);
        this.msg = msg;
    }

    public CategoryException(Throwable ex) {
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
