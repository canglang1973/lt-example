package lt.ratelimiter.example.client.redis;

import lt.ratelimiter.example.client.Token;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author leitao.
 * @time: 2017/12/12  14:55
 * @version: 1.0
 * @description:
 **/
public class RateLimiterRedisClient {
    private  RateLimiter rateLimiter = new RateLimiter(new Jedis("127.0.0.1"));

    public RateLimiter getRateLimiter() {
        return rateLimiter;
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
            List<String> time = rateLimiter.getJedis().time();
            long currMillSecond = Long.parseLong(time.get(0))*1000+Long.parseLong(time.get(1))/1000;
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
}
