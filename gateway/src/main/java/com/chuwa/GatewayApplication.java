package com.chuwa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.chuwa", "com.chuwa.filters", "com.chuwa.utils"})
public class GatewayApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
