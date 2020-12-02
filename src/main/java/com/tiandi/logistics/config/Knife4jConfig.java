package com.tiandi.logistics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Knife4jConfiguration配置类
 *
 *  访问路径为 ： http://localhost:8082/doc.html
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/12/1 9:12
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("TP物流后端接口文档")
                        .description("TP物流后端接口描述")
                        .termsOfServiceUrl("http://****")
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("TP物流")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.tiandi.logistics.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
