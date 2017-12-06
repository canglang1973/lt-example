package com.canglang.activemq.jms;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author leitao.
 * @time: 2017/11/21  16:13
 * @version: 1.0
 * @description:
 **/
public class DelayItem<T> implements Delayed {
    private static final long NANO_ORIGIN = System.nanoTime();
    private static final AtomicLong sequencer = new AtomicLong(0L);
    private final long sequenceNumber;
    private final long time;
    private final T item;

    static final long now() {
        return System.nanoTime() - NANO_ORIGIN;
    }

    public DelayItem(T submit, long timeout) {
        this.time = now() + timeout;
        this.item = submit;
        this.sequenceNumber = sequencer.getAndIncrement();
    }

    public T getItem() {
        return this.item;
    }

    public long getDelay(TimeUnit unit) {
        long d = unit.convert(this.time - now(), TimeUnit.NANOSECONDS);
        return d;
    }

    public int compareTo(Delayed other) {
        if(other == this) {
            return 0;
        } else if(other instanceof DelayItem) {
            DelayItem x = (DelayItem)other;
            long diff = this.time - x.time;
            return diff < 0L?-1:(diff > 0L?1:(this.sequenceNumber < x.sequenceNumber?-1:1));
        } else {
            long d = this.getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS);
            return d == 0L?0:(d < 0L?-1:1);
        }
    }
}

