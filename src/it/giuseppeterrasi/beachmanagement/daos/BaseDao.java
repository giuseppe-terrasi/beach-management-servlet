package it.giuseppeterrasi.beachmanagement.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public abstract class BaseDao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 644074527425903356L;

	protected DataSource dataSource;
	
	protected Connection connection;
	
	public BaseDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	protected int getInsertedRowId() throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT LAST_INSERT_ID();");
		
		statement.execute();
		
		int rowId = 0;
		
		ResultSet rs = statement.getResultSet();
		
		if(rs.isBeforeFirst()) {
			rs.next();
			
			rowId = rs.getInt(0);
			
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
}
