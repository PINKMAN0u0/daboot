package com.pinkman.dtboot.shiro;

import com.pinkman.dtboot.entity.SysUser;
import com.pinkman.dtboot.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-17 00:04
 **/
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    /**
     * @description: 认证
     * @param token
     * @return: org.apache.shiro.authc.AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        System.out.println("认证");

        ///用户输入的用户名和密码
        String usernameInput = (String)token.getPrincipal();
        //要先以字符获取再拼接成字符串
        String passwordInput = new String((char[])token.getCredentials());

        //查询用户是否存在
        SysUser user = sysUserService.queryByUserName(usernameInput);

        if(user == null){
            throw new UnknownAccountException("账号或密码不正确");
        }

        ///从数据库中取出的用户名和密码
        String username = user.getUsername();
        String password = user.getPassword();

        //判断密码是否正确
        if (!passwordInput.equals(password)) {
            throw new UnknownAccountException("账号或密码不正确");
        }

        //判断账号是否被锁定
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        //将数据库中取出的password与token中的password进行比较
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo( user, password, this.getName());


        return info;
    }

    /**
     * @description: 授权
     * @param principal
     * @return: org.apache.shiro.authz.AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        System.out.println("授权");
        //获取当前角色对象信息
        SysUser user = (SysUser) principal.getPrimaryPrincipal();

        //根据用户id从数据库获取当前用户角色的所有权限
        Set<String> userPermissions = sysUserService.getUserPermissionsById(user.getUserId());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //资源授权
        info.addStringPermissions(userPermissions);

        return info;
    }


}
