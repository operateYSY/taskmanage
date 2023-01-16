package com.ysy.task.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ysy.task.dao.EsUserDao;
import com.ysy.task.dto.UserAddDTO;
import com.ysy.task.entity.User;
import com.ysy.task.mapper.UserMapper;
import com.ysy.task.service.UserService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RestHighLevelClient elasticsearchClient;

    @Override
    public Boolean saveEsUser(UserAddDTO dto) {
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


    @Override
    public Object searchEsUser() {

        SearchRequest searchRequest = new SearchRequest("user");
        // 构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.highlighter();

        // 查询条件：可以使用 QueryBuilders 工具来实现
        // QueryBuilders.termQuery 精确
        // QueryBuilders.matchAllQuery () 匹配所有
        MatchAllQueryBuilder matchQueryBuilder = QueryBuilders.matchAllQuery();
        // MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery ( );
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            System.out.println(JSONUtil.toJsonStr(hits));
            System.out.println("---");
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                System.out.println(documentFields.getSourceAsMap());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
