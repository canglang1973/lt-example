package com.canglang.common.executor_policy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author leitao.
 * @category
 * @time: 2019/1/15 0015-14:48
 * @version: 1.0
 * @description: 运行结果:
 * java.util.concurrent.RejectedExecutionException: Task MyRunnable@7cef4e59 rejected from java.util.concurrent.ThreadPoolExecutor@3cd1f1c8[Running, pool size = 1, active threads = 1, queued tasks = 1, completed tasks = 0]
 * at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2047)
 * at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:823)
 * at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1369)
 * at AbortPolicyDemo.main(AbortPolicyDemo.java:32)
 * task-0 is running.
 * task-1 is running.
 * 结果说明：
 * 将"线程池的拒绝策略"由DiscardPolicy修改为AbortPolicy之后，当有任务添加到线程池被拒绝时，会抛出RejectedExecutionException。
 **/
public class AbortPolicyDemo {
    private static final int THREADS_SIZE = 1;
    private static final int CAPACITY = 1;

    public static void main(String[] args) throws Exception {

        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE, THREADS_SIZE, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(CAPACITY));
        // 设置线程池的拒绝策略为"抛出异常"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        try {

            // 新建10个任务，并将它们添加到线程池中。
            for (int i = 0; i < 10; i++) {
                Runnable myrun = new MyRunnable("task-" + i);
                pool.execute(myrun);
            }
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
            // 关闭线程池
            pool.shutdown();
        }
    }
}


