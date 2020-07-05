package it.giuseppeterrasi.beachmanagement.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import it.giuseppeterrasi.beachmanagement.models.AppUser;

public class BookingDao extends BaseDao{

	private static final long serialVersionUID = 2117782949308761480L;

	public BookingDao(DataSource dataSource) {
		super(dataSource);
		umbrellas = new ArrayList<UmbrellaGridDao>();
	}
	
	public BookingDao() {
		super(null);
		umbrellas = new ArrayList<UmbrellaGridDao>();
	}
	
	private long id;
	
	private Date from;
	
	private Date to;
	
	private int numberOfPersons;
	
	private AppUser user;
	
	private List<UmbrellaGridDao> umbrellas;
	
	private double price;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public int getNumberOfPersons() {
		return numberOfPersons;
	}

	public void setNumberOfPersons(int numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public List<UmbrellaGridDao> getUmbrellas() {
		return umbrellas;
	}

	public void setUmbrellas(List<UmbrellaGridDao> umbrellas) {
		this.umbrellas = umbrellas;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public List<BookingDao> getAllBookings() {
		return getAllBookings(0, null, null);
	}
	
	public List<BookingDao> getAllUserBookings(long userId) {
		return getAllBookings(userId, null, null);
	}
	
	public List<BookingDao> getAllBookings(long userId, java.sql.Date fromDate, java.sql.Date toDate) {
		
		List<BookingDao> bookings = new ArrayList<BookingDao>();
		
		int numberOfParameters = 0;
		Map<String, Integer> parameterIndexMap = new HashMap<String, Integer>();
		
    	try {
			connection = dataSource.getConnection();
			
			String query = "SELECT b.*, g.id as umbrellaGridId, g.grid_row, g.grid_column, g.capacity, g.price, u.username, u.first_name as userFirstName, u.last_name as userLastName " + 
					"FROM booking b " + 
					"inner join booking_umbrellas bu on bu.booking_id = b.id " + 
					"inner join umbrella_grid g on bu.umbrella_grid_id = g.id " +
					"inner join users u on b.user_id = u.id ";
			
			if(userId > 0) {
				query += "where u.id = ? ";
				numberOfParameters++;
				parameterIndexMap.put("userId", numberOfParameters);
			}
			
			if(fromDate != null) {
				query += (numberOfParameters > 0 ? "AND" : "WHERE") + " b.from_date = ? ";
				numberOfParameters++;
				parameterIndexMap.put("fromDate", numberOfParameters);
			}
			
			if(toDate != null) {
				query += (numberOfParameters > 0 ? "AND" : "WHERE") + " b.to_date = ? ";
				numberOfParameters++;
				parameterIndexMap.put("toDate", numberOfParameters);
			}
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			if(userId > 0) {
				statement.setLong(parameterIndexMap.get("userId"), userId);
			}
			
			if(fromDate != null) {
				statement.setDate(parameterIndexMap.get("fromDate"), fromDate);
			}
			
			if(toDate != null) {
				statement.setDate(parameterIndexMap.get("toDate"), toDate);
			}
			
			statement.execute();
			
			ResultSet rs = statement.getResultSet();
			
			long currentBookingId = 0;
			
			if(rs.isBeforeFirst()) {
				while(rs.next()) {
					
					UmbrellaGridDao umbrellaItem = new UmbrellaGridDao();
					umbrellaItem.setId(rs.getInt("umbrellaGridId"));
					umbrellaItem.setGridRow(rs.getInt("grid_row"));
					umbrellaItem.setGridColumn(rs.getInt("grid_column"));
					umbrellaItem.setCapacity(rs.getInt("capacity"));
					umbrellaItem.setPrice(rs.getFloat("price"));
					
					if(currentBookingId == rs.getLong("id")) {					
						bookings.get(bookings.size() - 1).umbrellas.add(umbrellaItem);
					}
					else {
						currentBookingId =  rs.getLong("id");
						BookingDao item = new BookingDao();
						item.id = rs.getLong("id");
						item.from = rs.getTimestamp("from_date");
						item.to = rs.getTimestamp("to_date");
						item.numberOfPersons = rs.getInt("number_of_persons");
						item.user = new AppUser();
						item.user.setId(rs.getLong("user_id"));
						item.user.setFirstName(rs.getString("userFirstName"));
						item.user.setLastName(rs.getString("userLastName"));
						item.user.setUsername(rs.getString("username"));
						item.umbrellas.add(umbrellaItem);
						bookings.add(item);
					}
				}
				rs.close();
			}
			
			connection.close();
			
			for (BookingDao bookingDao : bookings) {
				bookingDao.price = bookingDao.umbrellas.stream().mapToDouble(u -> u.getPrice()).sum();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
    	
    	return bookings;
	}
	
}
