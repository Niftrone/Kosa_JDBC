package com.jdbc.dao;

import java.sql.SQLException;
import java.util.List;

import com.jdbc.vo.Custom;

public interface CustomDAO {
	void addCustom(Custom custom) throws SQLException;
	void removeCustom(int id) throws SQLException;
	void updateCustom(Custom custom) throws SQLException;
	
	Custom getCustom(int id) throws SQLException;
	List<Custom> getCustom() throws SQLException;
}
