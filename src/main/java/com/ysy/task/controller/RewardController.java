package com.ysy.task.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ysy.task.common.dto.ConvertReWardDto;
import com.ysy.task.common.dto.RewardAddDto;
import com.ysy.task.common.dto.RewardDto;
import com.ysy.task.common.lang.Result;
import com.ysy.task.entity.Reward;
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
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yaosy
 * @since 2021-04-25
 */
@RestController

public class RewardController {
    @Autowired
    RewardService rewardService;
    @Autowired
    UserService userService;
    @Autowired
    UserRewardService userRewardService;

    @RequiresAuthentication
    @PostMapping("/getReward")
    public Result inedx(@Validated @RequestBody RewardDto rewardDto) {

        List<Reward> rewards = new LinkedList<>();
        rewards = rewardService.list(new QueryWrapper<Reward>().eq("user_id", rewardDto.getUserId()));

        return Result.succ(rewards);

    }

    @RequiresAuthentication
    @PostMapping("/convertReWard")
    public Result convertReWard(@RequestBody ConvertReWardDto convertReWardDto) {

        convertReWardDto.getUserId();
        User user = userService.getById(convertReWardDto.getUserId());
        Long lastPoint = user.getPoint() - convertReWardDto.getNeedPoint();
        Reward reward = rewardService.getById(convertReWardDto.getId());
        Long lastNum = reward.getNum() - 1;
        if (lastPoint >= 0 && lastNum >= 0) {
            user.setPoint(lastPoint);
            QueryWrapper userQueryWrapper = new QueryWrapper<User>().eq("id", convertReWardDto.getUserId());
            boolean updateUser = userService.update(user, userQueryWrapper);
            if (updateUser) {

                reward.setNum(lastNum);
                QueryWrapper rewardQueryWrapper = new QueryWrapper<User>().eq("id", convertReWardDto.getId());
                boolean updateReward = rewardService.update(reward, rewardQueryWrapper);
                QueryWrapper userRewardQueryWrapper = new QueryWrapper<UserReward>().eq("reward_id", convertReWardDto.getId());
                UserReward userReward = userRewardService.getOne(userRewardQueryWrapper);
                UserReward r = new UserReward();
                r.setNeedPoint(reward.getNeedPoint());
                r.setRewardId(reward.getId());
                r.setNum(1L);
                r.setTotalPoint(reward.getNeedPoint());
                r.setUserId(reward.getUserId());
                r.setRewardName(reward.getRewardName());
                if (userReward == null) {
                    userRewardService.save(r);
                } else {
                    r.setId(userReward.getId());
                    r.setTotalPoint(userReward.getTotalPoint() + reward.getNeedPoint());
                    r.setNum(userReward.getNum() + 1);
                    userRewardService.saveOrUpdate(r);
                }

                return Result.succ("兑换成功");

            }
            return Result.fail("兑换失败");
        }
        return Result.fail("点数或者数量不足");
    }

    @RequiresAuthentication
    @PostMapping("/addReward")
    public Result addReward(@Validated @RequestBody RewardAddDto rewardAddDto) {


        Reward reward = new Reward();
        reward.setUserId(rewardAddDto.getUserId());
        reward.setRewardName(rewardAddDto.getName());
        reward.setRewardDescribe(rewardAddDto.getDescribe());
        reward.setNum(rewardAddDto.getNum());
        reward.setNeedPoint(rewardAddDto.getNeedPoint());

        boolean update = rewardService.saveOrUpdate(reward);
        if (update) {
            return Result.succ("新增成功");
        }
        return Result.fail("新增失败");
    }

    @RequiresAuthentication
    @PostMapping("/deleteReward")
    public Result deleteReward(@Validated @RequestBody ConvertReWardDto convertReWardDto) {

        boolean removeById = rewardService.removeById(convertReWardDto.getId());
        return Result.succ(removeById);

    }

}
