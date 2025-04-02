package com.example.todo.config;

import com.example.todo.Filter.CustomFilter;
import com.example.todo.Filter.LoginFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean customFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new CustomFilter());

        filterRegistrationBean.setOrder(1);

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
