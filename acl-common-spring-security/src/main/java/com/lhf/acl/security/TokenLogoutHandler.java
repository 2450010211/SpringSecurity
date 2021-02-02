package com.lhf.acl.security;

import com.lhf.acl.utils.R;
import com.lhf.acl.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: lhf
 * @Date: 2021/1/31 16:22
 */
public class TokenLogoutHandler implements LogoutHandler {

    private TokenManager tokenManage;
    private RedisTemplate redisTemplate;

    public TokenLogoutHandler(TokenManager tokenManage, RedisTemplate redisTemplate){
        this.tokenManage = tokenManage;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //从header获取token
        String token = request.getHeader("token");
        if(token != null && token.trim().length() > 0){
            //移除token   可以不用写
            String userName = tokenManage.getUserInfoByToken(token);
            //从redis中删除
            redisTemplate.delete(userName);
        }
        ResponseUtil.out(response, R.ok());
    }
}
