package lt.ratelimiter.example;

import lt.ratelimiter.example.redis_lua.client.RateLimiterClient;
import lt.ratelimiter.example.redis_lua.client.redis.leakbucket.LeakyBucketRateLimiterRedisClient;
import lt.ratelimiter.example.redis_lua.client.redis.tokenbucket.RateLimiterRedisClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/12/10.
 */

@ImportResource(value="classpath:server/applicationContext.xml")
@SpringBootApplication
public class ApplicationRunner {
    static  ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationRunner.class, args);
//        RedisLuaTest(context);
//        RedisJavaTest(context);
        LeakyBucketRedisJavaTest(context);
    }
    private static void RedisLuaTest(ConfigurableApplicationContext context){
        RateLimiterClient client = (RateLimiterClient) context.getBean("rateLimiterClient");
        for (int i=0 ;i<10;i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i=0 ;i<10000;i++) {
                        boolean acquire = client.acquire("leitao", "APP");
                        System.out.println(acquire);
                    }
                }
            });
        }
    }
    private static void RedisJavaTest(ConfigurableApplicationContext context){
        RateLimiterRedisClient client = (RateLimiterRedisClient) context.getBean("rateLimiterRedisClient");
        for (int i=0 ;i<10;i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i=0 ;i<1000;i++) {
                        boolean acquire = client.acquire("leitao", "APP");
                        System.out.println(acquire);
                    }
                }
            });
        }
    }
    private static void LeakyBucketRedisJavaTest(ConfigurableApplicationContext context){
        LeakyBucketRateLimiterRedisClient client = (LeakyBucketRateLimiterRedisClient) context.getBean("leakyBucketRateLimiterRedisClient");
        for (int i=0 ;i<10;i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i=0 ;i<1000;i++) {
                        boolean acquire = client.acquire("lei", "LeakyBucketRateLimiter");
                        if (acquire){
                            client.access("LeakyBucketRateLimiter",1);
                        }
                        System.out.println(acquire);
                    }
                }
            });
        }
    }
}
