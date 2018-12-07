package com.mark.manager.service;

import com.mark.manager.dto.SignatureBody;
import com.qcloud.Utilities.Json.JSONObject;

public interface TencentService {
    public String getTempPermission();
    public String sign(SignatureBody signatureBody);
}
