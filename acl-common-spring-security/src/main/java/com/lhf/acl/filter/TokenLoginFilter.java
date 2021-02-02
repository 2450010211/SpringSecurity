package com.lhf.acl.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhf.acl.entity.SecurityUser;
import com.lhf.acl.entity.FormUser;
import com.lhf.acl.security.TokenManager;
import com.lhf.acl.utils.R;
import com.lhf.acl.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @Author: lhf
 * @Date: 2021/1/31 16:44
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        //登录页面
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login","POST"));
    }

    //获取表单提交的用户名和密码
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //获取表单提交的数据
        try {
            FormUser user = new ObjectMapper().readValue(request.getInputStream(), FormUser.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //认证成功
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        //获取attemptAuthentication认证成功的用户信息
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        //得到用户名生成的token
        String token = tokenManager.createToken(securityUser.getUsername());
        //把用户名和权限放到redis中
        redisTemplate.opsForValue().set(securityUser.getUsername(), securityUser.getPermissionValueList());
        ResponseUtil.out(response, R.ok().data("token", token));
    }

    //认证失败
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response, R.error());
    }
}
