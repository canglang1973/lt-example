package com.canglang.mybatis;

import com.canglang.mybatis.mapper.GradeMapper;
import com.canglang.mybatis.mapper.StudentMapper;
import com.canglang.mybatis.po.GradePo;
import com.canglang.mybatis.po.StudentPo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @author leitao.
 * @category
 * @time: 2020-3-10-17:24
 * @version: 1.0
 * @description: Mybatis源码导读demo
 **/
public class MybatisDemo {

    public static void main(String[] args) throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //根据xml文件编写sql
        StudentPo student = (StudentPo)sqlSession.selectOne("com.canglang.mybatis.mapper.StudentMapper.selectStudentById", 1);
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        StudentPo studentPo = studentMapper.selectStudentById(1);
        //根据mapper class 编写sql
        GradeMapper gradeMapper = sqlSession.getMapper(GradeMapper.class);
        GradePo gradePo = gradeMapper.selectGradeById(1);
        sqlSession.close();
    }

}
