package com.canglang.mybatis.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author leitao.
 * @category
 * @time: 2020-3-3-10:53
 * @version: 1.0
 * @description:
 **/
@Data
public class StudentPo implements Serializable {

    private Integer id;

    private String name;

    private String gender;

    private Integer age;

    private Date createTime;

}
