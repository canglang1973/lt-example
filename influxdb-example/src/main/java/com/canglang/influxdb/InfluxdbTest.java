package com.canglang.influxdb;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

import java.util.List;

/**
 * @author leitao.
 * @category
 * @time: 2018/10/22 0022-14:09
 * @version: 1.0
 * @description:
 **/
public class InfluxdbTest {

    //INSERT cpu,host=serverA,region=us_west idle=0.64,happydevop=false,uptimesecs=123456789i

    public static void main(String[] args){
        write();
        query();
    }

    private static void  query(){
        InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root");
        String dbName = "leitao";
        QueryResult queryResult = influxDB.query(new Query("SELECT * FROM cpu", dbName));
        // thread-safe - can be reused
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        List<Cpu> cpus = resultMapper.toPOJO(queryResult, Cpu.class);
        System.out.println(cpus);
    }
    private static void  write(){
        InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root");
        String dbName = "leitao";
        Point.Builder builder = Point.measurement("cpu");
        Point point = builder.tag("host", "serverC")
                .tag("region", "中国")
                .addField("idle", 20.09)
                .addField("happydevop", true)
                .addField("uptimesecs", 23423)
                .build();
        influxDB.setDatabase(dbName);
        influxDB.write(point);
        System.out.println("======writer success=========");
    }


}
