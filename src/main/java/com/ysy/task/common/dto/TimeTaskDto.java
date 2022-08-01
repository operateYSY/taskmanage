package com.ysy.task.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author Yaosy5
 * @description： 类描述
 * @date 2021/3/17 20:37
 */
@Data
public class TimeTaskDto implements Serializable {
    private Long userId;
    private Long id;
    private Long plantTime;
}
