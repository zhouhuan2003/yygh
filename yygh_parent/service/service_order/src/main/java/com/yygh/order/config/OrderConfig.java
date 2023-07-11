package com.yygh.order.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import javafx.scene.image.Image;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@MapperScan("com.yygh.order.mapper")
public class OrderConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
       return new PaginationInterceptor();
    }
}
