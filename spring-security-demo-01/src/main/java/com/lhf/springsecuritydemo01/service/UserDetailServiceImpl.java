package com.lhf.springsecuritydemo01.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhf.springsecuritydemo01.bean.UserInfo;
import com.lhf.springsecuritydemo01.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lhf
 * @Date: 2021/1/3 0:00
 */
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserInfoDao userInfoDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        UserInfo userInfo = userInfoDao.selectOne(queryWrapper);

        if(userInfo == null){
            throw new UsernameNotFoundException(username + "用户名不存在");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(userInfo.getPassword());
        //权限
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_manage");
        User user = new User(userInfo.getUsername(), password, authorities);
        return user;
    }

}
