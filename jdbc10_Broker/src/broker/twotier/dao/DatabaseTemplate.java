package broker.twotier.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import broker.twotier.exception.DuplicateSSNException;
import broker.twotier.exception.InvalidTransactionException;
import broker.twotier.exception.RecordNotFoundException;
import broker.twotier.vo.CustomerRec;
import broker.twotier.vo.SharesRec;
import broker.twotier.vo.StockRec;

public interface DatabaseTemplate {
	
	//비지니스 로직...CRUD
	void addCustomer(CustomerRec cust)throws SQLException,DuplicateSSNException;
	void deleteCustomer(String ssn)throws SQLException,RecordNotFoundException;
	void updateCustomer(CustomerRec cust)throws SQLException,RecordNotFoundException;
	
	ArrayList<SharesRec> getPortfolio(String ssn) throws SQLException;
	CustomerRec getCustomer(String ssn) throws SQLException;
	ArrayList<CustomerRec> getAllCustomers() throws SQLException;
	ArrayList<StockRec> getAllStocks() throws SQLException;
	
	//비지니스 로직...특화된 로직
	void buyShares(String ssn, String symbol, int quantity)throws SQLException;
	void sellShares(String ssn, String symbol, int quantity)throws SQLException,InvalidTransactionException,RecordNotFoundException;

}













