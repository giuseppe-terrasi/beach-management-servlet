package it.giuseppeterrasi.beachmanagement.daos;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

public class UmbrellaGridDao extends BaseDao implements Serializable {

	public UmbrellaGridDao() {
		super(null);
	}
	
	public UmbrellaGridDao(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -409327153221658650L;

    private int id;

    private int gridRow;

    private int gridColumn;

    private int capacity;

    private float price;
    
    private boolean available;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGridRow() {
		return gridRow;
	}

	public void setGridRow(int gridRow) {
		this.gridRow = gridRow;
	}

	public int getGridColumn() {
		return gridColumn;
	}

	public void setGridColumn(int gridColumn) {
		this.gridColumn = gridColumn;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

    public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int save() {
		int created = 0;
		try {
			connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM umbrella_grid WHERE grid_row = ? AND grid_column = ?");
			statement.setInt(1, gridRow);
			statement.setInt(2, gridColumn);
			statement.execute();
			
			ResultSet rs = statement.getResultSet();
			
			if(rs.isBeforeFirst()) {
				return -1;
			}
			else {
				rs.close();
				statement = connection.prepareStatement("INSERT INTO umbrella_grid(grid_row, grid_column, capacity, price) values(?, ?, ?, ?)");
				statement.setInt(1, gridRow);
				statement.setInt(2, gridColumn);
				statement.setInt(3, capacity);
				statement.setFloat(4, price);
				statement.executeUpdate();
				
				id = getInsertedRowId();			
				
				created = 1;
			}
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return created;
    }
    
    public Set<Entry<Integer, List<UmbrellaGridDao>>> getGrid() {
    	try {
			connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM umbrella_grid ORDER BY grid_row, grid_column ");
			statement.execute();
			
			ResultSet rs = statement.getResultSet();
			
			if(rs.isBeforeFirst()) {
				List<UmbrellaGridDao> list = new ArrayList<UmbrellaGridDao>();
				while(rs.next()) {
					UmbrellaGridDao item = new UmbrellaGridDao();
					item.id = rs.getInt("id");
					item.gridRow = rs.getInt("grid_row");
					item.gridColumn = rs.getInt("grid_column");
					item.capacity = rs.getInt("capacity");
					item.price = rs.getFloat("price");
					
					list.add(item);
				}
				rs.close();
				
		        return list.stream().collect(Collectors.groupingBy(g -> g.getGridRow())).entrySet();
			}
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    public Set<Entry<Integer, List<UmbrellaGridDao>>> getCurrentGridStatus() {
    	try {
			connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT g.*, b.id as bookingId " + 
					"FROM beachmanagement.umbrella_grid g " + 
					"left join booking_umbrellas bu on bu.umbrella_grid_id = g.id " + 
					"left join booking b on bu.booking_id = b.id and b.to_date >= now()  ");
			statement.execute();
			
			ResultSet rs = statement.getResultSet();
			
			if(rs.isBeforeFirst()) {
				List<UmbrellaGridDao> list = new ArrayList<UmbrellaGridDao>();
				while(rs.next()) {
					UmbrellaGridDao item = new UmbrellaGridDao();
					item.id = rs.getInt("id");
					item.gridRow = rs.getInt("grid_row");
					item.gridColumn = rs.getInt("grid_column");
					item.capacity = rs.getInt("capacity");
					item.price = rs.getFloat("price");
					
					rs.getInt("bookingId");
					
					item.available = rs.wasNull();
					
					list.add(item);
				}
				rs.close();
				
		        return list.stream().collect(Collectors.groupingBy(g -> g.getGridRow())).entrySet();
			}
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
    	
    	return null;
    }
}
