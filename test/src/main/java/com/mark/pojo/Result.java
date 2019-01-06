package com.mark.pojo;

public class Result {
    private String data;
    private String code;

    @Override
    public String toString() {
        return "Result{" +
                "data='" + data + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public Result() {
    }

    public Result(String data, String code) {
        this.data = data;
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
