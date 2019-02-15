package com.mark.common.exception;

public class LessonException extends Exception{
    private String msg;
    private Integer code;

    public LessonException(String msg, Integer code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public LessonException(String msg) {
        super(msg);
        this.msg = msg;
    }
    public LessonException(String msg, Throwable ex) {
        super(msg, ex);
        this.msg = msg;
    }

    public LessonException(Throwable ex) {
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

