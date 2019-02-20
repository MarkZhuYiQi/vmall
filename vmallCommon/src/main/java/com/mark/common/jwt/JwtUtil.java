package com.mark.common.jwt;

import java.util.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mark.common.pojo.JwtUserDetails;
import com.mark.common.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtUtil {
    private final static String JWT_KEY = "MarkZhu026";
    private final static Integer EXPIRE_TIME = 9600;
    private static Algorithm algorithmHS()
    {
        return Algorithm.HMAC256(JwtUtil.JWT_KEY);
    }
    public static String genToken(User user)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, EXPIRE_TIME);
        System.out.println("JwtUtil.genToken: " + user.toString());
        return JWT.create()
                .withIssuer("Mark")
                .withExpiresAt(calendar.getTime())
                .withClaim("id", user.getAuthId())
                .withClaim("appid", user.getAuthAppid())
                .withClaim("roleid", user.getAuthRolesId())
                .withClaim("ip", user.getAuthIp())
                .withClaim("rolename", user.getAuthRolesName())
                .sign(algorithmHS());
    }
    public static DecodedJWT verifyToken(String token)
    {
        try
        {
            JWTVerifier verifier = JWT.require(JwtUtil.algorithmHS()).build();
            return verifier.verify(token);
        }
        catch(JWTVerificationException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public static JwtUserDetails getUserFromToken(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        System.out.println(decodedJWT.getClaims());
        JwtUserDetails jwtUserDetails = new JwtUserDetails(
                (long)Integer.valueOf(decodedJWT.getClaim("id").asString()),
                decodedJWT.getClaim("appid").asString(),
                Collections.singleton(new SimpleGrantedAuthority(decodedJWT.getClaim("rolename").asString()))
        );
        return jwtUserDetails;
    }

    public static Collection<? extends GrantedAuthority> parseAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        SimpleGrantedAuthority authority;
        for (String role : roles) {
            authority = new SimpleGrantedAuthority(role.toString());
            authorities.add(authority);
        }
        return authorities;
    }
}
