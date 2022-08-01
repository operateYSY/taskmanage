package com.ysy.task.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ysy.task.common.lang.Result;
import com.ysy.task.entity.Blog;
import com.ysy.task.service.BlogService;
import com.ysy.task.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yaosy
 * @since 2021-02-27
 */
@RestController

public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 5);
        IPage pageDate = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.succ(pageDate);
    }

    @GetMapping("/blogs/{id}")
    public Result detail(@PathVariable(name = "id") long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "已经被删除");
        return Result.succ(blog);
    }

    @RequiresAuthentication
    @GetMapping("/blogs/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog temp = null;
        if (blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()), "没有编辑权限");
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtils.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return Result.succ(null);
    }
}
