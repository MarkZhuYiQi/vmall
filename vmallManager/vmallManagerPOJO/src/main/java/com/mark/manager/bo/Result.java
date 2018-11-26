package com.mark.manager.bo;

import java.io.Serializable;

public class Result implements Serializable {
    private Object data;
    private Integer code;
    {
        code = 200;
    }

    public Result(Object data, Integer code) {
        this.data = data;
        this.code = code;
    }

    public Result(Object data) {
        this.data = data;
    }
    public Result() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
