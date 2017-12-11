package lt.ratelimiter.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Administrator on 2017/12/10.
 */

@ImportResource(value="classpath:server/applicationContext.xml")
@SpringBootApplication
public class ApplicationRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationRunner.class, args);
//        RateLimiterClient rateLimiterClient = (RateLimiterClient) context.getBean("rateLimiterClient");
//        System.out.println(rateLimiterClient);
//        boolean acquire = rateLimiterClient.acquire("leitao", "lttest");
//        System.out.println(acquire);
    }
}
