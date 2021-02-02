package com.lhf.acl.service.impl;

import com.lhf.acl.entity.FormUser;
import com.lhf.acl.entity.SecurityUser;
import com.lhf.acl.entity.User;
import com.lhf.acl.service.PermissionService;
import com.lhf.acl.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lhf
 * @Date: 2021/1/31 18:51
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;
    @Autowired
    PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userService.selectByUsername(userName);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        FormUser formUser = new FormUser();
        BeanUtils.copyProperties(user, formUser);

        //查询用户权限表
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(formUser);
        securityUser.setPermissionValueList(permissionValueList);
        return securityUser;
    }
}
