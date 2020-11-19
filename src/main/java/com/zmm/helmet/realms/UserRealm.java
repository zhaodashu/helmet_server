package com.zmm.helmet.realms;

import com.zmm.helmet.entity.User;
import com.zmm.helmet.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
// 自定义Realm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        System.out.println("token:" + token);

        //得到用户名和密码
        String principal = (String) token.getPrincipal();
        System.out.println(principal);
        User byUsername = userService.getByName(principal);
        System.out.println(byUsername);

        if(byUsername != null){
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal, byUsername.getPassword(), this.getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }
}
