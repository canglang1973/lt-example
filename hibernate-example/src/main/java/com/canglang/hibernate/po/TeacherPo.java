package com.canglang.hibernate.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author leitao.
 * @category
 * @time: 2020/3/25 0025-14:00
 * @version: 1.0
 * @description:
 **/
@Entity
@Table(name = "teacher")
@AllArgsConstructor
@NoArgsConstructor
public class TeacherPo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @ManyToMany(targetEntity = StudentPo.class, mappedBy = "teachers",cascade = CascadeType.ALL)
    private Set<StudentPo> students;

    public TeacherPo(String name) {
        this.name = name;
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

    public Set<StudentPo> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentPo> students) {
        this.students = students;
    }
}