package broker.twotier.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import broker.twotier.dao.DatabaseTemplate;
import broker.twotier.exception.DuplicateSSNException;
import broker.twotier.exception.InvalidTransactionException;
import broker.twotier.exception.RecordNotFoundException;
import broker.twotier.vo.CustomerRec;
import broker.twotier.vo.SharesRec;
import broker.twotier.vo.StockRec;
import config.ServerInfo;

public class Database implements DatabaseTemplate {

	private static Database database = new Database("127.0.0.1");

	private Database(String ServerIp) {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("드라이브 로딩 성공");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static Database getInstance() {
		return database;
	}

//////////////////// 공통로직 ///////////////////////////
	private CustomerRec newCustomer(ResultSet rs) throws SQLException {
		return new CustomerRec(rs.getString("ssn"), rs.getString("cust_name"), rs.getString("address"));
	}

	private SharesRec newShareRec(ResultSet rs) throws SQLException {
		return new SharesRec(rs.getString("ssn"), rs.getString("symbol"), rs.getInt("quantity"));
	}

	private StockRec newStock(ResultSet rs) throws SQLException {
		return new StockRec(rs.getString("symbol"), rs.getInt("price"));
	}

	private Connection getConnect() throws SQLException {
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
		System.out.println("DB Connecting....");
		return conn;
	}

	/// DML ///
	private void closeAll(PreparedStatement ps, Connection conn) throws SQLException {
		if (ps != null)
			ps.close();
		if (conn != null)
			conn.close();
	}

	/// SELECT ///
	private void closeAll(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		if (rs != null)
			rs.close();
		closeAll(ps, conn);
	}

	private boolean isExist(String ssn, Connection conn) throws SQLException {
		String query = "SELECT ssn FROM customer WHERE ssn = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, ssn);
		ResultSet rs = ps.executeQuery();

		return rs.next(); // ssn이 있으면 true 없으면 false 반환
	}

///////////////////////// 비지니스 로직 ///////////////////////////////////
	@Override
	public void addCustomer(CustomerRec cust) throws SQLException, DuplicateSSNException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();

			if (isExist(cust.getSsn(), conn)) { // 추가하려는 ssn이 있다면
				throw new DuplicateSSNException("추가하려는 고객은 이미 등록된 상태입니다.");
			}

			String query = "INSERT INTO customer(ssn, cust_name, address) VALUES(?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, cust.getSsn());
			ps.setString(2, cust.getCustName());
			ps.setString(3, cust.getAddress());

			System.out.println(ps.executeUpdate() + "명 INSERT 성공 ...addCustomer");
		} finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public void deleteCustomer(String ssn) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();

			if (!isExist(ssn, conn)) { // 삭제하려는 ssn이 있다면
				throw new RecordNotFoundException("삭제하려는 고객이 없습니다.");
			}

			String query = "DELETE FROM customer WHERE ssn = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, ssn);

			System.out.println(ps.executeUpdate() + "명 DELETE 성공 ...deleteCustomer");
		} finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public void updateCustomer(CustomerRec cust) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();

			if (!isExist(cust.getSsn(), conn)) { // 수정하려는 ssn이 있다면
				throw new RecordNotFoundException("수정하려는 고객이 없습니다..");
			}

			String query = "UPDATE customer SET cust_name = ?, address = ? WHERE ssn = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, cust.getCustName());
			ps.setString(2, cust.getAddress());
			ps.setString(3, cust.getSsn());

			System.out.println(ps.executeUpdate() + "명 UPDATE 성공 ...updateCustomer");
		} finally {
			closeAll(ps, conn);
		}
	}

	@Override
	public ArrayList<SharesRec> getPortfolio(String ssn) throws SQLException {
		ArrayList<SharesRec> shares = new ArrayList<>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnect();

			String query = "SELECT * FROM shares WHERE ssn = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, ssn);
			rs = ps.executeQuery();

			while (rs.next()) {
				shares.add(newShareRec(rs));
			}

			System.out.println();

		} finally {
			closeAll(rs, ps, conn);
		}

		return shares;
	}
	/////////// getAllCustomer, getCustomer를 위한 getPortfolio //////////////////
	/*
	 * public ArrayList<SharesRec> getPortfolio(String ssn, Connection conn) throws
	 * SQLException { ArrayList<SharesRec> shares = new ArrayList<>();
	 * 
	 * PreparedStatement ps = null; ResultSet rs = null;
	 * 
	 * try {
	 * 
	 * if(!isExist(ssn, conn)) { throw new SQLException("해당 고객을 찾을수 없습니다."); }
	 * 
	 * String query = "SELECT * FROM shares WHERE ssn = ?"; ps =
	 * conn.prepareStatement(query); ps.setString(1, ssn); rs = ps.executeQuery();
	 * 
	 * while(rs.next()) { shares.add(newShareRec(rs)); }
	 * 
	 * } finally { }
	 * 
	 * return shares; }
	 */

	@Override
	public CustomerRec getCustomer(String ssn) throws SQLException {
		CustomerRec customer = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnect();

			if (!isExist(ssn, conn)) {
				throw new SQLException("해당 고객을 찾을수 없습니다.");
			}

			String query = "SELECT * FROM customer WHERE ssn = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, ssn);
			rs = ps.executeQuery();

			if (rs.next()) {
				customer = (newCustomer(rs));
			}
			customer.setportfolio(getPortfolio(ssn));

			System.out.println();

		} finally {
			closeAll(rs, ps, conn);
		}

		return customer;
	}

	@Override
	public ArrayList<CustomerRec> getAllCustomers() throws SQLException {
		ArrayList<CustomerRec> customers = new ArrayList<>();

		CustomerRec customer = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnect();

			String query = "SELECT * FROM customer ";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				customer = newCustomer(rs);
				customer.setportfolio(getPortfolio(customer.getSsn()));
				customers.add(customer);
			}

		} finally {
			closeAll(rs, ps, conn);
		}

		return customers;
	}

	@Override
	public ArrayList<StockRec> getAllStocks() throws SQLException {
		ArrayList<StockRec> stockRecs = new ArrayList<>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnect();
			String query = "SELECT * FROM stock";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				stockRecs.add(newStock(rs));
			}

		} finally {
			closeAll(rs, ps, conn);
		}

		return stockRecs;
	}

	@Override
	public void buyShares(String ssn, String symbol, int quantity) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnect();

			String checkQuery = "SELECT quantity FROM shares WHERE ssn = ? AND symbol = ?";
			ps = conn.prepareStatement(checkQuery);
			ps.setString(1, ssn);
			ps.setString(2, symbol);
			rs = ps.executeQuery();

			if (rs.next()) {
				int quantitys = rs.getInt("quantity");
				closeAll(ps, null);

				String query = "UPDATE shares SET quantity = ? WHERE ssn =? AND symbol = ?";
				ps = conn.prepareStatement(query);
				ps.setInt(1, quantitys + quantity);
				ps.setString(2, ssn);
				ps.setString(3, symbol);

				System.out.println(ps.executeUpdate() == 1 ? "매매 증가 성공" : "매매 증가 실패");

			} else {

				closeAll(ps, null);

				String query = "INSERT INTO shares VALUES(?, ?, ?)";

				ps = conn.prepareStatement(query);

				ps.setString(1, ssn);
				ps.setString(2, symbol);
				ps.setInt(3, quantity);

				System.out.println(ps.executeUpdate() == 1 ? "매매 추가 성공" : "매매 추가 실패");
			}

		} finally {
			closeAll(rs, ps, conn);
		}
	}

	@Override
	public void sellShares(String ssn, String symbol, int quantity)
			throws SQLException, InvalidTransactionException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnect();
			
			conn.setAutoCommit(false);
			
			String checkQuery = "SELECT quantity FROM shares WHERE ssn = ? AND symbol = ?";
			ps = conn.prepareStatement(checkQuery);

			ps.setString(1, ssn);
			ps.setString(2, symbol);
			rs = ps.executeQuery();

			if (rs.next()) {
				int quantitys = rs.getInt("quantity");
				closeAll(ps, null);

				if(quantitys < quantity) {
					throw new InvalidTransactionException("팔려는 수량이 보유 수량보다 많습니다.");
				} else if(quantitys == quantity) {
					String query = "DELETE FROM shares WHERE ssn = ? AND symbol = ?";
					ps = conn.prepareStatement(query);
					
					ps.setString(1, ssn);
					ps.setString(2, symbol);

					System.out.println(ps.executeUpdate() == 1 ? "매도 삭제 성공" : "매매 삭제 실패");
					
				} else {
					String query = "UPDATE shares SET quantity = ? WHERE ssn =? AND symbol = ?";
					ps = conn.prepareStatement(query);
					
					ps.setInt(1, quantitys - quantity);
					ps.setString(2, ssn);
					ps.setString(3, symbol);

					System.out.println(ps.executeUpdate() == 1 ? "매도 뺴기 성공" : "매매 빼기 실패");
				}
				
				conn.commit();
			} else {
				throw new RecordNotFoundException("팔려는 주식이 없습니다.");
			}

		} catch (SQLException e) {
			throw new InvalidTransactionException("트렌젝션 오류입니다.");
		} finally {
			conn.setAutoCommit(true);
			closeAll(rs, ps, conn);
		}
	}

}
