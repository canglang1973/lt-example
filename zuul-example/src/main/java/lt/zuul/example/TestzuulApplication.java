package lt.zuul.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author leitao.
 * @time: 2017/11/28  13:18
 * @version: 1.0
 * @description: @EnableZuulProxy开启zuul网关代理
 **/
@EnableZuulProxy
@SpringBootApplication
public class TestzuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestzuulApplication.class, args);
    }
}
