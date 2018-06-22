package com.canglang.common.proxy.staticproxy;

/**
 * @author leitao.
 * @time: 2018/4/27  9:51
 * @version: 1.0
 * @description:
 **/
public class App {

    public static  void main(String[] arg){
        UserDaoProxy  proxy = new UserDaoProxy(new UserDao());
        proxy.save();
    }

}
