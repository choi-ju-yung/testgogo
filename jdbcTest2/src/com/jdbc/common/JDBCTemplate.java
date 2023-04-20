package com.jdbc.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	//Connection 객체를 생성해주는 기능을 제공
	public static Connection getConnection() {
		Connection conn = null;
		// 파일과 연결하기 위해서는 절대경로가 필요함
		String path = JDBCTemplate.class.getResource("/driver.properties").getPath();
		// "/" -> 루트부터 bin까지의 경로가 다 나옴 (bin = 클래스파일이 저장되는 곳) 
		// "/driver.properties" -> driver.properties 경로 
	
		try {
			Properties driver = new Properties(); // Properties 객체 생성
			driver.load(new FileReader(path));
			
			
			Class.forName(driver.getProperty("drivername")); // 키값으로 밸류값 알수 있음
			conn = DriverManager.getConnection(driver.getProperty("url")
												,driver.getProperty("user")
												,driver.getProperty("pw"));
			conn.setAutoCommit(false);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	//connection, Statement, Result 객체를 닫아주는 기능 제공
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			if(stmt!=null && !stmt.isClosed()) {
				stmt.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs) {
		try {
			if(rs!=null && !rs.isClosed()) {
				rs.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//트렌젝션을 처리하는 기능을 제공
	public static void commit(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) {
				conn.commit();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rollback(Connection conn) {
		try {
			if(conn!= null && !conn.isClosed()) {
				conn.rollback();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}

