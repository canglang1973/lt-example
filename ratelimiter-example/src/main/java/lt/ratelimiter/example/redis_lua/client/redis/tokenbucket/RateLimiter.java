package lt.ratelimiter.example.redis_lua.client.redis.tokenbucket;

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
 * @time: 2017/12/12  13:58
 * @version: 1.0
 * @description: 此类与rate_limiter.lua脚本方法一样    令牌桶算法
 **/
public class RateLimiter {

    private HashOperations<String, String, String> hashOperations;


    public RateLimiter(RedisTemplate<String,String> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }
    /**
     * 判断source_str 中是否contains sub_str
     *
     * @param source_str
     * @param sub_str
     * @return
     */
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
     * --- 获取令牌
     * --- 返回码
     * --- 0 没有令牌桶配置
     * --- -1 表示取令牌失败，也就是桶里没有令牌
     * --- 1 表示取令牌成功
     * --- @param key 令牌的唯一标识
     * --- @param permits  请求令牌数量
     * --- @param curr_mill_second 当前毫秒数
     * --- @param context 使用令牌的应用标识
     *
     * @return
     */
    public synchronized int acquire(String key, long permits, long curr_mill_second, String context) {
        key = getKey(key);
        int result = -1;
        List<String> rate_limit_info = hashOperations.multiGet(key,getHashKeys());
        if (null == rate_limit_info || rate_limit_info.size() == 0) {
            return result;
        }
        String apps = rate_limit_info.get(4);
        //标识没有配置令牌桶
        if (!contains(apps, context)) {
            return result;
        }
        String last_mill_second = rate_limit_info.get(0);
        long curr_permits = Long.parseLong(rate_limit_info.get(1));
        long max_permits = Long.parseLong(rate_limit_info.get(2));
        String rate = rate_limit_info.get(3);

        long local_curr_permits = max_permits;
        //--- 令牌桶刚刚创建，上一次获取令牌的毫秒数为空
        //--- 根据和上一次向桶里添加令牌的时间和当前时间差，触发式往桶里添加令牌，并且更新上一次向桶里添加令牌的时间
        // --- 如果向桶里添加的令牌数不足一个，则不更新上一次向桶里添加令牌的时间
        if (StringUtils.isNotBlank(last_mill_second)) {
            long reverse_permits = ((curr_mill_second - Long.parseLong(last_mill_second)) / 1000L) * Long.parseLong(rate);
            long expect_curr_permits = reverse_permits + curr_permits;
            local_curr_permits = Math.min(expect_curr_permits, max_permits);
            //大于0表示不是第一次获取令牌，也没有向桶里添加令牌
            if (reverse_permits > 0) {
                hashOperations.put(key,"last_mill_second", curr_mill_second + "");
            }
        } else {
            hashOperations.put(key,"last_mill_second", curr_mill_second + "");
        }
        if (local_curr_permits - permits >= 0) {
            result = 1;
            hashOperations.put(key,"curr_permits", (local_curr_permits - permits) + "");
        } else {
            hashOperations.put(key,"curr_permits", local_curr_permits + "");
        }
        return result;
    }

    /**
     * 初始化令牌桶配置
     *
     * @param key         令牌的唯一标识
     * @param max_permits 桶大小
     * @param rate        向桶里添加令牌的速率
     * @param apps        可以使用令牌桶的应用列表，应用之前用逗号分隔
     */
    public synchronized  void init(String key, long max_permits, long rate, String apps) {
        key = getKey(key);
        List<String> rate_limit_info = hashOperations.multiGet(key,getHashKeys());
        String curr_permits = rate_limit_info.get(1);
        if (null == rate_limit_info || StringUtils.isEmpty(curr_permits)) {
            Map<String, String> map = new HashMap<String, String>(4);
            map.put("max_permits", max_permits + "");
            map.put("rate", rate + "");
            map.put("curr_permits", max_permits + "");
            map.put("apps", apps);
            hashOperations.putAll(key,map);
        }
    }

    private List<String> getHashKeys(){
        List<String> hashKeys = new ArrayList<String>(5);
        hashKeys.add("last_mill_second");
        hashKeys.add("curr_permits");
        hashKeys.add("max_permits");
        hashKeys.add("rate");
        hashKeys.add("apps");
        return hashKeys;
    }
    /**
     * 删除令牌桶
     *
     * @param key
     */
    public synchronized  void delete(String key) {
        hashOperations.delete(getKey(key));
    }

    private String getKey(String key) {
        return RateLimiterConstants.RATE_LIMITER_KEY_PREFIX + key;
    }
}
