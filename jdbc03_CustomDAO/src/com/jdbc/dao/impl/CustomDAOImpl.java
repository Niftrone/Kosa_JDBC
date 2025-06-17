package com.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.dao.CustomDAO;
import com.jdbc.vo.Custom;

import config.ServerInfo;

public class CustomDAOImpl implements CustomDAO {

	// 싱글톤 패턴
	private static CustomDAOImpl dao = new CustomDAOImpl();

	private CustomDAOImpl() {
		System.out.println("Singletone...creating");
	}

	public static CustomDAOImpl getInstance() {
		return dao;
	}

	///////////////// 공통 ///////////////////////

	private Connection getConnect() throws SQLException {
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
		return conn;
	}
	

	///////////////// 비지니스 로직 ///////////////////////
	@Override
	public void addCustom(Custom custom) throws SQLException{ // 회원가입
//		 1 디비서버 연결 Connection
//		 2 PreparedStatement 생성 PreparedStatemenet
//		 3 값 바인딩 및 SQL문 실행 
//		 4 자원 반환
		String query = "INSERT INTO custom (id, name, address) VALUES(?, ?, ?)";
		
		try(Connection conn = getConnect();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			ps.setInt(1, custom.getId());
			ps.setString(2, custom.getName());
			ps.setString(3, custom.getAddress());
			System.out.println(ps.executeUpdate() == 1 ? "추가 성공" : "추가 실패");

		}

	}

	@Override
	public void removeCustom(int id) throws SQLException{ // 회원 탈퇴
//		 1 디비서버 연결 Connection
//		 2 PreparedStatement 생성 PreparedStatemenet
//		 3 값 바인딩 및 SQL문 실행 
//		 4 자원 반환
		String query = "DELETE FROM custom WHERE id = ?";
		
		try(Connection conn = getConnect();
			PreparedStatement ps = conn.prepareStatement(query)){
	        ps.setInt(1, id); 
			System.out.println(ps.executeUpdate() == 1 ? "삭제 성공" : "삭제 실패");
			}
	}

	@Override
	public void updateCustom(Custom custom) throws SQLException {
//		 1 디비서버 연결 Connection
//		 2 PreparedStatement 생성 PreparedStatemenet
//		 3 값 바인딩 및 SQL문 실행 
//		 4 자원 반환
		
		String query = "UPDATE custom SET name = ?, address = ? WHERE id = ?";

		try(Connection conn = getConnect();
			PreparedStatement ps = conn.prepareStatement(query)){
			
	        ps.setString(1, custom.getName());
	        ps.setString(2, custom.getAddress());
	        ps.setInt(3, custom.getId()); 
			System.out.println(ps.executeUpdate() == 1 ? "수정 성공" : "수정 실패");
		}
		
	}

	@Override
	public Custom getCustom(int id) throws SQLException {
		Custom cs = null;
		
		String query = "SELECT * FROM custom WHERE id = ?";
		
		try(Connection conn = getConnect();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			ps.setInt(1, id);
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					cs = new Custom(rs.getInt("id"),
							rs.getString("name"),
							rs.getString("address"));
				}
			}
		}
		
		return cs;
	}

	@Override
	public List<Custom> getCustom() throws SQLException {
		List<Custom> customers = new ArrayList<Custom>();
		
		String query = "SELECT * FROM custom";
		
		try(Connection conn = getConnect();
			PreparedStatement ps = conn.prepareStatement(query)){
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					customers.add(new Custom(rs.getInt("id"),
							rs.getString("name"),
							rs.getString("address")));
				}
			}
		}

		return customers;
	}
	
}


