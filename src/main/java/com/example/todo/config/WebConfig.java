package com.example.todo.config;

import com.example.todo.filter.CustomFilter;
import com.example.todo.filter.LoginFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 필터 설정 클래스
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean customFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        // 커스텀 필터를 등록
        filterRegistrationBean.setFilter(new CustomFilter());

        // 필터의 실행 순서 지정
        filterRegistrationBean.setOrder(1);

        // 어느 URL에 적용할것인지
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new LoginFilter());

        filterRegistrationBean.setOrder(2);

        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
