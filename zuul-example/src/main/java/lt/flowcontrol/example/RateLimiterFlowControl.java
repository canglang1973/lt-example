package lt.flowcontrol.example;

import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.*;

/**
 * @author leitao.
 * @time: 2017/11/30  11:37
 * @version: 1.0
 * @description: 令牌桶算法限流
 *
 * 令牌实现:SmoothBursty
 * 漏桶实现:SmoothWarmingUp
 **/
public class RateLimiterFlowControl {

    public static void main(String[] args){
        ExecutorService executor = Executors.newCachedThreadPool();

        RateLimiter rateLimiter = RateLimiter.create(20);//每秒放入的令牌数
//        RateLimiter rateLimiter = RateLimiter.create(20,10,TimeUnit.SECONDS);//预热
        for(int i=0;i<20;i++){
            int no=i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    rateLimiter.acquire(50);
                    System.out.println("========"+no+"rate:"+rateLimiter.getRate());
                }
            };
            executor.submit(runnable);
        }

    }
}
