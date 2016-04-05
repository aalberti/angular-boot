package com.aa.angularboot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class StatsApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StatsApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}