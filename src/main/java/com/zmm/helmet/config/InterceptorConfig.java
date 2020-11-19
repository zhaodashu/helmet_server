package com.zmm.helmet.config;

import com.zmm.helmet.interceptors.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(new JWTInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/user/**");  //应该所有用户请求放行
//    }
}
