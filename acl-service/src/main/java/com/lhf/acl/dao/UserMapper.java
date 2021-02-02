package com.lhf.acl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhf.acl.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
