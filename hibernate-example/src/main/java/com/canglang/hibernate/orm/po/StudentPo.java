package com.canglang.hibernate.orm.po;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author leitao.
 * @category
 * @time: 2020-3-3-10:53
 * @version: 1.0
 * @description: 使用配置文件的方式 com/canglang/hibernate/mapper/StudentPo.hbm.xml
 **/
@Audited
@AllArgsConstructor
@NoArgsConstructor
public class StudentPo implements Serializable {

    private Integer id;

    private String name;

    private String gender;

    private Integer age;

    private Date createTime;

    private Set<GradePo> grades;

    private Set<TeacherPo> teachers;

    public StudentPo(String name, String gender, Integer age, Date createTime, Set<GradePo> grades) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.createTime = createTime;
        this.grades = grades;
    }

    public StudentPo(String name, String gender, Integer age, Date createTime) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Set<GradePo> getGrades() {
        return grades;
    }

    public void setGrades(Set<GradePo> grades) {
        this.grades = grades;
    }

    public Set<TeacherPo> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<TeacherPo> teachers) {
        this.teachers = teachers;
    }
}
