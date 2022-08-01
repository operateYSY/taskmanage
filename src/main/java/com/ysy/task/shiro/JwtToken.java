package com.ysy.task.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Yaosy5
 * @description： 类描述
 * @date 2021/3/1 16:28
 */
public class JwtToken implements AuthenticationToken {
    private String token;
    public  JwtToken(String jwt){
        this.token=jwt;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
