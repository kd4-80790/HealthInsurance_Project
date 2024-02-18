package com.caresure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public List<RequestParameter> customHeader(){
        RequestParameter requestParameter=new RequestParameterBuilder()
                .in(ParameterType.HEADER)
                .name("Authorization")
                .description("Token start with Bearer")
                .required(false)
                .query(param -> param.model(model->model.scalarModel(ScalarType.STRING))).build();
        return List.of(requestParameter);
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.caresure"))
                .paths(PathSelectors.any())
                .build().apiInfo(metaData())
                .globalRequestParameters(customHeader());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Backoffice-Service Swagger Configuration")
                .description("\"Swagger configuration for backoffice-service application\"")
                .version("1.0.0")
                .build();
    }

}