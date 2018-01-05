package com.mdm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.mdm.dao"})
@EnableAsync
public class Start {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Start.class);
        springApplication.run(args);
    }
}
