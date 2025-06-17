package com.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import com.jdbc.dao.BookDAO;
import com.jdbc.exception.BookNotFoundException;
import com.jdbc.exception.DMLException;
import com.jdbc.exception.DuplicateISBNException;
import com.jdbc.exception.InvalidInputException;
import com.jdbc.vo.Book;

import config.ServerInfo;

public class BookDAOImpl implements BookDAO{
	
	private static BookDAOImpl dao = new BookDAOImpl();
	
	private BookDAOImpl() {
		System.out.println("singletone");
	}
	
	public static BookDAOImpl getInstance() {
		return dao;
	}
	
	//------------ 공통 로직 -----------------
	
	private Connection getConnect() throws SQLException{
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
		System.out.println("DATA_BASE_CONN");
		return conn;
	}
	
	private Book createBook(ResultSet rs) throws SQLException {
	    return new Book(rs.getString("isbn"),
	                    rs.getString("title"),
	                    rs.getString("writer"),
	                    rs.getString("publisher"),
	                    rs.getInt("price"));
	}
	
	
	//-------------- 비지니스 로직 ----------------

	@Override
	public void registerBook(Book vo) throws DMLException, DuplicateISBNException {
		/*
		 * 등록할 사람이 있다면 DuplicateISBNException 터지도록 우회한다
		 * try resource with구문 사용
		 * */
		String query = "INSERT INTO book(isbn, title, writer, publisher, price) VALUES(?, ?, ?, ?, ?)";
		
		try(Connection conn = getConnect();
			PreparedStatement ps = conn.prepareStatement(query);)
		{
			ps.setString(1, vo.getIsbn());
			ps.setString(2, vo.getTitle());
			ps.setString(3, vo.getWriter());
			ps.setString(4, vo.getPublisher());
			ps.setInt(5, vo.getPrice());
			
			System.out.println(ps.executeUpdate() == 1 ? "추가 성공" : "추가 실패");
			
		}catch(SQLIntegrityConstraintViolationException e) {
			throw new DuplicateISBNException("이미 존재하는 책");
		}catch(SQLException e) {
			throw new DMLException("책 추가중 오류");
		}
		
	}

	@Override
	public void deleteBook(String isbn) throws DMLException, BookNotFoundException {
		/**/
		
		String query = "DELETE FROM book WHERE isbn = ?";
		
		try(Connection conn = getConnect();
			PreparedStatement ps = conn.prepareStatement(query);)
		{
			ps.setString(1, isbn);
			
			int result = ps.executeUpdate();
			
			if(result != 1) 
				throw new BookNotFoundException("삭제할 데이터가 없습니다.");
			else
				System.out.println("삭제 성공");
			
		}catch(SQLException e) {
			throw new DMLException("책 삭제중 오류");
		}
		
	}

	@Override
	public Book findByBook(String isbn, String title) throws DMLException {
		
		Book book = null;
		
		String query = "SELECT * FROM book WHERE isbn = ? and title = ?";
		
		try(Connection conn = getConnect();
			PreparedStatement ps = conn.prepareStatement(query);)
		{
			ps.setString(1, isbn);
			ps.setString(2, title);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				book = new Book(rs.getString("isbn"), 
						   rs.getString("title"), 
						   rs.getString("writer"), 
						   rs.getString("publisher"), 
						   rs.getInt("price"));
			}
		}catch(SQLException e) {
			throw new DMLException("책 찾는중 오류");
		}
		
		return book;
	}
	

	@Override
	public ArrayList<Book> findByWriter(String writer) throws DMLException {
		ArrayList<Book> books = new ArrayList<>();
		
		String query = "SELECT * FROM book WHERE writer = ?";
		
		try(Connection conn = getConnect();
			PreparedStatement ps = conn.prepareStatement(query);)
		{
			ps.setString(1, writer);
			ResultSet rs = ps.executeQuery();
			
			boolean found = false;
			
			while(rs.next()) {
				found = true;
				books.add(createBook(rs));
			}
			if(!found) {
				System.out.println(writer + " 작가는 없습니다.");
			}
		}catch(SQLException e) {
			throw new DMLException("책 찾는중 오류");
		}
		
		
		return books;
	}

	@Override
	public ArrayList<Book> findByPublisher(String publisher) throws DMLException {
		ArrayList<Book> books = new ArrayList<>();
		
		String query = "SELECT * FROM book WHERE publisher = ?";
		
		try(Connection conn = getConnect();
			PreparedStatement ps = conn.prepareStatement(query);)
		{
			ps.setString(1, publisher);
			ResultSet rs = ps.executeQuery();
			
			boolean found = false;
			
			while(rs.next()) {
				found = true;
				books.add(createBook(rs));
			}
			
			if(!found) {
				System.out.println(publisher + " 출판사는 없습니다.");
			}
			
		}catch(SQLException e) {
			throw new DMLException("책 찾는중 오류");
		}
		
		return books;
	}

	@Override
	public ArrayList<Book> findByPrice(int min, int max) throws DMLException, InvalidInputException {
		ArrayList<Book> books = new ArrayList<>();
		
		if (max < min) {
			throw new InvalidInputException("최소값이 최대값보다 클 수 없습니다.");
		}
		
		String query = "SELECT * FROM book WHERE price > ? AND price < ?";
		
		try(Connection conn = getConnect();
			PreparedStatement ps = conn.prepareStatement(query);)
		{
			ps.setInt(1, min);
			ps.setInt(2, max);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				books.add(createBook(rs));
			}
		}catch(SQLException e) {
			throw new DMLException("책 찾는중 오류");
		}
		
		return books;
	}

	@Override
	public void discountBook(int per, String publisher) throws DMLException {
		
		String query = "UPDATE book SET price = price * (1 - ?/100.0) WHERE publisher = ?";
		
		try(Connection conn =getConnect();
			PreparedStatement ps = conn.prepareStatement(query);)
		{
			ps.setInt(1, per);
			ps.setString(2, publisher);
			
			System.out.println(ps.executeUpdate() > 0 ? "할인 완료" : "해당 출판사의 책이 없습니다.");
			
		} catch(SQLException e) {
			throw new DMLException("책 할인중 오류");
		}
		
	}

}
