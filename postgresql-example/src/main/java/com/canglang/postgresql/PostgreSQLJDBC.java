package com.canglang.postgresql;

import java.sql.*;

/**
 * @author leitao.
 * @category
 * @time: 2018/11/10 0010-15:28
 * @version: 1.0
 * @description:
 **/
public class PostgreSQLJDBC {

    public static void main(String args[]) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/local_db",
                            "postgres", "123456");
            System.out.println("Opened database successfully");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "select * from \"user\";";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                System.out.println(id + "_" + name + "_" + age);
            }
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

}
