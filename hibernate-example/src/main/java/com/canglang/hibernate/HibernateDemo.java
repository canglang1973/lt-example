package com.canglang.hibernate;

import com.canglang.hibernate.po.GradePo;
import com.canglang.hibernate.po.StudentPo;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author leitao.
 * @category
 * @time: 2020-3-3-11:05
 * @version: 1.0
 * @description: 本demo目的是为了更好的阅读Hibernate源码,更好的理解调用流程
 **/
public class HibernateDemo {

    public static void main(String[] args) {
        /*读取配置文件 .configure()自动读取cfg.xml文件*/
        Configuration configuration = new Configuration().configure();
        /*获得sessionFactory对象，自动生成表，读取数据库信息，检查数据表是否更新
         * 这个对象我们用它只用来获取session
         * 实际上这行代码   它检查了数据库和实体类
         * 如果有变化它会更新
         * 非常耗费资源    解决方案：（封装工具类）
         * */
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        /*使用sessionFactory获得session对象*/
        Session session = sessionFactory.openSession();
        /*开启事务
         * hibernate 必须开启事务
         * 不开启事务不会将数据保存到数据库
         * */
        Transaction transaction = session.beginTransaction();
        /*向数据表中插入一条数据*/
        StudentPo student = new StudentPo();
        student.setName("张三");
        student.setAge(20);
        student.setGender("男");
        student.setCreateTime(new Date());
        /*添加、保存方法*/
        session.save(student);
        NativeQuery sqlQuery = session.createSQLQuery("select * from student");
        List list = sqlQuery.list();
//        /*提交事务*/
        GradePo grade = new GradePo(1,100.0,"语文",new Date());
        session.save(grade);
        transaction.commit();
        /*关闭资源*/
        session.close();
        sessionFactory.close();
    }
}
