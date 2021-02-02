package com.lhf.springsecuritydemo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SpringSecurityDemo01Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemo01Application.class, args);
       /* ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringSecurityDemo01Application.class, args);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }*/
    }

}
