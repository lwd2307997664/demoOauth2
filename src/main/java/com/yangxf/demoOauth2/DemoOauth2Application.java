package com.yangxf.demoOauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
public class DemoOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(DemoOauth2Application.class, args);
    }

}
