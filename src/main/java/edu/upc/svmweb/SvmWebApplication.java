package edu.upc.svmweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController
@ServletComponentScan

public class SvmWebApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SvmWebApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SvmWebApplication.class, args);
    }
}
