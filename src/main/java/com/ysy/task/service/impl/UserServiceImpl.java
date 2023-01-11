package com.ysy.task.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.IterUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ysy.task.dao.EsUserDao;
import com.ysy.task.dto.UserAddDTO;
import com.ysy.task.entity.User;
import com.ysy.task.mapper.UserMapper;
import com.ysy.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yaosy
 * @since 2021-02-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private EsUserDao esUserDao;

    @Override
    public boolean saveEsUser(UserAddDTO dto) {
        User user = BeanUtil.copyProperties(dto, User.class);
        user.setId(IdWorker.getId());
        esUserDao.save(user);
        return true;
    }

    @Override
    public List<User> getEsUserAll() {
        Iterable<User> all = esUserDao.findAll();
        if (CollectionUtil.isEmpty(all)) {
            return Collections.EMPTY_LIST;
        }
        return IterUtil.toList(all);
    }
}
