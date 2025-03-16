package dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.UUID;

import model.User;

public class UserDAO extends DAO {
	public UserDAO() {
		super();
		connect();
	}
	public boolean insert(final User user) {
		boolean status = false;
		String sql = "INSERT INTO users (id, username, password, is_admin, created_at, updated_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		try(PreparedStatement stmt = connection.prepareStatement(sql)) {
			Statement st = connection.createStatement();
			stmt.setObject(1, user.getId());
			stmt.setString(2, user.getUsername());
			stmt.setString(3, toMD5(user.getPassword()));
			stmt.setBoolean(4, user.isAdmin());
			stmt.setTimestamp(5, Timestamp.from(user.getCreatedAt()));
			stmt.setTimestamp(6, Timestamp.from(user.getUpdatedAt()));

			stmt.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}


	public boolean update(final User user) {
		boolean status = false;
		String sql = "UPDATE users SET username = ?, password = ?, updated_at = ? WHERE id = ?";

		try (PreparedStatement st = connection.prepareStatement(sql)) {
			st.setString(1, user.getUsername());
			st.setString(2, toMD5(user.getPassword()));
			st.setTimestamp(3, Timestamp.from(user.getUpdatedAt()));
			st.setObject(4, user.getId());

			int rowsAffected = st.executeUpdate();
			status = rowsAffected > 0;
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
		String sql = "SELECT 1 FROM users WHERE username = ? AND password = ?";
		boolean resp = false;

		try (PreparedStatement st = connection.prepareStatement(sql)) {
			st.setString(1, username);
			st.setString(2, toMD5(password));

			try (ResultSet rs = st.executeQuery()) {
				resp = rs.next();
			}
		} catch (SQLException u) {
			throw new RuntimeException(u);
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
