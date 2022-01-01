package cn.ecut.lrj.config;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket AdminApiConfig(){
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("Authorization").description("token").modelRef(new ModelRef("string"))
                .parameterType("header").required(true).build();
        List<Parameter> parameters = Lists.newArrayList();
        parameters.add(parameterBuilder.build());
        return  new Docket(DocumentationType.SWAGGER_2)
                .groupName("AdminApi")
                .apiInfo(adminApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/.*")))
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo adminApiInfo(){
        return new ApiInfoBuilder()
                .title("点餐系统后台管理系统API文档")
                .description("本文档描述了点餐系统后台管理系统的各个模块的接口调用方式")
                .version("1.0")
                .contact(new Contact("lrj","http://","123@163.com")).build();
    }

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("点餐系统API文档")
                .description("本文档描述了点餐系统各个模块的接口调用方式")
                .version("1.0")
                .contact(new Contact("lrj","http://","123@163.com")).build();
    }

    @Bean
    public Docket WebApiConfig(){
        return  new Docket(DocumentationType.SWAGGER_2)
                .groupName("WebApi")
                .select()
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();
    }
}
