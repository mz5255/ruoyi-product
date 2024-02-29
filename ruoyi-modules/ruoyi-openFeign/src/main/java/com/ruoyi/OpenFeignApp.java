package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class OpenFeignApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(OpenFeignApp.class,args);
        System.out.println( "Hello World!" );
    }
}
