package com.ysy.task.common.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Yaosy5
 * @description： 类描述
 * @date 2021/3/5 15:56
 */
@Data
public class LoginDto implements Serializable {
    @NotBlank(message = "昵称不能为空")
    private String userName;


    @NotBlank(message = "密码不能为空")
    private String password;
}
