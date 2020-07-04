package it.giuseppeterrasi.beachmanagement.daos;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.sql.DataSource;

import it.giuseppeterrasi.beachmanagement.models.AppUser;

public class UserDao implements Serializable {

	private static final long serialVersionUID = 5516496197270220067L;

	private static final SecureRandom RAND = new SecureRandom();
	
	private static final int ITERATIONS = 65536;
  	private static final int KEY_LENGTH = 512;
  	private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
  	private static final String SALT = "l235uZaiME4YZuyCbTgbUOhCPGo=";
	
	private String firstName;
	
	private String lastName;
	
	private String username;
	
	private String password;
	
	private String confirmPassword;
	
	public UserDao() {
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	public AppUser login(DataSource dataSource) {
		AppUser appUser = null;
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT first_name, last_name, username, role " +
					"FROM beachmanagement.users u " + 
					"INNER JOIN user_roles ur on ur.user_id = u.id " + 
					"INNER JOIN roles r on ur.role_id = r.id WHERE username = ? and password = ?");
			statement.setString(1, username);
			statement.setString(2, hashPassword(password, SALT).get());
			statement.execute();
			
			ResultSet rs = statement.getResultSet();
			
			if(rs.isBeforeFirst()) {
				appUser = new AppUser();
				
				while(rs.next()) {
					appUser.setFirstName(rs.getString("first_name"));
					appUser.setLastName(rs.getString("last_name"));
					appUser.getRoles().add(rs.getString("role"));
				}
			}
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return appUser;
	}
	
	public int create(DataSource dataSource) {
		int created = 0;
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
			statement.setString(1, username);
			statement.execute();
			
			ResultSet rs = statement.getResultSet();
			
			if(rs.isBeforeFirst()) {
				return -1;
			}
			else {
				rs.close();
				statement = connection.prepareStatement("INSERT INTO users(first_name, last_name, username, password, active) values(?, ?, ?, ?, ?)");
				statement.setString(1, firstName);
				statement.setString(2, lastName);
				statement.setString(3, username);
				statement.setString(4, hashPassword(password, SALT).get());
				statement.setInt(5, 1);
				statement.executeUpdate();
				
				created = 1;
			}
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return created;
	}
	
	public static Optional<String> generateSalt (final int length) {

	    if (length < 1) {
	      System.err.println("error in generateSalt: length must be > 0");
	      return Optional.empty();
	    }
	
	    byte[] salt = new byte[length];
	    RAND.nextBytes(salt);
	
	    return Optional.of(Base64.getEncoder().encodeToString(salt));
	}
	
	 private static Optional<String> hashPassword (String password, String salt) {

	    char[] chars = password.toCharArray();
	    byte[] bytes = salt.getBytes();

	    PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);

	    Arrays.fill(chars, Character.MIN_VALUE);

	    try {
	      SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
	      byte[] securePassword = fac.generateSecret(spec).getEncoded();
	      return Optional.of(Base64.getEncoder().encodeToString(securePassword));

	    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
	      System.err.println("Exception encountered in hashPassword()");
	      return Optional.empty();

	    } finally {
	      spec.clearPassword();
	    }
	 }
}
