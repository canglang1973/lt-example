package lt.ratelimiter.example.autoconfigure;

import lt.ratelimiter.example.client.RateLimiterClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * @author leitao
 */
@Configuration
@AutoConfigureBefore(RedisAutoConfiguration.class)
@ConditionalOnBean(StringRedisTemplate.class)
public class RateLimiterAutoConfiguration {

    private StringRedisTemplate stringRedisTemplate;

    public RateLimiterAutoConfiguration(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private DefaultRedisScript<Long> rateLimiterLua() {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<Long>();
        defaultRedisScript.setLocation(new ClassPathResource("classpath:rate_limiter.lua"));
        defaultRedisScript.setResultType(Long.class);
        return defaultRedisScript;
    }

    @Bean
    @ConditionalOnMissingBean(name = "rateLimiterClient")
    public RateLimiterClient rateLimiterClient() {
        return new RateLimiterClient(stringRedisTemplate, rateLimiterLua());
    }

}
