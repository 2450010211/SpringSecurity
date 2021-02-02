package com.lhf.acl.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: lhf
 * @Date: 2021/1/31 16:05
 */
@Component
public class TokenManager {

    //token有效时长
    private long tokenExpiration = 24 * 60 * 60 * 1000;
    //密钥
    private String tokenSignKey = "123456";

    //根据用户名生产token
    public String createToken(String userName){
        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512,tokenSignKey).compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    //根据token字符串获取用户信息
    public String getUserInfoByToken(String token){
        String userInfo =Jwts.parser()
                .setSigningKey(tokenSignKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userInfo;
    }
}
