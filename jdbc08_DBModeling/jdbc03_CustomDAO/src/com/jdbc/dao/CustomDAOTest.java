package com.jdbc.dao;

import java.sql.SQLException;

import com.jdbc.dao.impl.CustomDAOImpl;
import com.jdbc.vo.Custom;

import config.ServerInfo;

public class CustomDAOTest {

	public static void main(String[] args) {
		CustomDAOImpl dao = CustomDAOImpl.getInstance();
		
		try {
			dao.addCustom(new Custom(6, "김선호", "대전"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			dao.removeCustom(6);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			dao.updateCustom(new Custom(1, "양관수", "범계역"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(dao.getCustom(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			dao.getCustom().stream()
						   .forEach(System.out :: println);;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	static {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("드라이브 로딩 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
