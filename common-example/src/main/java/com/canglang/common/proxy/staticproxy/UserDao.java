package com.canglang.common.proxy.staticproxy;

/**
 * @author leitao.
 * @time: 2018/4/27  9:43
 * @version: 1.0
 * @description:
 **/
public class UserDao implements IUserDao {
    @Override
    public void save() {
        System.out.println("保存数据");
    }
}
