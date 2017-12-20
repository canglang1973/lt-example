package lt.ratelimiter.example.redis_lua.client.jedis;

import lt.ratelimiter.example.redis_lua.client.RateLimiterConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leitao.
 * @time: 2017/12/19  9:35
 * @version: 1.0
 * @description:  漏桶算法-速率限制
 **/
public class LeakyBucketRateLimiter {

    private HashOperations<String, String, String> hashOperations;

    public LeakyBucketRateLimiter(RedisTemplate<String,String> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public boolean contains(String source_str, String sub_str) {
        if (StringUtils.isEmpty(source_str) || StringUtils.isEmpty(sub_str)) {
            return false;
        }
        if (source_str.contains(sub_str)) {
            String[] split = source_str.split(",");
            for (String str : split) {
                if (str.equals(sub_str)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 初始化方法
     *
     * @param key          存入redis键
     * @param apps         拦截的应用
     */
    public synchronized void init(String key, String apps, long maxPermits) {
        key = getKey(key);

        List<String> rate_limit_info=hashOperations.multiGet(key,getHashKeys());
        Map<String, String> map = new HashMap<String, String>();
        String curr_permits = rate_limit_info.get(0);
        if (null == rate_limit_info || StringUtils.isEmpty(curr_permits)) {
            map.put("apps", apps);
            map.put("curr_permits", 0+"");
            map.put("max_permits", maxPermits+"");
            hashOperations.putAll(key, map);
        }
    }

    public int acquire(String key, String context){
        return  acquire(key, 1,context);
    }


    public synchronized int acquire(String key, long permits, String context) {
        key = getKey(key);
        int result = -1;
        List<String> rate_limit_info=hashOperations.multiGet(key,getHashKeys());
        if (null == rate_limit_info || rate_limit_info.size() == 0) {
            return result;
        }
        String apps = rate_limit_info.get(2);
        //标识没有配置令牌桶
        if (!contains(apps, context)) {
            return result;
        }
        long curr_permits = Long.parseLong(rate_limit_info.get(0));
        long max_permits = Long.parseLong(rate_limit_info.get(1));
        //如果请求数已经达到了桶最大容量
        if (curr_permits+permits>max_permits){
            curr_permits = Math.min(curr_permits,max_permits);
            hashOperations.put(key, "curr_permits", curr_permits + "");
            return result;
        }
        result=1;
        hashOperations.put(key, "curr_permits",  curr_permits+permits + "");

        return result;
    }
    public void access(String key,long permits){
        hashOperations.increment(getKey(key), "curr_permits", -permits);
    }


    /**
     * 删除令牌桶
     *
     * @param key
     */
    public synchronized void delete(String key) {
        hashOperations.delete(getKey(key));
    }

    private List<String> getHashKeys(){
        List<String> hashKeys = new ArrayList<String>(5);
        hashKeys.add("curr_permits");
        hashKeys.add("max_permits");
        hashKeys.add("apps");
        return hashKeys;
    }
    private String getKey(String key) {
        return RateLimiterConstants.RATE_LIMITER_KEY_PREFIX + key;
    }


}
