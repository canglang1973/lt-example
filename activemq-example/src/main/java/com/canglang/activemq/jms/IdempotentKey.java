package com.canglang.activemq.jms;

import java.lang.reflect.Method;

/**
 * @author leitao.
 * @time: 2017/11/21  16:14
 * @version: 1.0
 * @description:
 **/
class IdempotentKey {
    private Method method;
    private Object key;

    public IdempotentKey() {
    }

    public IdempotentKey(Method method, Object key) {
        this.method = method;
        this.key = key;
    }

    public Method getMethod() {
        return this.method;
    }

    public Object getKey() {
        return this.key;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (this.method == null?0:this.method.hashCode());
        result = 31 * result + (this.key == null?0:this.key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj == null) {
            return false;
        } else if(this.getClass() != obj.getClass()) {
            return false;
        } else {
            IdempotentKey other = (IdempotentKey)obj;
            if(this.method == null) {
                if(other.method != null) {
                    return false;
                }
            } else if(!this.method.equals(other.method)) {
                return false;
            }

            if(this.key == null) {
                if(other.key != null) {
                    return false;
                }
            } else if(!this.key.equals(other.key)) {
                return false;
            }

            return true;
        }
    }
}
