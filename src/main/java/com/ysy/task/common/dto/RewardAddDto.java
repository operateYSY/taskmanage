package com.ysy.task.common.dto;

import lombok.Data;

/**
 * @author Yaosy5
 * @description： 类描述
 * @date 2021/5/20 13:09
 */
@Data
public class RewardAddDto {

    private Long userId;
    private String name;
    private String describe;
    private Long num;
    private Long needPoint;

}
