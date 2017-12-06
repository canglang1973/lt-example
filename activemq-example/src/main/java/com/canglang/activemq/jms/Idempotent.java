package com.canglang.activemq.jms;

import java.lang.annotation.*;

/**
 * @author leitao.
 * @time: 2017/11/21  16:13
 * @version: 1.0
 * @description:
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Idempotent {
    int identifierPosition() default 0;
}
