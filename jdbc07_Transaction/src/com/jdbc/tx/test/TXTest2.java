package com.jdbc.tx.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.ServerInfo;

/*
 * bank 테이블 생성
 * 111 아이유 Toss 300
 * 222 박보검 kb   500
 * */

public class TXTest2 {

	public static void main(String[] args) throws SQLException {
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
			System.out.println("디비 연결 성공");
			
			conn.setAutoCommit(false);
			
			String selectQuery = "SELECT ssn, name, bname, balance FROM bank";
			ps1 = conn.prepareStatement(selectQuery);
			rs = ps1.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("name") + "\t" +
								   rs.getString("bname") + "\t" +
								   rs.getInt("balance") + "\t"
									);
			}
			//아이유 계좌에서 박보검 계좌로 100만원을 이체
			//아이유 계좌에서 100만원 출금
			String minusQuery = "UPDATE bank SET balance = balance - ? WHERE ssn = ?";
			ps2 = conn.prepareStatement(minusQuery);
			ps2.setInt(1, 100);
			ps2.setInt(2, 111);
			ps2.executeUpdate();
			
			//박보검 계좌에서 100만원이 입금
			String plusQuery = "UPDATE bank SET balance = balance + ? WHERE ssn = ?";
			ps3 = conn.prepareStatement(plusQuery);
			ps3.setInt(1, 100);
			ps3.setInt(2, 222);
			ps3.executeUpdate();
			
			conn.commit();
		} catch (SQLException e) {
			System.out.println("===== 계좌 이체시 문제가 발생했습니다 ====");
			conn.rollback();
		} finally {
			System.out.println("===== 계좌 이체후 정보 확인 ====");
			rs = ps1.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("name") + "\t" +
								   rs.getString("bname") + "\t" +
								   rs.getInt("balance") + "\t"
									);
			}
			conn.setAutoCommit(true);
			if(rs!=null) rs.close();
			if(ps1!=null) ps1.close();
			if(ps2!=null) ps2.close();
			if(ps3!=null) ps3.close();
			if(conn!=null) conn.close();
		}

//		String query1 = "INSERT INTO kosa VALUES(4, '이길동')";
//		String query2 = "SELECT name FROM kosa WHERE num = 3";

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
