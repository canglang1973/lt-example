package com.canglang.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author leitao.
 * @category
 * @time: 2018/11/20 0020-9:17
 * @version: 1.0
 * @description:
 **/
@SpringBootApplication
@EnableEurekaServer //开启eureka支持
public class CoudCanlangEurekaApplication {

    public static void main(String[] args){
        SpringApplication.run(CoudCanlangEurekaApplication.class, args);
    }

}
