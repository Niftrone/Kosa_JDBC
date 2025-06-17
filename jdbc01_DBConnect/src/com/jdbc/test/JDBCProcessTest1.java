package com.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * DAT(database access object)
 * JAVA 프로그램을 통해서 DBMS에 접속
 * 메소드를 통해서 SQL이 실행되도록 한다
 * 
 * 파일끼리의 연결은 경로
 * 클래스 끼리 연결하는건 패키지
 * 
 * 1. MySQL Driver를 해당 클래스 메모리에 로팅
 * 2. DB 서버 접속
 * 	  접속 성공하면 Connection객체를 반환받고
 * 	  이후 작업은 Connection을 통해서 모든 작업이 이뤄진다
 * 
 * 	  jdbc:mysql://127.0.0.1:3306/kosa?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8
 * 	
 * 3. 쿼리문 실행하기 위해서는 아래 함수를 사용
 * 	  executeUdate() --- DML(INSERT/DELETE/UPDATE)
 * 	  executeQuery() --- SELECT
 * 
 * 	  함수를 PreparedStatement가 제공
 * 	  PreparedStatement를 일단 먼저 생성
 * 
 * 
 * */
public class JDBCProcessTest1 {

	public static void main(String[] args) {
		// 1 드라이버 로딩
		try {

			Class.forName("com.mysql.cj.jdbc.Driver"); // FQCN(fullnameclass)
			System.out.println("Driver Loading....Success");

			// 2 DB서버 연결
			String url = "jdbc:mysql://127.0.0.1:3306/kosa?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
			try {
				Connection conn = DriverManager.getConnection(url, "root", "1234");
				System.out.println("DB Connect.......");
				
				//3 PreparedStatement 생성
				PreparedStatement ps = conn.prepareStatement("DELETE * FROM custom WHERE id = 3");
				System.out.println("PreparedStatement...Connect");
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("DB Fail.......");
			
			}

		} catch (ClassNotFoundException e) {
			System.out.println("Driver Loading....Fail");
		}
	}

}
