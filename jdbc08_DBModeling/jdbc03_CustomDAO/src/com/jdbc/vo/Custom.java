package com.jdbc.vo;

public class Custom {
	private int id;
	private String name;
	private String address;
	
	public Custom() {
		
	}
	
	public Custom(int id, String name, String address) {
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Custom [id=" + id + ", name=" + name + ", address=" + address + "]";
	}
	
}
