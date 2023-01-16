package com.ysy.task.controller;


import com.ysy.task.common.lang.Result;
import com.ysy.task.dto.UserAddDTO;
import com.ysy.task.entity.User;
import com.ysy.task.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

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
@Api("用户管理")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/index")
    public Result<User> index() {
        User user = userService.getById(1);
        return Result.succ(200, "成功", user);
    }

    @GetMapping("/{id}")
    public Result<User> test(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        return Result.succ(user);
    }

    @PostMapping("/es/add")
    @ApiOperation("es新增用户")
    public Result<Boolean> addEsUser(@NotNull @RequestBody(required = false) UserAddDTO dto) {
        return Result.succ(userService.saveEsUser(dto));
    }

    @GetMapping("/es/all")
    @ApiOperation("es获取全部用户")
    public Result<List<User>> getEsUserAll() {
        return Result.succ(userService.getEsUserAll());
    }

    @GetMapping("/es/search")
    @ApiOperation("es查询用户")
    public Result<Object> searchEsUser() {
        return Result.succ(userService.searchEsUser());
    }

    @PostMapping("/save")
    public Result<User> save(@Validated @RequestBody User user) {
        return Result.succ(user);
    }

}
