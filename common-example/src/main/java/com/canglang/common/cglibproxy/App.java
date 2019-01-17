package com.canglang.common.cglibproxy;

import com.canglang.common.staticproxy.UserDao;

/**
 * @author leitao.
 * @time: 2018/4/27  10:15
 * @version: 1.0
 * @description:
 **/
public class App {
    public static  void main(String[] args){
        //目标对象
        UserDao target = new UserDao();

        //代理对象
        UserDao proxy = (UserDao)new ProxyFactory(target).getProxyInstance();

        //执行代理对象的方法
        proxy.save();
    }
}
