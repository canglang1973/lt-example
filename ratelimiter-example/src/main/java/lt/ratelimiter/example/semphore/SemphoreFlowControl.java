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
        //线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        //最多只允许5个线程同时访问,即初始化5个许可证
        Semaphore semaphore = new Semaphore(5);
        //模拟20个客户端访问
        for (int i=0;i<20;i++){
            int no = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        //获取许可继续执行,如果没有获取到线程将一直阻塞在这儿
                        semaphore.acquire();
                        System.out.println("Accessing:"+no);
                        Thread.sleep((long) (Math.random()*1000));
                        //运行完释放许可
                        semaphore.release();
                        //打印出目前可用的许可
                        System.out.println("*"+semaphore.availablePermits());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            executor.submit(runnable);
        }
        //退出线程池
        executor.shutdown();
    }
}
