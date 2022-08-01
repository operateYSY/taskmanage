package com.ysy.task.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ysy.task.common.dto.TimeTaskDto;
import com.ysy.task.common.lang.Result;
import com.ysy.task.entity.CommonTask;
import com.ysy.task.service.CommonTaskService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yaosy
 * @since 2021-05-20
 */
@RestController
public class CommonTaskController {
    @Autowired
    CommonTaskService commonTaskService;

    @RequiresAuthentication
    @PostMapping("/getCommonTask")
    public Result inedx(@RequestBody TimeTaskDto timeTaskDto) {

        List<CommonTask> commonTasks = new LinkedList<>();
        commonTasks = commonTaskService.list(new QueryWrapper<CommonTask>().eq("user_id", timeTaskDto.getUserId()));

        return Result.succ(commonTasks);

    }
}
