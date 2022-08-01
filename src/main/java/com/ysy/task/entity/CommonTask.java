package com.ysy.task.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
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
public class CommonTask implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Integer state;

    private String taskDescribe;

    private LocalDateTime createTime;

    private LocalDateTime doneTime;

    private Integer point;

    private Integer type;

    private Long totalNum;


}
