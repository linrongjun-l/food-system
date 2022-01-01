package cn.ecut.lrj.config;

import cn.ecut.lrj.common.config.BaseSwaggerConfig;
import cn.ecut.lrj.common.domain.SwaggerProperties;

/**
 * Swagger API文档相关配置
 * Created by macro on 2018/4/26.
 */
//@Configuration
//@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.tulingxueyuan.mall.modules")
                .title("图灵商城基础版项目后台管理系统")
                .description("tuling_mall项目后台管理接口文档")
                .contactName("xushu")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }
}
