package com.oj.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
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

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * @Title: swagger的全局配置
 * @Description:
 * @Author: zhenyu
 * @DateTime: 2022/8/28 17:25
 */
@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig implements ApplicationListener<WebServerInitializedEvent> {


    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<String>(Arrays.asList("application/json","application/xml"));

    /* swagger接口提取路径 */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).enable(true)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .select()
                //apis： 添加swagger接口提取范围
                .apis(RequestHandlerSelectors.basePackage("com.oj"))
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    /* swagger服务的基本描述 */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("OnlineJudge")
                .description("在线的判题系统")
                .termsOfServiceUrl("http://localhost:8999/")
                .contact(new Contact("zhenyu", "无", "704907239@qq.com"))
                .version("1.0")
                .build();
    }

    /* swagger地址打印 */
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        try {
            // 获取IP
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            // 获取端口号
            int port = event.getWebServer().getPort();
            // 获取应用名
            String applicationName = event.getApplicationContext().getApplicationName();
            // 打印 swagger2文档地址
            log.info("项目启动启动成功！swagger2 接口文档地址: http://" + hostAddress + ":" + port + applicationName + "/doc.html");
            log.info("项目启动启动成功！nacos 地址: http://" + hostAddress + ":" + "8848");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
