package com.ysy.task.dao;

import com.ysy.task.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * es用户dao
 *
 * @author ysy
 * @date 2023-01-10
 */
@Repository
public interface EsUserDao extends ElasticsearchRepository<User, Long> {
}
