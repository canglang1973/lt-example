package com.canglang.hibernate.orm.po;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

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
@Audited
@org.hibernate.annotations.NamedQuery(name = "grade.query",query = "from GradePo where id = :id")
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

    @ManyToOne
    @JoinColumn(name = "student")
    private StudentPo student;

    public GradePo(Double score, String subject, Date createTime) {
        this.score = score;
        this.subject = subject;
        this.createTime = createTime;
    }
    public GradePo(Double score, String subject, Date createTime,StudentPo student) {
        this.score = score;
        this.subject = subject;
        this.createTime = createTime;
        this.student = student;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public StudentPo getStudent() {
        return student;
    }

    public void setStudent(StudentPo student) {
        this.student = student;
    }
}
