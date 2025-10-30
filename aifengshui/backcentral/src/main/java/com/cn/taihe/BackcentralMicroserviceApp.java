package com.cn.taihe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.cn.taihe.*"})
@MapperScan("com.cn.taihe.back.*.mapper")
public class BackcentralMicroserviceApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(BackcentralMicroserviceApp.class, args);
    }
}
