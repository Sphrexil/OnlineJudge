package com.oj.config;


import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Title: http设置
 * @Description: TODO
 * @Author: zhenyu
 * @DateTime: 2022/8/28 17:26
 */

@Configuration
public class webConfig implements WebMvcConfigurer {
    //解决跨域问题
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    /* fastjson配置 */
    @Bean//使用@Bean注入fastJsonHttpMessageConvert
    public HttpMessageConverter fastJsonHttpMessageConverters() {
        //1.需要定义一个Convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 该配置显示会过滤掉为null的数据
        // 以下是可以修改的配置返回内容的过滤
        // WriteNullListAsEmpty  ：List字段如果为null,输出为[],而非null
        // WriteNullStringAsEmpty ： 字符类型字段如果为null,输出为"",而非null
        // DisableCircularReferenceDetect ：消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
        // WriteNullBooleanAsFalse：Boolean字段如果为null,输出为false,而非null
        // WriteMapNullValue：是否输出值为null的字段,默认为false
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        // 配置序列化时间格式
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // 使用全局和配置
        SerializeConfig.globalInstance.put(Long.class, ToStringSerializer.instance);
        fastJsonConfig.setSerializeConfig(SerializeConfig.globalInstance);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverters());
    }
}
