package com.ruoyi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.ruoyi.mapper")
@EnableFeignClients
public class AppDelivery
{
    public static void main( String[] args )
    {
        SpringApplication.run(AppDelivery.class,args);
        System.out.println( "Hello World!" );
    }
}
