package lt.ratelimiter.example.semphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author leitao.
 * @time: 2017/11/30  11:24
 * @version: 1.0
 * @description: Java 并发库的Semaphore实现流控
 **/
public class SemphoreFlowControl {

    public  static void main(String[] args){
        ExecutorService executor = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(20);
        for (int i=0;i<20;i++){
            int no = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println("Accessing:"+no);
                        Thread.sleep((long) (Math.random()*1000));
                        semaphore.release();
                        System.out.println("*"+semaphore.availablePermits());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            executor.submit(runnable);
        }
        executor.shutdown();
    }
}
