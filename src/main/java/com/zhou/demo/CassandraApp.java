package com.zhou.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @auther:
 * @description:
 * @date: 17:26 2019/1/8
 */

@SpringBootApplication
@EnableSwagger2
public class CassandraApp {
    public static void main(String[] args) {
        SpringApplication.run(CassandraApp.class, args);
    }
}

