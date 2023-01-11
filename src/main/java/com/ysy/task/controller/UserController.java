package com.ysy.task.controller;


import com.ysy.task.common.lang.Result;
import com.ysy.task.dto.UserAddDTO;
import com.ysy.task.entity.User;
import com.ysy.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yaosy
 * @since 2021-02-27
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/index")
    public Object index() {
        User user = userService.getById(1);
        return Result.succ(200, "成功", user);
    }

    @GetMapping("/{id}")
    public Object test(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        return Result.succ(200, "成功", user);
    }

    @PostMapping("/es")
    public Object addEsUser(@NotNull @RequestBody(required = false) UserAddDTO dto) {
        return Result.succ(200, "成功", userService.saveEsUser(dto));
    }

    @GetMapping("/all")
    public Object getEsUserAll() {
        return Result.succ(200, "成功", userService.getEsUserAll());
    }

    @PostMapping("/save")
    public Object save(@Validated @RequestBody User user) {
        return Result.succ(user);
    }

}
