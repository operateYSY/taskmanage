package com.ysy.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ysy.task.dto.UserAddDTO;
import com.ysy.task.entity.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yaosy
 * @since 2021-02-27
 */
public interface UserService extends IService<User> {

    boolean saveEsUser(UserAddDTO dto);

    List<User> getEsUserAll();
}
