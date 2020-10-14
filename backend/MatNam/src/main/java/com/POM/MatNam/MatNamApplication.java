package com.POM.MatNam;

import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.POM.MatNam.interceptor.JwtInterceptor;


@SpringBootApplication
public class MatNamApplication implements WebMvcConfigurer{
	@PostConstruct
	public void setTimeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(MatNamApplication.class, args);
	}
	
	@Autowired
	private JwtInterceptor jwtInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> addList = Arrays.asList("");
		List<String> excludeList = Arrays.asList("");
		registry.addInterceptor(jwtInterceptor).addPathPatterns(addList).excludePathPatterns(excludeList);
	}
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*")
                .exposedHeaders("jwt-auth-token", "nickname");
    }
}
