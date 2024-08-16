package com.chuwa.client;

import com.chuwa.config.DefaultFeignConfig;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@EnableFeignClients(defaultConfiguration = DefaultFeignConfig.class)
@FeignClient("kafka-server")
public interface KakfaClient {
    // 把要代理的kafka server的api定义在这里
}
