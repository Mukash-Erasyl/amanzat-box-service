package org.example.amanzatboxservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ConfigurationPropertiesScan
public class AmanzatBoxServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AmanzatBoxServiceApplication.class, args);
    }
}
