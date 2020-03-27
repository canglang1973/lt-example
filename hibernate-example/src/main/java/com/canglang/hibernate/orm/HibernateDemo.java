package com.canglang.hibernate.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.util.Date;
import java.util.List;

/**
 * @author leitao.
 * @category
 * @time: 2020-3-3-11:05
 * @version: 1.0
 * @description: 本demo目的是为了更好的阅读Hibernate源码, 更好的理解调用流程
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
        saveStudent(session);
//        selectStudentByXml(session);
//        selectStudentByOid(session);
//        selectStudentByHql(session);
//        selectStudentBySql(session);

//        saveGrade(session);

//        /*提交事务*/
        transaction.commit();
        session.close();
        Session session1 = sessionFactory.openSession();
        StudentPo load = session1.load(StudentPo.class, 1);
        Set<GradePo> grades = load.getGrades();
        session1.close();
        /*关闭资源*/
        sessionFactory.close();
    }

    private static void saveStudent(Session session) {
        /*向数据表中插入一条数据*/
        Set<GradePo> gradePos = new HashSet<GradePo>();
        StudentPo student = new StudentPo("张三","男",20,new Date());
        gradePos.add(new GradePo(100.0, "语文", new Date()));
        student.setGrades(gradePos);
        Set<TeacherPo> teacherPos = new HashSet<TeacherPo>();
        TeacherPo teacherPo1 = new TeacherPo("王老师");
        TeacherPo teacherPo2 = new TeacherPo("张老师");
        teacherPos.add(teacherPo1);
        teacherPos.add(teacherPo2);
        Set<StudentPo> studentPos = new HashSet<StudentPo>();
        studentPos.add(student);
        teacherPo1.setStudents(studentPos);
        teacherPo2.setStudents(studentPos);
        student.setTeachers(teacherPos);
        /*添加、保存方法*/
        session.save(student);
    }

    private static void selectStudentByXml(Session session){
        List list1 = session.getNamedQuery("student.sqlget").list();
        List list = session.getNamedQuery("student.hqlget").list();
        System.out.println();
    }

    private static void selectStudentByOid(Session session){
        StudentPo studentPo = session.get(StudentPo.class, 1);
        StudentPo load = session.load(StudentPo.class, 1);
        System.out.println();
    }

    private static void selectStudentByHql(Session session) {
        List list = session.createQuery("from StudentPo ").list();
        System.out.println(list);
    }

    private static void selectStudentBySql(Session session) {
        NativeQuery sqlQuery = session.createSQLQuery("select * from student where id = :id");
        sqlQuery.setParameter("id", 1);
        List list = sqlQuery.list();
        System.out.println(list);
    }

    private static void saveGrade(Session session) {
        GradePo grade = new GradePo(100.0, "语文", new Date());
        session.save(grade);
    }
}
