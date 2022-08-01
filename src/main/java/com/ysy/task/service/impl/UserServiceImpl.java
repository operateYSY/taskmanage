package com.ysy.task.service.impl;

import com.ysy.task.entity.User;
import com.ysy.task.mapper.UserMapper;
import com.ysy.task.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yaosy
 * @since 2021-02-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
