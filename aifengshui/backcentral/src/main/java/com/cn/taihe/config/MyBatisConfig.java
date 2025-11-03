package com.cn.taihe.config;

import lombok.Data;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("com.cn.taihe.back.user.mapper")
@EnableTransactionManagement
@Data
@Component
@ConfigurationProperties(prefix = "mybatis-plus")
public class MyBatisConfig {

    @Bean
    public org.apache.ibatis.session.Configuration mybatisConfiguration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(true);
        configuration.setLazyLoadingEnabled(false);
        configuration.setAggressiveLazyLoading(false);
        configuration.setMultipleResultSetsEnabled(true);
        configuration.setUseGeneratedKeys(true);
        configuration.setDefaultStatementTimeout(30);
        return configuration;
    }
}
