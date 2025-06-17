package com.jdbc.test;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jdbc.dao.impl.BookDAOImpl;
import com.jdbc.exception.BookNotFoundException;
import com.jdbc.exception.DuplicateISBNException;
import com.jdbc.exception.InvalidInputException;
import com.jdbc.vo.Book;

import config.ServerInfo;

public class BookDAOTest {

	public static void main(String[] args) {
		BookDAOImpl dao = BookDAOImpl.getInstance();
//		try {
//			dao.registerBook(new Book("1", "안녕하세요", "이우진", "한빛미디어", 3000));
//			dao.registerBook(new Book("2", "자바의 정석", "남궁성", "도우출판", 35000));
//			dao.registerBook(new Book("3", "모두의 딥러닝", "남궁성", "길벗", 22000));
//			dao.registerBook(new Book("4", "토비의 스프링", "이일민", "도우출판", 45000));
//		} catch(SQLException e){
//			System.out.println(e.getMessage());
//		} catch(DuplicateISBNException e) {
//			System.out.println(e.getMessage());
//		}
		
		try {
			dao.deleteBook("1");
		} catch(SQLException e){
			System.out.println(e.getMessage());
		} catch(BookNotFoundException e) {
			System.out.println(e.getMessage());
		}
			
		try {
			Book book = dao.findByBook("2", "자바의 정석");
			System.out.println(book);
		} catch(SQLException e){
			System.out.println(e.getMessage());
		} 
		
		try {
			ArrayList<Book> books = dao.findByWriter("dkdkdk");
			books.stream().forEach(System.out :: println);
		} catch(SQLException e){
			System.out.println(e.getMessage());
		} 
		
		try {
			ArrayList<Book> books = dao.findByPublisher("도우출판");
			books.stream().forEach(System.out :: println);
		} catch(SQLException e){
			System.out.println(e.getMessage());
		} 
		
		try {
			ArrayList<Book> books = dao.findByPrice(40000, 20000);
			books.stream().forEach(System.out :: println);
		} catch(SQLException e){
			System.out.println(e.getMessage());
		} catch(InvalidInputException e) {
			System.out.println(e.getMessage());
		}
		
//		try {
//			dao.discountBook(10, "도우출판");
//		} catch(SQLException e){
//			System.out.println(e.getMessage());
//		}
		
	}
	
	static {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("드라이브 연결 성공...");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
