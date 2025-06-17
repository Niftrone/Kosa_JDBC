package com.jdbc.dao;
/*
 * JDBC 4단계
 * 1 드라이브 로딩
 * 2 디비서버 연결 Connection
 * 3 PreparedStatement 생성 PreparedStatemenet
 * 4 값 바인딩 및 SQL문 실행 ResultSet
 * 		 + 
 * 	   자원 반납
 * 	finally.close()
 * 	rs.close()
 *  ps.close()
 *  conn.close()
 * */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class CustomDAO {
	public CustomDAO() {

	}

	public CustomDAO(String driver, String url, String user, String pass, Properties p) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			// 1. 드라이버 로딩
			Class.forName(driver);
			System.out.println("로딩..성공");

			// 2 DB서버 연결(getConnection()..Connection 반환)
			conn = DriverManager.getConnection(url, user, pass);
			System.out.println("DB 연결 성공");

			// 3 PreaparedStatement 

			String query = "jdbc.sql.selectAll";
			ps = conn.prepareStatement(query);

			// 4 쿼리문 실행
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getString("address"));
			}
		} finally {
			rs.close();
			ps.close();
			conn.close(); // Connection을 닫는게 가장 중요하다
		}

	}
}
