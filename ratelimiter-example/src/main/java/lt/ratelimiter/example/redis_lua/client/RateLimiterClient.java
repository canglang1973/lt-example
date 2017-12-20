package lt.ratelimiter.example.redis_lua.client;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

/**
 * @author leitao
 */
public class RateLimiterClient {

    private Logger logger = LoggerFactory.getLogger(RateLimiterClient.class);

    private StringRedisTemplate stringRedisTemplate;

    private RedisScript<Long> rateLimiterClientLua;

    public RateLimiterClient(StringRedisTemplate stringRedisTemplate, RedisScript<Long> rateLimiterClientLua) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.rateLimiterClientLua = rateLimiterClientLua;
    }

    /**
     * 获取令牌，访问redis异常算做成功
     * 默认的permits为1
     *
     * @param context
     * @param key
     * @return
     */
    public  boolean acquire(String context, String key) {
        Token token = acquireToken(context, key);
        return token.isPass() || token.isAccessRedisFail();
    }


    /**
     * 获取{@link Token}
     * 默认的permits为1
     *
     * @param context
     * @param key
     * @return
     */
    public  Token acquireToken(String context, String key) {
        return acquireToken(context, key, 1);
    }

    /**
     * 获取{@link Token}
     *
     * @param context
     * @param key
     * @param permits
     * @return
     */
    public synchronized Token acquireToken(String context, String key, Integer permits) {
        Token token;
        try {
            Long currMillSecond = stringRedisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.time();
                }
            });
            Long acquire = stringRedisTemplate.execute(rateLimiterClientLua, ImmutableList.of(getKey(key)), RateLimiterConstants.RATE_LIMITER_ACQUIRE_METHOD, permits.toString(), currMillSecond.toString(), context);

            if (acquire == 1) {
                token = Token.PASS;
            } else if (acquire == -1) {
                token = Token.FUSING;
            } else {
                logger.error("no rate limit config for context={}", context);
                token = Token.NO_CONFIG;
            }
        } catch (Throwable e) {
            logger.error("get rate limit token from redis error,key=" + key, e);
            token = Token.ACCESS_REDIS_FAIL;
        }
        return token;
    }

    public void init(String key, long maxPermits, long rate, String apps) {
        stringRedisTemplate.execute(rateLimiterClientLua,
                ImmutableList.of(getKey(key)),
                RateLimiterConstants.RATE_LIMITER_INIT_METHOD, maxPermits, rate, apps);

    }

    private String getKey(String key) {
        return RateLimiterConstants.RATE_LIMITER_KEY_PREFIX + key;
    }


}
