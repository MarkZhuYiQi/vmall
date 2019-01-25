package com.mark.common.exception;

//public class CourseException extends RuntimeException{
public class CourseException extends Exception{
    private String msg;
    private Integer code;

    public CourseException(String msg, Integer code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public CourseException(String msg) {
        super(msg);
        this.msg = msg;
    }
    public CourseException(String msg, Throwable ex) {
        super(msg, ex);
        this.msg = msg;
    }

    public CourseException(Throwable ex) {
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
