package com.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import config.ServerInfo;

public class ProductDAO {
	

    public void addData(int no, String pname, String maker, double price) throws SQLException {
        String sql = "INSERT INTO product(no, pname, maker, price) VALUES(?,?,?,?)";
        
        try (Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, no);
            ps.setString(2, pname);
            ps.setString(3, maker);
            ps.setDouble(4, price);

            System.out.println(ps.executeUpdate() == 1 ? "성공" : "실패");
        }
    }

    public void deleteData(int no) throws SQLException {
        String sql = "DELETE FROM product WHERE no = ?";

        try (Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, no);

            System.out.println(ps.executeUpdate() == 1 ? "성공" : "실패");
        }
    }
}
