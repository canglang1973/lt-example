package lt.ratelimiter.example.redis_lua.client.jedis;

import lt.ratelimiter.example.redis_lua.client.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author leitao.
 * @time: 2017/12/12  14:55
 * @version: 1.0
 * @description:
 **/
@Component("rateLimiterRedisClient")
public class RateLimiterRedisClient {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private RateLimiter rateLimiter;

    public RateLimiterRedisClient(RedisTemplate<String,String> redisTemplate){
        this.rateLimiter = new RateLimiter(redisTemplate);
    }

    public boolean acquire(String context, String key) {
        Token token = acquireToken(context, key);
        return token.isPass() || token.isAccessRedisFail();
    }
    public Token acquireToken(String context, String key) {
        return acquireToken(context, key, 1);
    }
    public Token acquireToken(String context, String key, Integer permits){
        Token token;
        try {
            Long currMillSecond = stringRedisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.time();
                }
            });
            int acquire = rateLimiter.acquire(key, permits, currMillSecond, context);
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

    public void  init(String key, long max_permits, long rate, String apps){
        rateLimiter.init(key, max_permits, rate, apps);
    }

    public void delete(String key){
        rateLimiter.delete(key);
    }


}
