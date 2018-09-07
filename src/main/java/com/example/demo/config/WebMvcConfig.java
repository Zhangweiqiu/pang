package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.MyInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/*").excludePathPatterns("/passtopay").excludePathPatterns("/payNotify.do").excludePathPatterns("/login").excludePathPatterns("/index.html").excludePathPatterns("/pay.html").excludePathPatterns("/goBack.html");
	}
	
}
