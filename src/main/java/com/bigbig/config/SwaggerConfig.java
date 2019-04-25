package com.bigbig.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.bigbig.controller"))
                .paths(PathSelectors.any())
                .build();
    }
//    @Bean
//    public Docket getClaimsAPI() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                // .apis(RequestHandlerSelectors.basePackage("com....service"))
//                .apis(RequestHandlerSelectors.any())
//                .paths(Predicates.not(regex("/error*")))
//                // .paths(regex("/claims/*"))
//                .build()
//                .apiInfo(apiInfo());
//
//    }
//
    //构建 api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("Spring Boot 测试使用 Swagger2 构建RESTful API")
                //创建人
                .contact(new Contact("bigbig chen", "http://www.baidu.com", "yaya_simada@qq.com"))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }

}
