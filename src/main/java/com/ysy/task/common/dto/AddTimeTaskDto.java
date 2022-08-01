package com.ysy.task.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Yaosy5
 * @description： 类描述
 * @date 2021/5/19 22:03
 */
@Data
public class AddTimeTaskDto implements Serializable {
    private Long userId;
    private String name;
    private String describe;
    private Integer type;
    private Long plantTime;
    private Integer point;
}
