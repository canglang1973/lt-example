package com.canglang.common.executor_policy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author leitao.
 * @category
 * @time: 2019/1/15 0015-14:50
 * @version: 1.0
 * @description:
 *
 * 运行结果:
 * task-2 is running.
 * task-0 is running.
 * task-1 is running.
 * task-4 is running.
 * task-3 is running.
 * task-6 is running.
 * task-7 is running.
 * task-5 is running.
 * task-8 is running.
 * task-9 is running.
 *
 * 结果说明：
 * 将"线程池的拒绝策略"由DiscardPolicy修改为CallerRunsPolicy之后，当有任务添加到线程池被拒绝时，
 * 线程池会将被拒绝的任务添加到"线程池正在运行的线程"中取运行。
 *
 **/
public class CallerRunsPolicyDemo {private static final int THREADS_SIZE = 1;
    private static final int CAPACITY = 1;

    public static void main(String[] args) throws Exception {

        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE, THREADS_SIZE, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(CAPACITY));
        // 设置线程池的拒绝策略为"CallerRunsPolicy"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 新建10个任务，并将它们添加到线程池中。
        for (int i = 0; i < 10; i++) {
            Runnable myrun = new MyRunnable("task-"+i);
            pool.execute(myrun);
        }
        // 关闭线程池
        pool.shutdown();
        System.exit(0);
    }
}