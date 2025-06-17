package com.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.dao.EmployeeDAO;
import com.jdbc.vo.Employee;

import config.ServerInfo;

public class EmployeeDAOImpl implements EmployeeDAO {

	private static EmployeeDAOImpl emp = new EmployeeDAOImpl();

	private EmployeeDAOImpl() {
		System.out.println("*******");
	}

	public static EmployeeDAOImpl getInstance() {
		return emp;
	}

	/// 공통 로직///
	@Override
	public Connection getConnect() throws SQLException {
		Connection cnn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
		return cnn;
	}
	
	private Employee makeEmployee(ResultSet re) throws SQLException{
		return new Employee(re.getInt("num"), 
				   			re.getString("name"), 
				   			re.getDouble("salary"), 
				   			re.getString("address"));
	}

	//// 비지니스 로직 ////
	@Override
	public void insertEmployee(Employee emp) throws SQLException {
		String query = "INSERT INTO employee(num, name, salary, address) VALUES(?, ?, ?, ?)";

		try (Connection conn = getConnect();
			PreparedStatement ps = conn.prepareStatement(query);) {
			
			ps.setInt(1, emp.getNum());
			ps.setString(2, emp.getName());
			ps.setDouble(3, emp.getSalary());
			ps.setString(4, emp.getAddress());

			System.out.println(ps.executeUpdate() == 1 ? "추가 성공" : "추가 실패");
		}
	}

	@Override
	public void removeEmployee(int num) throws SQLException {

		String query = "DELETE FROM employee WHERE num = ?";

		try (Connection conn = getConnect(); 
			PreparedStatement ps = conn.prepareStatement(query);) {
			
			ps.setInt(1, num);

			System.out.println(ps.executeUpdate() == 1 ? "삭제 성공" : "삭제 실패");
		}
	}

	@Override
	public void updateEmployee(Employee emp) throws SQLException {

		String query = "UPDATE employee SET name = ?, salary = ?, address = ? WHERE num = ?";

		try (Connection conn = getConnect(); 
			PreparedStatement ps = conn.prepareStatement(query);) {
			
			ps.setString(1, emp.getName());
			ps.setDouble(2, emp.getSalary());
			ps.setString(3, emp.getAddress());
			ps.setInt(4, emp.getNum());

			System.out.println(ps.executeUpdate() == 1 ? "수정 성공" : "수정 실패");
		}
	}

	@Override
	public Employee selectEmployee(int num) throws SQLException {
		
		Employee emp = null;
		String query = "SELECT * FROM employee WHERE num = ?";
		
		try(Connection conn = getConnect();
			PreparedStatement ps = conn.prepareStatement(query);){
			
			ps.setInt(1, num);
			
			ResultSet re = ps.executeQuery();
			if(re.next()) 
				emp = makeEmployee(re);
			
		}
		
		return emp;
	}

	@Override
	public List<Employee> selectEmployee() throws SQLException {
		
		List<Employee> emps = new ArrayList<>();
		String query = "SELECT * FROM employee";
		
		try(Connection conn = getConnect();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet re = ps.executeQuery();){
			
			while(re.next()) 
				emps.add(makeEmployee(re));
		}
		return emps;
	}

}
