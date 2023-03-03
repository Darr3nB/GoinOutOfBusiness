package com.ZSoos_Darren.GoingOutOfBusiness.helper;

import com.ZSoos_Darren.GoingOutOfBusiness.security.AuthorizationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilterRegistrationBean() {
        FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthorizationFilter());
        registrationBean.addUrlPatterns("/api/*"); //TODO Only apply this filter to URLs starting with "/api/", but add more routes
        return registrationBean;
    }
}
