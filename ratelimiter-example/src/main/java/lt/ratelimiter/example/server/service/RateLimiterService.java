package lt.ratelimiter.example.server.service;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import lt.ratelimiter.example.client.RateLimiterConstants;
import lt.ratelimiter.example.client.Token;
import lt.ratelimiter.example.client.redis.RateLimiter;
import lt.ratelimiter.example.client.redis.RateLimiterRedisClient;
import lt.ratelimiter.example.server.dao.RateDao;
import lt.ratelimiter.example.server.domain.RateLimiterInfo;
import lt.ratelimiter.example.server.form.RateLimiterForm;
import lt.ratelimiter.example.server.vo.RateLimiterVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import redis.clients.util.JedisByteHashMap;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author leitao
 */
@Slf4j
@Service
public class RateLimiterService implements InitializingBean {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource(name = "rateLimiterLua")
    private RedisScript<Long> rateLimiterLua;

    private RateDao rateDao = new RateDao();

    private RateLimiterRedisClient client = new RateLimiterRedisClient();

//    @Autowired
//    private RateLimiterInfoMapper rateLimiterInfoMapper;


    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);


    private String getKey(String key) {
        return RateLimiterConstants.RATE_LIMITER_KEY_PREFIX + key;
    }

    public List<RateLimiterVo> getRateLimiters(String context) {
        List<RateLimiterInfo> rateLimiterInfoList = rateDao.selectAll()
                .stream()
                .filter((rateLimiterInfo) -> Sets.newHashSet(rateLimiterInfo.getApps().split(",")).contains(context))
                .collect(Collectors.toList());

        List<Object> rateLimiterListFromRedis = stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
            for (RateLimiterInfo rateLimiterInfo : rateLimiterInfoList) {
                stringRedisConn.hGetAll(getKey(rateLimiterInfo.getName()));
            }
            return null;
        });


        List<RateLimiterVo> rateLimiterRespList = Lists.newArrayListWithCapacity(rateLimiterInfoList.size());

        for (int i = 0; i < rateLimiterListFromRedis.size(); i++) {
            Object object = rateLimiterListFromRedis.get(i);
            if ((object instanceof JedisByteHashMap)) {
                continue;
            }
            RateLimiterInfo rateLimiterInfo = rateLimiterInfoList.get(i);
            HashMap<String, String> rateLimiterMap = (HashMap<String, String>) object;
            rateLimiterRespList.add(RateLimiterVo.builder()
                    .name(rateLimiterInfo.getName())
                    .apps(rateLimiterMap.get("apps"))
                    .maxPermits(Integer.parseInt(rateLimiterMap.get("max_permits")))
                    .currPermits(Integer.parseInt(rateLimiterMap.get("curr_permits")))
                    .rate(Integer.parseInt(rateLimiterMap.get("rate")))
                    .lastPermitTimestamp(rateLimiterMap.get("last_mill_second"))
                    .build());
        }
        return rateLimiterRespList;
    }


    public void saveOrUpdateRateLimiter(RateLimiterForm form) {
        RateLimiterInfo rateLimiterInfo = rateDao.selectByName(form.getName());
        String apps = form.getContext();
        if (rateLimiterInfo != null) {
            Set<String> contexts = Sets.newHashSet(rateLimiterInfo.getApps().split(","));
            if (!contexts.contains(form.getContext())) {
                contexts.add(form.getContext());
            }
            apps = StringUtils.join(contexts, ",");
        }

        rateDao.saveOrUpdate(form.getName(), apps, form.getMaxPermits(), form.getRate());
        stringRedisTemplate.execute(rateLimiterLua,
                ImmutableList.of(getKey(form.getName())),
                RateLimiterConstants.RATE_LIMITER_INIT_METHOD, form.getMaxPermits() + "", form.getRate() + "", apps);
    }


    public void deleteRateLimiter(String context, String name) {
        RateLimiterInfo rateLimiterInfo = rateDao.selectByName(name);
        if (rateLimiterInfo != null) {
            Set<String> contexts = Sets.newHashSet(rateLimiterInfo.getApps().split(","));
            if (contexts.contains(context)) {
                contexts.remove(context);
            }
            if (contexts.isEmpty()) {
                rateDao.deleteByName(name);

            } else {
                rateDao.saveOrUpdate(name, StringUtils.join(contexts, ","), rateLimiterInfo.getMaxPermits(), rateLimiterInfo.getRate());
            }
            stringRedisTemplate.execute(rateLimiterLua,
                    ImmutableList.of(getKey(name)),
                    RateLimiterConstants.RATE_LIMITER_INIT_METHOD, rateLimiterInfo.getMaxPermits().toString(), rateLimiterInfo.getRate().toString(), StringUtils.join(contexts, ","));
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("diff db and redis job start.....");
                    List<RateLimiterInfo> rateLimiterInfoList = rateDao.selectAll();
                    for (RateLimiterInfo rateLimiterInfo : rateLimiterInfoList) {
                        RateLimiter rateLimiter = client.getRateLimiter();
                        rateLimiter.init(rateLimiterInfo.getName(),rateLimiterInfo.getMaxPermits(), rateLimiterInfo.getRate(), rateLimiterInfo.getApps());
//                        stringRedisTemplate.execute(rateLimiterLua,
//                                ImmutableList.of(getKey(rateLimiterInfo.getName())),
//                                RateLimiterConstants.RATE_LIMITER_INIT_METHOD, rateLimiterInfo.getMaxPermits().toString(), rateLimiterInfo.getRate().toString(), rateLimiterInfo.getApps());
                    }
                    log.info("diff db and redis job end.....");
                } catch (Exception e) {
                    log.error("diff db and redis error.....", e);
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    public void rateTest(String context,String key){
//        RateLimiterClient client = new RateLimiterClient(stringRedisTemplate,rateLimiterLua);
        RateLimiterRedisClient client = new RateLimiterRedisClient();
        Token token = client.acquireToken(context, key, 1);
        System.out.println(context+":"+key+":"+token.name());
    }
}
