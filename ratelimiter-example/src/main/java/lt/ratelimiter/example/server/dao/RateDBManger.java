package lt.ratelimiter.example.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RateDBManger {
	 private static final String DRIVER="com.mysql.jdbc.Driver";
	 private static final String URL="jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&amp;characterEncoding=utf-8&amp;zeroDateTimeBehavior=convertToNull&amp;useServerPrepStmts=false";
	 private static final String USERNAME="root";
	 private static final String PASSWORD="123456";
	 private static List<Connection> connections =new ArrayList<Connection>();
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void close(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
