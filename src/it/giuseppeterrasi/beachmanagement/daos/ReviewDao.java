package it.giuseppeterrasi.beachmanagement.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class ReviewDao extends BaseDao {

	private static final long serialVersionUID = -8588464674259715906L;

	public ReviewDao(DataSource dataSource) {
		super(dataSource);
	}
	
	public ReviewDao() {
		super(null);
	}
	
	private long id;
	
	private long userId;
	
	private int score;
	
	private String title;
	
	private String message;
	
	private Timestamp reviewedOn;
	
	private String username;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getReviewedOn() {
		return reviewedOn;
	}

	public void setReviewedOn(Timestamp reviewedOn) {
		this.reviewedOn = reviewedOn;
	}	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int save() {
		int created = 0;
		
		if(score <= 0 || score > 5 ) {
			errors.put("score","Score must be between 1 and 5");
		}
		
		if(title == null || title.isEmpty()) {
			errors.put("title","Title is required");
		}
		
		if(message == null || message.isEmpty()) {
			errors.put("message","Message is required");
		}
		
		if(errors.size() > 0) return -1;
		
		try {
			connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement("INSERT INTO reviews(user_id, score, title, message, reviewed_on) values(?, ?, ?, ?, ?)");
			statement.setLong(1, userId);
			statement.setInt(2, score);
			statement.setString(3, title);
			statement.setString(4, message);
			statement.setTimestamp(5, reviewedOn);
			statement.executeUpdate();
			
			id = getInsertedRowId();
			
			created = 1;
			insertSucceded = true;
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			errors.put("error", "An error occurred while savig the data");
			created = -1;
		}
		
		return created;
	}
	
	public List<ReviewDao> getReviews(int limit) {
		
		List<ReviewDao> reviews = new ArrayList<ReviewDao>();
		
		PreparedStatement statement;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement("SELECT r.*, u.username FROM reviews r inner join users u on r.user_id = u.id ORDER BY reviewed_on DESC, score DESC LIMIT ?");
			
			statement.setInt(1, limit);
			statement.execute();
			
			ResultSet rs = statement.getResultSet();
			
			while(rs.next())
			{
				ReviewDao item = new ReviewDao();
				
				item.id = rs.getLong("id");
				item.userId = rs.getLong("user_id");
				item.score = rs.getInt("score");
				item.title = rs.getString("title");
				item.message = rs.getString("message");
				item.reviewedOn = rs.getTimestamp("reviewed_on");
				item.username = rs.getString("username");
				
				reviews.add(item);
			}
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		return reviews;
	}
	
}
