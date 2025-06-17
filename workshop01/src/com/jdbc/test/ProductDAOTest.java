package com.jdbc.test;

import java.sql.SQLException;

import com.jdbc.dao.ProductDAO;

import config.ServerInfo;

public class ProductDAOTest {

	public static void main(String[] args) {
		ProductDAO p = new ProductDAO();
		try {
			p.addData(1, "노트북", "Apple", 150.1);
			p.addData(2, "컴퓨터", "Samsung", 300.62);
			p.addData(3, "로봇", "Tesla", 200.05);
			p.deleteData(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("드라이버 로딩 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
