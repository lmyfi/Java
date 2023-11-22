package com.imooc.mall.config;

import com.imooc.mall.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 管理员身份统一校验 配置类
 */
@Configuration
public class AdminFilterConfig {

    /**
     * 创建过滤器
     * @return
     */
    @Bean
    public AdminFilter adminFilter(){
        return new AdminFilter();
    }

    /**
     * 管理员校验过滤器配置注册
     * @return
     */
    @Bean(name = "adminFilterConf")
    public FilterRegistrationBean adminFilterConfig(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(); //过滤器注册对象
        filterRegistrationBean.setFilter(adminFilter());//设置过滤器
        filterRegistrationBean.addUrlPatterns("/admin/category/*");//将"/admin/category/*"形式的url都拦截，过滤
        filterRegistrationBean.addUrlPatterns("/admin/product/*");//商品
        filterRegistrationBean.addUrlPatterns("/admin/order/*");//订单
        filterRegistrationBean.addUrlPatterns("/admin/upload/*");//上传
        filterRegistrationBean.setName("adminFilterConf");//设置过滤配置器对象实例的名字
        return filterRegistrationBean;
    }
}
