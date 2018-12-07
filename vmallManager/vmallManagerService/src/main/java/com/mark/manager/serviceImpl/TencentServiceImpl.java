package com.mark.manager.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.mark.common.constant.TencentConstant;
import com.mark.common.util.EncryptUtil;
import com.mark.manager.dto.SignatureBody;
import com.mark.manager.service.TencentService;
import com.qcloud.Module.Sts;
import com.qcloud.QcloudApiModuleCenter;
import com.qcloud.Utilities.Json.JSONObject;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class TencentServiceImpl implements TencentService {
    public String getTempPermission() {
        /* 如果是循环调用下面举例的接口，需要从此处开始你的循环语句。切记！ */
        TreeMap<String, Object> config = new TreeMap<String, Object>();
        config.put("SecretId", TencentConstant.secretId);
        config.put("SecretKey", TencentConstant.secretKey);
        /* 请求方法类型 POST、GET */
        config.put("RequestMethod", "GET");
        /* 区域参数，可选: gz:广州; sh:上海; hk:香港; ca:北美;等。 */
        config.put("DefaultRegion", "sh");
        QcloudApiModuleCenter module = new QcloudApiModuleCenter(new Sts(),
                config);

        TreeMap<String, Object> params = new TreeMap<String, Object>();
        /* 将需要输入的参数都放入 params 里面，必选参数是必填的。 */
        /* DescribeInstances 接口的部分可选参数如下 */
        params.put("name", "mark");
        String policy = "{\"statement\": [{\"action\": [\"name/cos:GetObject\",\"name/cos:PutObject\"],\"effect\": \"allow\",\"resource\":[\"qcs::cos:ap-shanghai:uid/1258194404:vpro-1258194404/*\"]}],\"version\": \"2.0\"}";
        params.put("policy", policy);

        /* 在这里指定所要用的签名算法，不指定默认为 HmacSHA1*/
//        params.put("SignatureMethod", "HmacSHA256");

        /* generateUrl 方法生成请求串, 可用于调试使用 */
        System.out.println(module.generateUrl("GetFederationToken", params));
        String result = null;
        try {
            /* call 方法正式向指定的接口名发送请求，并把请求参数 params 传入，返回即是接口的请求结果。 */
            result = module.call("GetFederationToken", params);
            JSONObject jsonResult = new JSONObject(result);
            System.out.println(jsonResult);
            return jsonResult.toString();
        } catch (Exception e) {
            System.out.println("error..." + e.getMessage());
        }
        return null;
    }

    /**
     * 生成Authorization PUT请求签名
     * @return String
     */
    public String sign(SignatureBody signatureBody) {
        Map<String, String> map = new TreeMap<>();
        map.put("q-sign-algorithm", "sha1");
        map.put("q-ak", TencentConstant.secretId);
        Long currentTimestamp = System.currentTimeMillis() / 1000;
        DateTime dt = new DateTime();
        Long fTimestamp = dt.plusMinutes(15).getMillis() / 1000;
        String time = String.valueOf(currentTimestamp) + ";" + String.valueOf(String.valueOf(fTimestamp));
        map.put("q-sign-time", time);
        map.put("q-key-time", time);
//        map.put("q-header-list", "host;x-cos-content-sha1;x-cos-storage-class");
        map.put("q-header-list", "host");
        map.put("q-url-param-list", "");
        map.put("q-signature", authorize(signatureBody, time));
        String res = "";
        for(Map.Entry<String, String> m : map.entrySet()) {
            if (res != "") res += "&";
            res += m.getKey() + "="+m.getValue();
        }
        System.out.println(res);
        return res;
    }

    /**
     * 请求签名Signature字符串生成成功！nm的
     * @return
     */
    private String authorize(SignatureBody signatureBody, String time) {
        String signKey = null;
        try {
            signKey = EncryptUtil.calculateRFC2104HMAC(time, TencentConstant.secretKey);
//        HttpString = [HttpMethod]\n[HttpURI]\n[HttpParameters]\n[HttpHeaders]\n
//        StringToSign = [q-sign-algorithm]\n[q-sign-time]\nSHA1-HASH(HttpString)\n
            String httpString = "put\n/" + signatureBody.getName() + "\n\nhost=" + signatureBody.getHost() + "\n";
            System.out.println(httpString);
            String sha1HttpString = EncryptUtil.sha1(httpString);
            String stringToSign = "sha1\n" + time + "\n" + sha1HttpString + "\n";
            System.out.println(stringToSign);
            return EncryptUtil.calculateRFC2104HMAC(stringToSign, signKey);
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
