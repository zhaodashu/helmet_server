package com.zmm.helmet.config;

import com.zmm.helmet.realms.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * 用来整合shiro框架相关的配置类
 */
@Configuration
public class ShiroConfig {

    // 1.创建shiroFilter //负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //给Filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //配置系统受限资源
        //配置系统公共资源
//        Map<String, String> map = new HashMap<String, String>();
////        map.put("/user/findAll", "anon");
//        map.put("/user/**", "anon");
//        map.put("/**", "authc"); //authc 请求这个资源需要认证和授权
//
//
//        //默认认证界面路径
////        shiroFilterFactoryBean.setLoginUrl("/login.jsp");
//
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    // 2.创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //给安全管理器设置Realm
        securityManager.setRealm(userRealm);

        return securityManager;
    }

    // 3.创建自定义realm
    @Bean
    public UserRealm getRealm(){
        UserRealm userRealm = new UserRealm();

        return userRealm;
    }

}
