package com.lhf.springsecuritydemo01.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhf.springsecuritydemo01.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: lhf
 * @Date: 2021/1/3 0:42
 */
@Mapper
public interface UserInfoDao extends BaseMapper<UserInfo> {
}
