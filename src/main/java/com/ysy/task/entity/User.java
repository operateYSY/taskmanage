package com.ysy.task.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 用户
 *
 * @author yaosy
 * @since 2021-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_user")
@Document(indexName = "user")
@ApiModel(value = "用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Field(type = FieldType.Long)
    @ApiModelProperty(value = "用户id")
    private Long id;

    @NotBlank(message = "昵称不能为空")
    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_max_word")
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

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "状态")
    private Integer status;

    @Field(type = FieldType.Date)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime created;

    @Field(type = FieldType.Date)
    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLogin;

    @Field(type = FieldType.Long)
    @ApiModelProperty(value = "点数")
    private Long point;


}
