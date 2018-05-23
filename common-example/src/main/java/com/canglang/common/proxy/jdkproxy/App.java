package com.canglang.common.proxy.jdkproxy;

import com.canglang.common.proxy.staticproxy.IUserDao;
import com.canglang.common.proxy.staticproxy.UserDao;

/**
 * @author leitao.
 * @time: 2018/4/27  9:57
 * @version: 1.0
 * @description:
 **/
public class App {
    public static void main(String[] args){
        ProxyFactory proxy = new ProxyFactory(new UserDao());
        IUserDao proxyInstance = (IUserDao)proxy.getProxyInstance();
        proxyInstance.save();
    }
}
