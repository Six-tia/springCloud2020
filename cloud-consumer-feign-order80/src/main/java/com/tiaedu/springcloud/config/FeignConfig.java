package com.tiaedu.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置feign日志bean
 */
@Configuration
public class FeignConfig {
    /**
     * feign的日志级别有以下几种：
     *     NONE：默认的，不显示任何日志
     *     BASIC：仅记录请求方法，URL,响应状态码和执行时间
     *     HEADER：BASIC + 还有请求和响应的头信息
     *     FULL：HEADERS + 请求和响应的正文及元数据
     */

    @Bean
    //Logger.Level是enum类型
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
