package lt.ratelimiter.example.redis_lua.client.redis.leakbucket;

import lt.ratelimiter.example.redis_lua.client.Token;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author leitao.
 * @time: 2017/12/19  16:22
 * @version: 1.0
 * @description: 漏桶算法客户端
 **/
@Component("leakyBucketRateLimiterRedisClient")
public class LeakyBucketRateLimiterRedisClient {

    private  LeakyBucketRateLimiter rateLimiter;

    /**
     * Spring容器会自动将redisTemplate注入到此构造方法中
     * @param redisTemplate
     */
    public  LeakyBucketRateLimiterRedisClient(RedisTemplate<String,String> redisTemplate) {
        this.rateLimiter = new LeakyBucketRateLimiter(redisTemplate);
    }

    public boolean acquire(String context, String key) {
        Token token = acquireToken(context, key);
        return token.isPass() || token.isAccessRedisFail();
    }
    public Token acquireToken(String context, String key) {
        return acquireToken(context, key, 1);
    }
    public  Token  acquireToken(String context, String key, Integer permits){
        Token token;
        try {
            int acquire = rateLimiter.acquire(key, permits, context);
            if (acquire == 1) {
                token = Token.PASS;
            } else if (acquire == -1) {
                token = Token.FUSING;
            } else {
                token = Token.NO_CONFIG;
            }
        }catch (Exception e){
            token = Token.ACCESS_REDIS_FAIL;
        }
        return token;
    }

    public void init(String key, String apps, long maxPermits){
        rateLimiter.init(key, apps, maxPermits);
    }

    public void delete(String key){
        rateLimiter.delete(key);
    }
    public void access(String key,long permits){
        rateLimiter.access(key, permits);
    }

}
