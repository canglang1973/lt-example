package com.canglang.common.executor_policy;

/**
 * @author leitao.
 * @category
 * @time: 2019/1/15 0015-14:46
 * @version: 1.0
 * @description:
 **/
public class MyRunnable implements Runnable{

    private String name;

    public MyRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " is running.");
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
