package com.mark.common.exception;

public class LessonException extends RuntimeException {
    private String msg;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LessonException(String msg) {
        super(msg);
    }
    public LessonException(String msg, Throwable ex) {
        super(msg, ex);
    }
    public LessonException(Throwable ex) {
        super(ex);
    }
}
