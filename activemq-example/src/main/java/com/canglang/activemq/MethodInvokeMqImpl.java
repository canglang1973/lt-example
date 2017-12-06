package com.canglang.activemq;

import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

/**
 * @author leitao.
 * @time: 2017/11/6  14:36
 * @version: 1.0
 * @description:
 **/
@Service("methodInvokeMq")
public class MethodInvokeMqImpl implements MethodInvokeMq {
    public void printMesg(String mesg) {
        System.out.println(mesg);
    }
}
