package lt.zuul.example.dynamicrate;

import com.google.common.util.concurrent.RateLimiter;
import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @author leitao.
 * @time: 2017/12/4  17:16
 * @version: 1.0
 * @description:
 **/
@Data
public class Rate {
    /**
     * 是否是脏数据
     */
    private boolean dirty = false;
    /**
     * 动态设置每秒放入令牌桶的令牌数
     */
    private double rate;
    /**
     * 默认每秒放入令牌桶的令牌数
     */
    private double defaultRate=1;
    /**
     *预热时间
     */
    private long warmupPeriod=10;

    /**
     * 预热时间单位
     */
    private TimeUnit warmupTimeUnit = TimeUnit.SECONDS;
    /**
     * tryAcquire()超时时间
     */
    private long acquireTimeout = 0;

    /**
     * tryAcquire超时时间单位
     */
    private TimeUnit acquireTimeoutTimeUnit = TimeUnit.SECONDS;

    /**
     * 需要的许可证数量
     */
    private int acquirePermits = 1;

    private  RateLimiter rateLimiter = null;

    public Rate(){
        super();
    }
    public  Rate(double rate,double defaultRate,long warmupPeriod,boolean dirty){
        this.rate =rate;
        this.defaultRate = defaultRate;
        this.warmupPeriod=warmupPeriod;
        this.dirty = dirty;
    }

    public synchronized RateLimiter getRateLimiter() {
        if (null ==rateLimiter || dirty){
            System.out.println("rateLimiter is  null or rate is dirty!");
            rateLimiter = RateLimiter.create(this.dirty?this.rate:this.defaultRate,this.warmupPeriod, TimeUnit.SECONDS);
            dirty = false;
            return rateLimiter;
        }
        System.out.println("rateLimiter is not null!");
        return  rateLimiter;
    }
}
