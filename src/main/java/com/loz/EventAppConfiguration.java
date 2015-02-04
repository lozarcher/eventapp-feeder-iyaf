package com.loz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan("com.loz")
public class EventAppConfiguration {
    public static void main(String[] args) {
        SpringApplication.run(EventAppConfiguration.class, args);
    }
}
