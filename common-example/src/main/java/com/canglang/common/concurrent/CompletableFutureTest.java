package com.canglang.common.concurrent;

import java.util.concurrent.CompletableFuture;

/**
 * @author leitao.
 * @category
 * @time: 2019/8/30 0030-16:38
 * @version: 1.0
 * @description:
 **/
public class CompletableFutureTest {

    public static void main(String[] args){
        completedFutureExample();
    }

    public static void completedFutureExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("message");
        boolean done = cf.isDone();
        //getNow(null)方法在future完成的情况下会返回结果，就比如上面这个例子，否则返回null (传入的参数)
        Object now = cf.getNow(null);
        System.out.println(done + "  " + now);
    }


}
