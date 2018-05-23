package com.canglang.common.proxy.staticproxy;

/**
 * @author leitao.
 * @time: 2018/4/27  9:44
 * @version: 1.0
 * @description:
 **/
public class UserDaoProxy {

    private IUserDao target;

    public UserDaoProxy(IUserDao target){
        this.target=target;
    }

    public void save(){
        System.out.println("开始事务");
        target.save();//执行目标方法
        System.out.println("结束事务");
    }

}
