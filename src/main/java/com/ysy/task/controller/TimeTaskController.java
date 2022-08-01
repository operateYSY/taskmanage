package com.ysy.task.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ysy.task.common.dto.AddTimeTaskDto;
import com.ysy.task.common.dto.TimeTaskDto;
import com.ysy.task.common.lang.Result;
import com.ysy.task.entity.TimeTask;
import com.ysy.task.entity.User;
import com.ysy.task.service.TimeTaskService;
import com.ysy.task.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yaosy
 * @since 2021-03-17
 */
@RestController

public class TimeTaskController {
    @Autowired
    TimeTaskService timeTaskService;
    @Autowired
    UserService userService;

    @RequiresAuthentication
    @PostMapping("/getTimeTask")
    public Result inedx(@Validated @RequestBody TimeTaskDto timeTaskDto) {

        List<TimeTask> timeTask = new LinkedList<>();
        timeTask = timeTaskService.list(new QueryWrapper<TimeTask>().eq("user_id", timeTaskDto.getUserId()));

        return Result.succ(timeTask);

    }

    @RequiresAuthentication
    @PostMapping("/updateTimeTask")
    public Result updateTask(@Validated @RequestBody TimeTaskDto timeTaskDto) {


        TimeTask timeTask1 = timeTaskService.getById(timeTaskDto.getId());
        long totalTime = timeTask1.getUsingTimeTotal();
        timeTask1.setUsingTimeTotal(totalTime + timeTaskDto.getPlantTime());
        timeTask1.setDoneTime(LocalDateTime.now());
        QueryWrapper queryWrapper = new QueryWrapper<TimeTask>().eq("id", timeTaskDto.getId());
        boolean update = timeTaskService.update(timeTask1, queryWrapper);

        QueryWrapper userQueryWrapper = new QueryWrapper<User>().eq("id", timeTask1.getUserId());
        User user = userService.getById(timeTaskDto.getUserId());
        user.setPoint(user.getPoint() + timeTask1.getPoint());
        boolean updateUser = userService.update(user, userQueryWrapper);

        if (update && updateUser) {
            return Result.succ("更新成功");
        }
        return Result.fail("更新失败");
    }

    @RequiresAuthentication
    @PostMapping("/addTimeTask")
    public Result AddTask(@Validated @RequestBody AddTimeTaskDto AddtimeTaskDto) {


        TimeTask timeTask = new TimeTask();
        timeTask.setTaskName(AddtimeTaskDto.getName());
        timeTask.setTaskDescribe(AddtimeTaskDto.getDescribe());
        timeTask.setType(AddtimeTaskDto.getType());
        timeTask.setPlantTime(AddtimeTaskDto.getPlantTime());
        timeTask.setPoint(AddtimeTaskDto.getPoint());
        timeTask.setUserId(AddtimeTaskDto.getUserId());
        timeTask.setState(1);
        timeTask.setUsingTimeTotal(0L);
        timeTask.setCreateTime(LocalDateTime.now());

        boolean update = timeTaskService.saveOrUpdate(timeTask);
        if (update) {
            return Result.succ("更新成功");
        }
        return Result.fail("更新失败");
    }

    @RequiresAuthentication
    @PostMapping("/deleteTimeTask")
    public Result deleteTimeTask(@Validated @RequestBody TimeTaskDto timeTaskDto) {

        List<TimeTask> timeTask = new LinkedList<>();
        timeTask = timeTaskService.list(new QueryWrapper<TimeTask>().eq("user_id", timeTaskDto.getUserId()));
        boolean removeById = timeTaskService.removeById(timeTaskDto.getId());
        return Result.succ(removeById);

    }

}
