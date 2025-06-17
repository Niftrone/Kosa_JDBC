package broker.twotier.vo;

import java.util.ArrayList;

public class CustomerRec {
	private String ssn;
	private String custName; //컬럼명은 cust_name
	private String address;
	private ArrayList<SharesRec> portfolio;
	
	public CustomerRec() {
		
	}
	
	public CustomerRec(String ssn, String custName, String address, ArrayList<SharesRec> portfolio) {
		super();
		this.ssn = ssn;
		this.custName = custName;
		this.address = address;
		this.portfolio = portfolio;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ArrayList<SharesRec> getportfolio() {
		return portfolio;
	}

	public void setportfolio(ArrayList<SharesRec> portfolio) {
		this.portfolio = portfolio;
	}

	@Override
	public String toString() {
		return "CustomerRec [ssn=" + ssn + ", custName=" + custName + ", address=" + address + ", portfolio=" + portfolio
				+ "]";
	} 
	
}
