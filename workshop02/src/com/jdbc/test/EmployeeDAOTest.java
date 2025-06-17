package com.jdbc.test;

import java.sql.SQLException;

import com.jdbc.dao.impl.EmployeeDAOImpl;
import com.jdbc.vo.Employee;

import config.ServerInfo;

public class EmployeeDAOTest {

	public static void main(String[] args) {
		EmployeeDAOImpl dao = EmployeeDAOImpl.getInstance();
		
		try {
			dao.insertEmployee(new Employee(1, "홍길동", 3000.0, "서울 강남구"));
//			dao.insertEmployee(new Employee(2, "김영희", 2800.5, "부산 해운대구"));
//			dao.insertEmployee(new Employee(3, "이철수", 3200.75, "대구 수성구"));
//			dao.insertEmployee(new Employee(4, "박민수", 3100.0, "인천 남동구"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			dao.removeEmployee(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			dao.updateEmployee(new Employee(4, "민경훈", 4500.12, "경기 안양"));;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println(dao.selectEmployee(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			dao.selectEmployee().stream()
				 				.forEach(System.out :: println);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}
	
	static {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("파일 연결 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
