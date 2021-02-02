package com.lhf.acl.security;

import com.lhf.acl.utils.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Author: lhf
 * @Date: 2021/1/31 15:58
 */
@Component
public class DefaultPasswordEncoder implements PasswordEncoder {

    public DefaultPasswordEncoder(){
        this(-1);
    }

    public DefaultPasswordEncoder(int strength){}

    //密码加密
    @Override
    public String encode(CharSequence charSequence) {
        return MD5.encrypt(charSequence.toString());
    }

    //密码对比
    @Override
    public boolean matches(CharSequence charSequence, String encodedPWD) {
        return encodedPWD.equals(MD5.encrypt(charSequence.toString()));
    }
}
