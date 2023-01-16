package com.ysy.task;

import cn.hutool.json.JSONUtil;
import com.ysy.task.config.ElasticsearchConfig;
import com.ysy.task.entity.User;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.client.indices.DetailAnalyzeResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ysy
 * @date 2023-01-11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ElasticsearchConfig.class})
@SpringBootTest
public class ElasticsearchTest {
    @Autowired
    private RestHighLevelClient elasticsearchClient;


    @Test
    public void testEsUserAll() {
//        Iterable<User> all = esUserDao.findAll();
//        List<User> users = IterUtil.toList(all);
//        System.out.println(users);
    }

    @Test
    public void testAddDocument() throws IOException {
        //创建对象
        User user = new User().setId(7L).setUserName("中国银行");
        //创建请求
        IndexRequest request = new IndexRequest("my_index");
        //规则 put /user_index/_doc/1
        request.id("7");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
        // 将我们的数据放入请求  json
        String jsonStr = JSONUtil.toJsonStr(user);
        request.source(jsonStr, XContentType.JSON);
        // 客户端发送请求 , 获取响应的结果
        IndexResponse indexResponse = elasticsearchClient.index(request, RequestOptions.DEFAULT);

        System.out.println(indexResponse.toString());//数据
        System.out.println(indexResponse.status());// 对应我们命令返回的状态CREATED
    }

    @Test
    public void testGetDocument() throws IOException {
        GetRequest getRequest = new GetRequest("user_index", "4");
        GetResponse getResponse = elasticsearchClient.get(getRequest,
                RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString()); // 打印文档的内容
        System.out.println(getResponse); // 返回的全部内容和命令式一样的
    }

    @Test
    public void testIk() throws IOException {
        AnalyzeRequest analyzeRequest = AnalyzeRequest.withIndexAnalyzer("user_index", "ik_max_word", "中国人民");
        AnalyzeResponse analyze = elasticsearchClient.indices().analyze(analyzeRequest, RequestOptions.DEFAULT);
        List<AnalyzeResponse.AnalyzeToken> tokens = analyze.getTokens();
        DetailAnalyzeResponse detail = analyze.detail();
        System.out.println(JSONUtil.toJsonStr(tokens));
    }


    @Test
    public void test() {
        SearchRequest searchRequest = new SearchRequest("my_index");
        // 构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.highlighter();

        // 查询条件：可以使用 QueryBuilders 工具来实现
        // QueryBuilders.termQuery 精确
        // QueryBuilders.matchAllQuery () 匹配所有
        //MatchAllQueryBuilder matchQueryBuilder = QueryBuilders.matchAllQuery();
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        //MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("userName", "中国人民").analyzer("ik_max_word");
        sourceBuilder.query(matchAllQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(JSONUtil.toJsonStr(searchResponse.getHits()));
            System.out.println("===========================================");
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                System.out.println(documentFields.getSourceAsMap());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testAll() {
        SearchRequest searchRequest = new SearchRequest("user_index");
        // 构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.highlighter();

        // 查询条件：可以使用 QueryBuilders 工具来实现
        // QueryBuilders.termQuery 精确
        // QueryBuilders.matchAllQuery () 匹配所有
        //MatchAllQueryBuilder matchQueryBuilder = QueryBuilders.matchAllQuery();
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        // MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("userName", "中国");
        sourceBuilder.query(matchAllQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(JSONUtil.toJsonStr(searchResponse.getHits()));
            System.out.println("===========================================");
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                System.out.println(documentFields.getSourceAsMap());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
