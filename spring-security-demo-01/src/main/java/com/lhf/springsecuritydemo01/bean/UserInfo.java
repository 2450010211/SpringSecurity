package com.lhf.springsecuritydemo01.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: lhf
 * @Date: 2021/1/3 0:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_info")
@ToString
public class UserInfo {

    @TableId
    private Long id;

    private String username;

    private String password;

    private Date buildTime;
}
