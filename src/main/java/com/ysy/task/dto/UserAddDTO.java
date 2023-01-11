package com.ysy.task.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author ysy
 * @date 2023-01-10
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "用户新增dto")
public class UserAddDTO {

    @NotBlank(message = "昵称不能为空")
    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "用户名称")
    private String userName;

    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "头像")
    private String avatar;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "邮箱")
    private String email;

    @Field(type = FieldType.Text)
    @ApiModelProperty(value = "密码")
    private String password;
}
