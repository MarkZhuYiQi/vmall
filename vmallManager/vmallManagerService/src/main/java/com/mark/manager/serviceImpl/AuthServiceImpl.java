package com.mark.manager.serviceImpl;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.mark.common.constant.LoginConstant;
import com.mark.common.constant.RSAConstant;
import com.mark.common.jedis.JedisClient;
import com.mark.common.jedis.JedisClientPool;
import com.mark.common.jwt.JwtUtil;
import com.mark.common.pojo.User;
import com.mark.common.rsa.RsaUtil;
import com.mark.common.util.BeanUtil;
import com.mark.manager.bo.Result;
import com.mark.manager.bo.TokenResult;
import com.mark.manager.dto.DtoUtil;
import com.mark.manager.dto.UserRoles;
import com.mark.manager.mapper.UserRolesPermissionsMapper;
import com.mark.manager.mapper.VproAuthMapper;
import com.mark.manager.dto.Login;
import com.mark.manager.pojo.VproAuth;
import com.mark.manager.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.beans.IntrospectionException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Map;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    VproAuthMapper vproAuthMapper;
    @Autowired
    Validator localValidator;
    @Autowired
    UserRolesPermissionsMapper userRolesPermissionsMapper;
    @Autowired
    JedisClient jedisClient;

    @Value("${publicKey}")
    private String publicKey;

    @Value("${authPrefix}")
    private String authPrefix;

    @Value("${rolePrefix}")
    private String rolePrefix;

//    @Value("${privateKey}")
//    private String privateKey;

    /**
     * 检验信息是否合规合法，手动@Validated
     * @param login
     * @return
     */
    @Override
    public String verifyLogin(Login login)
    {
        DataBinder dataBinder = new DataBinder(login);
        dataBinder.setValidator(localValidator);
        dataBinder.validate();
        BindingResult bindingResult = dataBinder.getBindingResult();
        String errMessage = null;
        if (bindingResult.hasErrors())
        {
            errMessage = bindingResult.getFieldError().getDefaultMessage();
        }
        return errMessage;
    }

    @Override
    public UserRoles getAuthRoles(String appId) {
        System.out.println(appId);
        return userRolesPermissionsMapper.getUserRoles(appId);
    }

    @Override
    public Result genToken(Login login) throws Exception {
        // 拿到用户在数据库的信息和权限
        UserRoles userRoles = userRolesPermissionsMapper.getUserRoles(login.getAppId());
        if (userRoles == null)
        {
            return new Result(new TokenResult("", "", "user not exist"), LoginConstant.USER_NOT_EXIST);
        }
        System.out.println("AuthService.genToken_userRoles_output: " + userRoles.toString());
        // 解密数据库中用户的密码
        String authAppKey = decryptString(userRoles.getAuthAppkey());
        // 密码不一致就报错
        if (!authAppKey.equals(login.getAppKey()))
        {
            return new Result(new TokenResult("", "", "passError"), LoginConstant.PASS_MISMATCH);
        }
        User user = DtoUtil.userRoles2user(userRoles);
        System.out.println("AuthService.genToken_user_beforeGenToken : " + user.toString());
        String token = JwtUtil.genToken(user);
        return new Result(new TokenResult("admin", token));
    }
    @Override
    public String getRoleByNameInRedis(String appAuthId) {
        return "admin";
//        logger.info("尝试获取用户的权限： " + appAuthId);
//        String rolesName = "";
//        JedisClient jedisClient = new JedisClientPool();
//        logger.info("拿到redis连接");
//        String rolesId = jedisClient.hget(authPrefix + appAuthId, "authRolesId");
//        logger.info("拿到用户权限id： " + rolesId);
//        if (!rolesId.equals("")) {
//            rolesName = jedisClient.hget(rolePrefix + rolesId, "rolesName");
//        }
//        return rolesName;
    }
    public Map<String, String> getAuthByAuthIdFromRedis(String appAuthId) {
        logger.info("从redis获取用户信息: " + appAuthId);
        return jedisClient.hgetAll(authPrefix + appAuthId);
    }
    public String decryptString(String en) throws Exception {
        //将Base64编码后的私钥转换成PrivateKey对象
        PrivateKey privateKey = RsaUtil.string2PrivateKey(RSAConstant.privateKey);
        //加密后的内容Base64解码
        byte[] appIdbase642Byte = RsaUtil.base642Byte(en);
        //用私钥解密
        byte[] strPrivateDecrypt = RsaUtil.privateDecrypt(appIdbase642Byte, privateKey);
        System.out.println("AuthService.decryptString: " + new String(strPrivateDecrypt));
        return new String(strPrivateDecrypt);
    }
    public Login decrypt(Login login) throws Exception {
        login.setAppId(decryptString(login.getAppId()));
        login.setAppKey(decryptString(login.getAppKey()));
        return login;
    }
}
