package com.mark.manager.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.mark.common.constant.LoginConstant;
import com.mark.manager.bo.Result;
import com.mark.manager.bo.TokenResult;
import com.mark.manager.dto.Login;
import com.mark.manager.dto.UserRoles;
import com.mark.manager.service.AuthService;
import com.mark.common.rsa.RsaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

@RestController
@RequestMapping(value = "auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthService authService;

    @GetMapping("{appId:\\w+}")
    public UserRoles getAuth(@PathVariable("appId") String appId)
    {
        return authService.getAuthRoles(appId);
    }

    @PostMapping("")
    public Result login(@RequestBody Login login)
    {
        logger.info("开始");
        login = authService.decrypt(login);
        String errMessage = authService.verifyLogin(login);
        if (errMessage == null)
        {
            Result result = authService.genToken(login);
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(principal);
            return result;
        }
        return new Result(new TokenResult("", ""), LoginConstant.PASS_MISMATCH);
    }

    @GetMapping("testauth")
    public Result testAuth()
    {
        logger.info("测试开始");
        String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD5MPAoPanethCu9+P6QrwSzWCZ" +
                "E/iiRPyS5vq0HQBIpICv6Syx45N5Y/A6bec6KLefhYX8CpYquFbNTu2V9BW4QLQf" +
                "l78yBJS90EkEh2Q/+Ep6fIjlU3Lud3jy3vOqiCm/HwTBiiic20TBmxC84wXOY7r0" +
                "s3usGiDpXPS2HmH5fQIDAQAB";

        String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAPkw8Cg9qd62EK73" +
                "4/pCvBLNYJkT+KJE/JLm+rQdAEikgK/pLLHjk3lj8Dpt5zoot5+FhfwKliq4Vs1O" +
                "7ZX0FbhAtB+XvzIElL3QSQSHZD/4Snp8iOVTcu53ePLe86qIKb8fBMGKKJzbRMGb" +
                "ELzjBc5juvSze6waIOlc9LYeYfl9AgMBAAECgYEA6fKgBaeey5ByqVHhnxr4J07Z" +
                "2d1+GBpcZi6kQGTZ11lZ1806QOg/lD/XUYPNCjUf74wT9kSaJxFZrPbk51t45uDT" +
                "dcLldRUX68obkkfNqvRNXFLzBF3dx02fSYeQJsxJca4e5Wrg2ORomJG0lnS1xE6U" +
                "Ck1XLv1oF8K80sQXpQECQQD+snHUghrwr7raWnFFCRxZpTezsRxt9cz7Cw+blHe8" +
                "Inrcktuv+UP6qZcWBNDqIqCmunbR8FM+6IyRwtTI3fxlAkEA+ndIaicfJ8/WlRtK" +
                "+2Ql6IFPTHUHb7+dMdeKOpPUcmwhcc7ZmIUCPwgF1E/B3EcICnTJxD8r5s6xOkiG" +
                "XEu7OQJAH9kqeLtIKR6gS9uRXGOWQvNw8qxL9MgOuLmaWwcGreTV3i0TAX826+o2" +
                "9U7m3NA1rIOWhgv6LNyhbzygpPl1LQJBAPTnwgUaL9qr3XR69XyO1opcNn/aRuU8" +
                "BacAzQauGiOwG5u+2QHLvQCJBbouKq8LPY+L1uZvpqvPG3iUBd0lp/ECQCBcOxF4" +
                "R7Ka5uDTsEbAq9ZxA8RX1aN1O7/5W6yj31tgzn7ZklhjV73jBHrKDOmwe4Wm0GOU" +
                "wxMM20YyKpG6Wk8=";

        byte[] aByte = "bgAkgU/AU7pO5QnJqG9iUrB/Ov6Oyg+KdSdDdFS0MzmWvgV9bcvvcbcoYVfOb3bWqZc6cbYYKmIOmJRWuKj/B/X0HAkZX0jIUnCG3LtGBlA4bUI90CdxIuDSn7CjOoRPHEYVHYOW3PgXTAn4+fA9HL+0X5d3E7aKjjR/96mwwk4=".getBytes();
        byte[] bByte = "wCMDcc35uYLiZGzI7Qstz7x387ljDpwYqjt7+MToMi8JAN3OvHHWkC9lnstcbx4+jSkqChKzh7SgL92hyaM22jboCG8e7wuZWvLABoy9qudeunpwUjWntcruL2XWVJsjO/89lVQSkevfw49g2F77sHC2nD/taVhjbzmRJ3+qur4=".getBytes();
        try {
            //将Base64编码后的私钥转换成PrivateKey对象
            PrivateKey privateKey = RsaUtil.string2PrivateKey(PRIVATE_KEY);
            //加密后的内容Base64解码
            byte[] base642Byte = RsaUtil.base642Byte(new String(aByte));
            //用私钥解密
            byte[] privateDecrypt = RsaUtil.privateDecrypt(base642Byte, privateKey);
            //解密后的明文
            System.out.println("解密后的明文: " + new String(privateDecrypt));
            return new Result(new TokenResult("", new String(privateDecrypt)), 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(new TokenResult("", ""), 200);
    }
}
