package com.aa.angularboot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class KickMe {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(KickMe.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}