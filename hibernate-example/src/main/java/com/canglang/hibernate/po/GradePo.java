package com.canglang.hibernate.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author leitao.
 * @category
 * @time: 2020-3-3-13:50
 * @version: 1.0
 * @description: 使用注解的方式
 **/
@org.hibernate.annotations.NamedQuery(name = "grade.query",query = "from GradePo where id = :id")
@Data
@Table(name = "grade")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GradePo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 5)
    private Double score;

    @Column(length = 32)
    private String subject;

    @Column(name = "create_time")
    private Date createTime;

}
