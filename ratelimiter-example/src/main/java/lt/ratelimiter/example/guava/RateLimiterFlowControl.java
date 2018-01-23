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

    public static void main(String[] args) throws Exception{
        ExecutorService executor = Executors.newCachedThreadPool();

//        RateLimiter rateLimiter = RateLimiter.create(10);//每秒放入的令牌数
        //在20秒中内许可会平稳增加到100,当一段时间没有请求许可又将恢复为0
        RateLimiter rateLimiter = RateLimiter.create(100,20,TimeUnit.SECONDS);//预热
        for(int i=0;i<10;i++){
            int no=i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    double acquire = rateLimiter.acquire(2);
                    System.out.println(no+"================"+acquire);

                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                    }
//                    boolean b = rateLimiter.tryAcquire(1);//必须在无延迟的情况下立即获取才返回true
//                    boolean b = rateLimiter.tryAcquire(1,2, TimeUnit.SECONDS);//允许延迟2秒
//                    if (!b){
//                        System.out.println("访问频率过高:"+no);
//                    }else {
//                        System.out.println("========"+no);
//                    }
                }
            };
            executor.submit(runnable);
        }
        executor.shutdown();
    }
}
