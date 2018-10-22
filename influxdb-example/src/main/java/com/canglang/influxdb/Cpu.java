package com.canglang.influxdb;

import lombok.Data;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

/**
 * @author leitao.
 * @category
 * @time: 2018/10/22 0022-15:32
 * @version: 1.0
 * @description:
 **/
@Data
@Measurement(name = "cpu")
public class Cpu {

    @Column(name = "time")
    private Instant time;

    @Column(name = "host",tag = true)
    private String hostname;

    @Column(name = "region",tag = true)
    private String region;

    @Column(name = "idle")
    private Double idle;

    @Column(name = "happydevop")
    private Boolean happydevop;

    @Column(name = "uptimesecs")
    private Long uptimeSecs;


}
