package com.ysy.task.shiro;

import com.ysy.task.entity.User;
import com.ysy.task.service.UserService;
import com.ysy.task.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Yaosy5
 * @description：
 * @date 2021/3/1 15:56
 */
@Component
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

   @Override
    public boolean supports(AuthenticationToken token){
       return token instanceof JwtToken;
   }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken=(JwtToken) token;
        String userId=jwtUtils.getClaimByToken((String)jwtToken.getPrincipal()).getSubject();
       User user= userService.getById(Long.valueOf(userId));
       if(user==null){
           throw new UnknownAccountException("账户不存在");
       }
       if(user.getStatus()==-1){
           throw new LockedAccountException("账户被锁");
       }
       AccountProfile profile=new AccountProfile();
        BeanUtils.copyProperties(user,profile);


        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());
    }
}
