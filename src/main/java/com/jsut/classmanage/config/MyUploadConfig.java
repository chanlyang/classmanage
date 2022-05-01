package com.jsut.classmanage.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class MyUploadConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("获取图片:{}",ClassUtils.getDefaultClassLoader().getResource("static/").getPath());
        registry.addResourceHandler("/uploadImg/**").addResourceLocations("file:"+ClassUtils.getDefaultClassLoader().getResource("static/").getPath());
    }

}