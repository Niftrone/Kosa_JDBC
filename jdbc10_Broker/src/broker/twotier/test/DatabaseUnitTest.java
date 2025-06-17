package broker.twotier.test;


import java.sql.SQLException;
import java.util.ArrayList;

import broker.twotier.dao.impl.Database;
import broker.twotier.exception.InvalidTransactionException;
import broker.twotier.exception.RecordNotFoundException;
import broker.twotier.vo.CustomerRec;
import broker.twotier.vo.SharesRec;

/*
 * 비지니스로직하나하나에 대한 단위 테스트 클래스
 * */
public class DatabaseUnitTest {

	public static void main(String[] args) {
		Database db = Database.getInstance();
		
//		try {
//			db.addCustomer(new CustomerRec("777-777", "이우진", "경기도"));
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		
//		try {
//			db.deleteCustomer("777-777");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		} 
//		
//		try {
//			db.updateCustomer(new CustomerRec("222-222", "이우진", "경기도"));
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
//		try {
//			ArrayList<SharesRec> shares = db.getPortfolio("222-222");
//			System.out.println(shares);
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		}
//		

//		
//		try {
//			System.out.println(db.getAllCustomers());
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		}
//		
//		try {
//			System.out.println(db.getAllStocks());
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		}
		
//		try {
//			db.buyShares("222-222", "ABStk", 50);
//			db.buyShares("222-222", "SUNW", 12);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
		try {
			db.sellShares("222-222", "ABStk", 50);
			db.sellShares("222-222", "SUNW", 12);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			CustomerRec customer = db.getCustomer("222-222");
			System.out.println(customer);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		

		
	}

}
