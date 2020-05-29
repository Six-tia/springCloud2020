package com.tiaedu.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义配置类（Ribbon负载均衡）
 * 此类不能放于@ComponentScan所扫描的当前包及子包下
 * 否则自定义的配置类会被所有ribbon客户端所共享，达不到特殊定制的目的
 */
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule(){
        return new RandomRule();
    }

}
