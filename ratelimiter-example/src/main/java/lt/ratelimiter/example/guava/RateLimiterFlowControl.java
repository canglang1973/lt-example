package lt.ratelimiter.example.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

//        RateLimiter rateLimiter = RateLimiter.create(10);//每秒放入的令牌数
        RateLimiter rateLimiter = RateLimiter.create(1,200,TimeUnit.SECONDS);//预热
        for(int i=0;i<20;i++){
            int no=i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    rateLimiter.acquire(10);
//                    boolean b = rateLimiter.tryAcquire(1);
//                    if (!b){
//                        System.out.println("访问频率过高:"+no);
//                    }else {
//                        System.out.println("========"+no+"rate:"+rateLimiter.getRate());
//
//                    }
                }
            };
            executor.submit(runnable);
        }

    }
}
