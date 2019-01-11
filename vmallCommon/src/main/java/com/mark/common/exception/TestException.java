package com.mark.common.exception;

/**
 * 继承Exception就必须处理
 * 继承RuntimeException就不需要处理
 */
public class TestException extends Exception {
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TestException() {
    }

    public TestException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
