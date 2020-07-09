package it.giuseppeterrasi.beachmanagement.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

public abstract class BaseDao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 644074527425903356L;

	protected DataSource dataSource;
	
	protected Connection connection;
	
	protected Map<String, String> errors; 
	
	protected boolean insertSucceded;
	
	public BaseDao(DataSource dataSource) {
		this.dataSource = dataSource;
		errors = new HashMap<String, String>();
	}
	
	protected int getInsertedRowId() throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT LAST_INSERT_ID();");
		
		statement.execute();
		
		int rowId = 0;
		
		ResultSet rs = statement.getResultSet();
		
		if(rs.isBeforeFirst()) {
			rs.next();
			
			rowId = rs.getInt(1);
			
		}
		
		rs.close();
		
		return rowId;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	public boolean isInsertSucceded() {
		return insertSucceded;
	}

	public void setInsertSucceded(boolean insertSucceded) {
		this.insertSucceded = insertSucceded;
	}
}
