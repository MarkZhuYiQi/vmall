package com.mark.common.exception;

public class RedisException extends RuntimeException {
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

    public RedisException(String msg) {
        super(msg);
    }
    public RedisException(String msg, Throwable ex) {
        super(msg, ex);
    }
    public RedisException(Throwable ex) {
        super(ex);
    }
}
