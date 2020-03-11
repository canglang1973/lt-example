package com.canglang.mybatis.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author leitao.
 * @category
 * @time: 2020-3-3-13:50
 * @version: 1.0
 * @description: 使用注解的方式
 **/
@Data
public class GradePo implements Serializable {

    private Integer id;

    private Double score;

    private String subject;

    private Date createTime;

}
