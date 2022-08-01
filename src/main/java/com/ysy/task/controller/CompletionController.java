package com.ysy.task.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ysy.task.common.dto.ConvertReWardDto;
import com.ysy.task.common.dto.RewardDto;
import com.ysy.task.common.dto.TimeTaskDto;
import com.ysy.task.common.lang.Result;
import com.ysy.task.entity.Reward;
import com.ysy.task.entity.TimeTask;
import com.ysy.task.entity.User;
import com.ysy.task.entity.UserReward;
import com.ysy.task.service.RewardService;
import com.ysy.task.service.UserRewardService;
import com.ysy.task.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Yaosy5
 * @description： 类描述
 * @date 2021/4/25 19:23
 */
@RestController
public class CompletionController {
    @Autowired
    UserService userService;
    @Autowired
    UserRewardService userRewardService;
    @Autowired
    RewardService rewardService;

    @RequiresAuthentication
    @PostMapping("/getCompletion")
    public Result inedx(@Validated @RequestBody TimeTaskDto timeTaskDto) {

        List<TimeTask> timeTask = new LinkedList<>();
        //timeTask=timeTaskService.list(new QueryWrapper<TimeTask>().eq("user_id",timeTaskDto.getUserId()));

        return Result.succ(timeTask);

    }

    @RequiresAuthentication
    @PostMapping("/getPoint")
    public Result getPoint(@Validated @RequestBody TimeTaskDto timeTaskDto) {
        User user = userService.getOne(new QueryWrapper<User>().eq("id", timeTaskDto.getUserId()));
        User userDate = new User();
        userDate.setPoint(user.getPoint());
        return Result.succ(userDate);

    }

    @RequiresAuthentication
    @PostMapping("/getUserReward")
    public Result getUserReward(@Validated @RequestBody RewardDto rewardDto) {

        List<UserReward> rewards = new LinkedList<>();
        rewards = userRewardService.list(new QueryWrapper<UserReward>().eq("user_id", rewardDto.getUserId()));

        return Result.succ(rewards);

    }

    @RequiresAuthentication
    @PostMapping("/turnBackUserReward")
    public Result deleteReward(@Validated @RequestBody ConvertReWardDto convertReWardDto) {

        UserReward userReward = userRewardService.getById(convertReWardDto.getId());

        if (userReward.getNum() > 0) {
            userReward.setNum(userReward.getNum() - 1);
            userReward.setTotalPoint(userReward.getTotalPoint() - userReward.getNeedPoint());
            userRewardService.updateById(userReward);

            Reward reward = rewardService.getById(userReward.getRewardId());
            reward.setNum(reward.getNum() + 1);
            rewardService.updateById(reward);

            User user = userService.getById(userReward.getUserId());
            user.setPoint(user.getPoint() + userReward.getNeedPoint());
            boolean updateUser = userService.updateById(user);
            if (userReward.getNum() == 0L) {
                userRewardService.removeById(userReward.getId());
            }
            return Result.succ(updateUser);
        }


        return Result.fail("数据错误");
    }
}
