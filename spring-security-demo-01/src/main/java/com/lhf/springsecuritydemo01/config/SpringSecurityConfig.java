package com.lhf.springsecuritydemo01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @Author: lhf
 * @Date: 2021/1/2 23:35
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        System.out.println("dataSource：" + dataSource.getClass().getName());
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //退出
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/index").permitAll();
        http.exceptionHandling().accessDeniedPage("/unauth.html");  //自定义403没有权限页面
        http
            .formLogin()    //自定义编写登录页面
            .loginPage("/login.html")   //登录的页面
            .loginProcessingUrl("/user/login") //登录的访问路径
            .defaultSuccessUrl("/success.html").permitAll() //登录成功之后跳转路径
            //.failureUrl()
            .and().authorizeRequests()
            .antMatchers("/","/hello","/user/login").permitAll()    //设置那些路径可以直接访问，不需要认证
//            .antMatchers("/index").hasAnyAuthority()    //当前登录的用户具有admin权限才能访问
//            .antMatchers("/index").hasAnyAuthority("admin,manage") //配置当前账号有多个权限，使用逗号分割字符串
//            .antMatchers("/index").hasRole("manage")    //配置当前账号指定角色
            .anyRequest().authenticated()
            .and().rememberMe().tokenRepository(persistentTokenRepository())    //定义操作数据库的数据源
            .tokenValiditySeconds(60)   //设置有效时长，单位秒
            .userDetailsService(userDetailsService) //操作数据库
            .and().csrf().disable();    //关闭csrf防护
    }

}
