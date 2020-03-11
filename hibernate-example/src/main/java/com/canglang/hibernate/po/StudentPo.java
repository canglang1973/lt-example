package com.canglang.hibernate.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author leitao.
 * @category
 * @time: 2020-3-3-10:53
 * @version: 1.0
 * @description: 使用配置文件的方式 com/canglang/hibernate/mapper/StudentPo.hbm.xml
 **/
@Data
public class StudentPo implements Serializable {

    private Integer id;

    private String name;

    private String gender;

    private Integer age;

    private Date createTime;

}
