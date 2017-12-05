package lt.zuul.example.dynamicrate;

import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.TimeUnit;

/**
 * @author leitao.
 * @time: 2017/12/4  17:16
 * @version: 1.0
 * @description:
 **/
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

    public double getDefaultRate() {
        return defaultRate;
    }

    public double getRate() {
        return this.rate;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setWarmupPeriod(long warmupPeriod) {
        this.warmupPeriod = warmupPeriod;
    }

    public long getWarmupPeriod() {
        return warmupPeriod;
    }
}
