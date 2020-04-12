package com.demo.aws.elasticsearch.data.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.demo.aws.elasticsearch.data.controller")).build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo("AWS Spring Elastic Search Data Sample",
                "Sample application to elucidate how AWS Elasticsearch can be leveraged with the spring-boot-starter-data-elasticsearch.",
                "V1", "https://github.com/PRASANTHRAJENDRAN/spring-boot-starter-data-elasticsearch-with-aws-demo",
                new Contact("Prasanth Rajendran's GitHub", "https://github.com/PRASANTHRAJENDRAN/spring-boot-starter-data-elasticsearch-with-aws-demo", "abc@123"),
                "", "",
                Collections.emptyList());
    }
}
