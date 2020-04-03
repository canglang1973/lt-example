package com.canglang.hibernate.validator.vo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * @author leitao.
 * @category
 * @time: 2020/3/27 0027-16:44
 * @version: 1.0
 * @description:
 **/
@Data
public class Car {

    @NotNull
    private String brand;

    @Max(value = 10)
    private Integer carHigh;

    @Min(value = 20)
    private int carLong;

    public Car(@NotNull String brand, @Max(value = 10) Integer carHigh, @Min(value = 20) int carLong) {
        this.brand = brand;
        this.carHigh = carHigh;
        this.carLong = carLong;
    }

    public Car(){}

}