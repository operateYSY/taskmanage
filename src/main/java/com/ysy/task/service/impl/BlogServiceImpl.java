package com.ysy.task.service.impl;

import com.ysy.task.entity.Blog;
import com.ysy.task.mapper.BlogMapper;
import com.ysy.task.service.BlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
