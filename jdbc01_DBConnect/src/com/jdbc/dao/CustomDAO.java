package com.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.Result;

import config.ServerInfo;

public class CustomDAO {
	public CustomDAO() throws SQLException {
		Connection conn = DriverManager.getConnection(ServerInfo.URL,ServerInfo.USER,ServerInfo.PASS );
		System.out.println("서버 연결...");
		
//		String query = "INSERT INTO custom(id,name,address) VALUES(?,?,?)";
//		PreparedStatement ps = conn.prepareStatement(query);
//		ps.setInt(1, 4);
//		ps.setString(2, "아이유");
//		ps.setString(3, "제주 애월읍");
//		System.out.println(ps.executeUpdate() + "등록 성공");
		
		//DELETE 
		// precompile workbench에 오류가 나지 않아도 자바쪽에서 오류가 나는 경우가 있음
//		String query = "DELETE FROM custom WHERE id = ?";
//		PreparedStatement ps = conn.prepareStatement(query);
//		
//		ps.setInt(1, 4);
//		
//		System.out.println(ps.executeUpdate() == 1 ? "성공" : "실패");
		
		//update
//		String query = "UPDATE custom SET name = ? , address = ? WHERE id = ?";
//		PreparedStatement ps =conn.prepareStatement(query);
//		
//		ps.setString(1, "김혜경");
//		ps.setString(2, "서울 종로");
//		ps.setInt(3, 5);
//		System.out.println(ps.executeUpdate() == 1 ? "성공" : "실패");
		
		//a custom
//		String query = "SELECT id, name, address FROM custom WHERE id = ?";
//		PreparedStatement ps = conn.prepareStatement(query);
//		ps.setInt(1, 1);
//		ResultSet rs = ps.executeQuery();
//		System.out.println("---------------고객정보-----------------");
//		if(rs.next()) {
//			System.out.println(rs.getInt(1) + 
//								"\t" + rs.getString(2) +
//								"\t" + rs.getString(3));
//		}
//		System.out.println("------------------------------------");
		
		// all custom
		String query = "SELECT id, name, address FROM custom";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		System.out.println("---------------고객정보-----------------");
		while(rs.next()) {
			System.out.println(rs.getInt(1) + 
								"\t" + rs.getString(2) +
								"\t" + rs.getString(3));
		}
		System.out.println("------------------------------------");

	}
}
