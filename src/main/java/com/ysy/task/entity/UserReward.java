package com.ysy.task.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yaosy
 * @since 2021-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserReward implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long rewardId;

    private Long userId;

    private String rewardName;

    private Long num;

    private Long needPoint;

    private Long totalPoint;

    private Long text1;


}
