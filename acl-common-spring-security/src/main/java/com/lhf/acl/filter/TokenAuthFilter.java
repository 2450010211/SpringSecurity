package com.lhf.acl.filter;

import com.lhf.acl.security.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: lhf
 * @Date: 2021/1/31 16:43
 */
public class TokenAuthFilter extends BasicAuthenticationFilter {

    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenAuthFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //获取当前认证成功用户的权限
        UsernamePasswordAuthenticationToken authRequest = getAuthentication(request);
        if(authRequest != null){
            //如果有权限信息，放到权限上下文中
            SecurityContextHolder.getContext().setAuthentication(authRequest);
        }
        super.doFilterInternal(request, response, chain);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(token != null && token.trim().length() > 0){
            String userName = tokenManager.getUserInfoByToken(token);
            List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(userName);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            for (String permissionValue : permissionValueList) {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permissionValue);
                authorities.add(simpleGrantedAuthority);
            }
            return new UsernamePasswordAuthenticationToken(userName, token, authorities);
        }
        return null;
    }
}
