package com.ysy.task.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ysy
 * @date 2022-12-02
 */
@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    @Bean(value = "defaultApi1")
    public Docket defaultApi1() {
        List<Parameter> list = new ArrayList<>();
        //设置需要的请求头
//    ParameterBuilder tokenPar = new ParameterBuilder();
//    Parameter pa = tokenPar.name("Authorization").description("登录校验")
//            .modelRef(new ModelRef("String")).parameterType("header")
//            .required(false).defaultValue("Bearer ").build();
//    list.add(pa);
        return new Docket(DocumentationType.SWAGGER_2)
                //分组名称
                .groupName("taskManager")
                .useDefaultResponseMessages(false)
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.ysy.task.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(list);
    }

}
