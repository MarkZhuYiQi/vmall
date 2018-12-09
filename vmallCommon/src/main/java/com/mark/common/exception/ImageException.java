package com.mark.common.exception;

public class ImageException extends RuntimeException {
    private String msg;

    public ImageException(String msg) {
        super(msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
