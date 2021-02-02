package com.lhf.springsecuritydemo01.controller;

import com.lhf.springsecuritydemo01.bean.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: lhf
 * @Date: 2021/1/3 17:04
 */
@Slf4j
@RestController
public class SecurityAnnotationController {

    @GetMapping("/secured")
    @Secured({"ROLE_manage","ROLE_admin"})
    public String secured(){
        return "@Secured";
    }

    @GetMapping("/preauth")
    @PreAuthorize("hasAuthority('admin')")
    public String preAuth(){
        return "@PreAuthorize";
    }

    @GetMapping("/postauth")
    @PostAuthorize("hasAnyAuthority('admin')")
    public String postAuth(){
        log.info("return之前:@PostAuthorize");
        return "@PostAuthorize";
    }

    @GetMapping("/postfilter")
    @PostFilter("filterObject.username=='AA'")
    public List<UserInfo> postFilter(){
        Date curDate = new Date();
        List<UserInfo> userInfoList = new ArrayList<>();
        userInfoList.add(new UserInfo(123L,"AA","aa",curDate));
        userInfoList.add(new UserInfo(456L,"BB","bb",curDate));
        return userInfoList;
    }

    @PostMapping("/prefilter")
    @PreFilter(value = "filterObject.id % 2 == 0")
    public List<UserInfo> preFilter(@RequestBody List<UserInfo> userInfoList){
        for (UserInfo userInfo : userInfoList) {
            log.info(userInfo.toString());
        }
        return userInfoList;
    }
}
