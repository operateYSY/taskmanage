package com.ysy.task.util;

import com.ysy.task.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @author Yaosy5
 * @description： 类描述
 * @date 2021/3/5 17:49
 */
public class ShiroUtil {
    public static AccountProfile getProfile(){
        return (AccountProfile)SecurityUtils.getSubject().getPrincipal();

    }
}
