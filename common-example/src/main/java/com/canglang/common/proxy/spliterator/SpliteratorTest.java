package com.canglang.common.proxy.spliterator;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author leitao.
 * @category
 * @time: 2018/10/25 0025-9:19
 * @version: 1.0
 * @description:
 **/
public class SpliteratorTest {

    public static void main(String[] args){
        List<Integer> arr = new ArrayList<>();
        for (int i=0;i<11;i++){
            arr.add(i);
        }
        Spliterator<Integer> a = arr.spliterator();
        Spliterator<Integer> b = a.trySplit();
        Spliterator<Integer> c = b.trySplit();
        Stream<Integer> stream = StreamSupport.stream(a, true);
        Stream<Integer> stream1 = StreamSupport.stream(b, true);
        Stream<Integer> stream2 = StreamSupport.stream(c, true);
        List<Integer> collect = stream.collect(Collectors.toList());
        List<Integer> collect1 = stream1.collect(Collectors.toList());
        List<Integer> collect2 = stream2.collect(Collectors.toList());
        Integer min = arr.stream().min(Integer::compareTo).get();
        List<Integer> collect3 = arr.stream().filter(integer -> integer > 5 ).collect(Collectors.toList());
        System.out.println();
//        filter();
        parallelStream();
    }

    private static void filter(){
        List<User> arr = new ArrayList<>();
        arr.add(new User(12,"小米"));
        arr.add(new User(15,"小名"));
        arr.add(new User(10,"小红"));
        arr.forEach(System.out::println);
        arr.forEach(user -> System.out.println(user.getAge()));
        List<User> users = arr.stream().filter(user -> user.getName().contains("名")).collect(Collectors.toList());
        List<User> users1 = arr.parallelStream().filter(user -> user.getName().contains("名")).collect(Collectors.toList());
        System.out.println();
    }

    static class User{
        private Integer age;
        private String name;

        public User(Integer age,String name){
            this.age=age;
            this.name=name;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }


    /**
     * parallelStream使用注意线程安全问题
     */
    private static void parallelStream(){
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();
        Lock lock = new ReentrantLock();
        IntStream.range(0, 10000).forEach(list1::add);

        IntStream.range(0, 10000).parallel().forEach(list2::add);

        IntStream.range(0, 10000).forEach(i -> {
            lock.lock();
            try {
                list3.add(i);
            }finally {
                lock.unlock();
            }
        });

        System.out.println("串行执行的大小：" + list1.size());
        System.out.println("并行执行的大小：" + list2.size());
        System.out.println("加锁并行执行的大小：" + list3.size());
    }

}
