package lt.ratelimiter.example.redis_lua.client;

/**
 * @author leitao
 */
public class RateLimiterConstants {
    public static final String RATE_LIMITER_KEY_PREFIX = "rate_limiter:";


    public static final String RATE_LIMITER_INIT_METHOD = "init";
    public static final String RATE_LIMITER_DELETE_METHOD = "delete";
    public static final String RATE_LIMITER_ACQUIRE_METHOD = "acquire";
}
