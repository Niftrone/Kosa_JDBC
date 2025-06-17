package com.jdbc.tx.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.ServerInfo;

/*
 * 여러가지 쿼리문을 더이상 쪼갤수 없는 하나의 처리단위로 묶는 기술을 Transaction
 * "Atomic" 원자성
 * 
 * Connection conn.setAutoCommit(false);
 * 				try{
 * 					//
 * 					//
 * 					conn.commit
 * 				}catch() {
 * 					//
 * 					conn.rollback();
 * 				}
 * 			  conn.setAutoCommit(true);
 * */

public class TXTest1 {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
			System.out.println("디비 연결 성공");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		

		String query1 = "INSERT INTO kosa VALUES(4, '이길동')";
		String query2 = "SELECT name FROM kosa WHERE num = 3";

//		// 트랜잭션 작업
//		conn.setAutoCommit(false);
//		PreparedStatement ps1 = conn.prepareStatement(query1);
//		ps1.executeUpdate();
//
//		PreparedStatement ps2 = conn.prepareStatement(query2);
//		ResultSet rs = ps2.executeQuery();
//
//		if (rs.next()) {
//			System.out.println("고객조회 성공");
//			conn.commit();
//		} else {
//			System.out.println("고객조회 실패");
//			conn.rollback();
//		}
//
//		// 레코드가 존재한다면 query2 작업이 성공 ..commit()
//		// 레코드가 존재하지 않는다면 query2 작업이 실패 .. rollback()
//
//		conn.setAutoCommit(true);
	}
	
	static {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("드라이버 로딩");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}

}
