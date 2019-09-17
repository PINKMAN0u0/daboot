package com.pinkman.dtboot.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-17 00:04
 **/
@Component
public class UserRealm extends AuthorizingRealm {

    /**
     * @description: 认证
     * @param authenticationToken
     * @return: org.apache.shiro.authc.AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println("认证");


        return null;
    }

    /**
     * @description: 授权
     * @param principalCollection
     * @return: org.apache.shiro.authz.AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("授权");



        return null;
    }


}
