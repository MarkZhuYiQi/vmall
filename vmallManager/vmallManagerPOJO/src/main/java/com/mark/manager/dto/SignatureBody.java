package com.mark.manager.dto;

import java.io.Serializable;

public class SignatureBody implements Serializable {
    private String host;
    private String name;

    public SignatureBody() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
