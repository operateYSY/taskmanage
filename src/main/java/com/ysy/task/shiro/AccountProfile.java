package com.ysy.task.shiro;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Yaosy5
 * @description： 类描述
 * @date 2021/3/3 16:30
 */
@Data
public class AccountProfile implements Serializable {
    private Long id;

    private String username;

    private String avatar;

    private String email;

    private LocalDateTime lastLogin;

}
