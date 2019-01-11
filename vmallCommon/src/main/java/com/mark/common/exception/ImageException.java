package com.mark.common.exception;

public class ImageException extends RuntimeException {
    private String msg;
    private Integer code;
    public ImageException(String msg) {
        super(msg);
        this.setMsg(msg);
    }

    public ImageException(String message, Integer code) {
        super(message);
        this.msg = message;
        this.code = code;
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
