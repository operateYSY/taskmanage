package com.ysy.task.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * ES配置
 *
 * @author ysy
 * @date 2023-01-10
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    private String host = "120.77.93.244";
    private Integer port = 9200;
    private String password = "8090";
    private String username = "admin";

    @Override
    public RestHighLevelClient elasticsearchClient() {
        final CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(host, port, "http"))
                .setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(provider));
        return new RestHighLevelClient(restClientBuilder);
    }
}
