package dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import model.User;

public class UserDAO extends DAO {
	public UserDAO() {
		super();
		connect();
	}
	public boolean insert(final User user) {
		boolean status = false;
		try {  
			Statement st = connection.createStatement();
			String sql = "INSERT INTO users (id, username, password, is_admin, created_at, updated_at) "
				       + "VALUES ('"+user.getId()+ "'::UUID, '" + user.getUsername() + "', '"
				       + this.toMD5(user.getPassword()) + "', '" + user.isAdmin() + "', '" + user.getCreatedAt() + "', '" + user.getUpdatedAt() + "');";
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean update(final User user) {
		boolean status = false;
		try {  
			Statement st = connection.createStatement();
			String sql = "UPDATE users SET username = '" + user.getUsername() + "', password = '"
				       + user.getPassword() + "', updated_at = '" + user.getCreatedAt() + "'"
					   + " WHERE id = " + user.getId();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(UUID id) {
		boolean status = false;
		try {  
			Statement st = connection.createStatement();
			String sql = "DELETE FROM users WHERE id = " + id;
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean login(final String username, final String password) {
		boolean resp = false;
		
		try {
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + this.toMD5(password)  + "'";
			ResultSet rs = st.executeQuery(sql);
			resp = rs.next();
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resp;
	}

	private String toMD5(String password) {
		try{
			MessageDigest m=MessageDigest.getInstance("MD5");
			m.update(password.getBytes(),0, password.length());
			return new BigInteger(1,m.digest()).toString(16);
		} catch (Exception e){
			throw new RuntimeException("Unable to cryptography the password");
		}
	}
}
