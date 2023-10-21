package edu.cs244.taskpulse.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.cs244.taskpulse.utils.DatabaseHandler;
import edu.cs244.taskpulse.utils.Hasher;

public class User {

	private int userId; // Unique identifier for each user
	private String username;
	private String hashedPassword; // Hashed for security reasons
	private String email;

	// Constructors:

	// Default constructor
	public User() {
	}

	// Parameterized constructor
	public User(String username, String password, String email) {
		this.username = username;
		this.hashedPassword = Hasher.getSHA(password); // Placeholder for hashing logic
		this.email = email;
	}

	// Getter methods:

	public int getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	// We won't have a getter for the password for security reasons

	// Setter methods:

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.hashedPassword = Hasher.getSHA(password); // Hashing the password before storing
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// Operational Methods:

	// Register a new user
	public boolean register() {
		Connection connection = null;
		String checkUserQuery = "SELECT * FROM users WHERE username = ? OR email = ?";
		String insertUserQuery = "INSERT INTO users (username, hashed_password, email) VALUES (?, ?, ?)";

		try {
			connection = DatabaseHandler.getConnection();

			// Using try-with-resources for PreparedStatement
			try (PreparedStatement checkStmt = connection.prepareStatement(checkUserQuery);
					PreparedStatement insertStmt = connection.prepareStatement(insertUserQuery)) {

				// 1. Check if a user with the given username or email already exists
				checkStmt.setString(1, this.username);
				checkStmt.setString(2, this.email);

				try (ResultSet rs = checkStmt.executeQuery()) {
					if (rs.next()) {
						// User with the provided username or email already exists
						return false;
					}
				}

				// 2. If not, then insert the user details to the database
				insertStmt.setString(1, this.username);
				insertStmt.setString(2, this.hashedPassword); // Ensure this is the hashed password
				insertStmt.setString(3, this.email);

				int rowsAffected = insertStmt.executeUpdate();

				// Commit the changes
				connection.commit();

				// If the insertion is successful, rowsAffected should be 1
				return rowsAffected == 1;

			}

		} catch (SQLException ex) {
			if (connection != null) {
				try {
					// Roll back the transaction in case of an error
					connection.rollback();
				} catch (SQLException e) {
					e.printStackTrace(); // Consider using a logging framework
				}
			}
			ex.printStackTrace(); // Consider using a logging framework
			return false;
		} finally {
			// Ensure connection is closed after operation
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Login method for the user
	public static boolean login(String inputUsername, String inputPassword) {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connection = DatabaseHandler.getConnection();

			// 1. Retrieve the stored hashed password for the given username
			String retrieveUserQuery = "SELECT hashed_password FROM users WHERE username = ?";
			stmt = connection.prepareStatement(retrieveUserQuery);
			stmt.setString(1, inputUsername);

			rs = stmt.executeQuery();

			if (rs.next()) {
				String storedHashedPassword = rs.getString("hashed_password");

				// 2. Hash the password provided by the user attempting to login
				String hashedInputPassword = Hasher.getSHA(inputPassword); // This is the same hashPassword method as in
																			// the User class

				// 3. Compare the two hashes
				if (storedHashedPassword.equals(hashedInputPassword)) {
					// Successful login
					updateLastLogin(connection, inputUsername); // Updating the lastLogin
					return true;
				}
			}

			return false; // Username not found or password mismatch

		} catch (SQLException ex) {
			ex.printStackTrace(); // Consider using a logging framework
			return false;
		} finally {
			// Close resources
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	// Helper method to update the lastLogin for the user
	private static void updateLastLogin(Connection connection, String username) throws SQLException {
		String updateLastLoginQuery = "UPDATE users SET last_login = NOW() WHERE username = ?";
		try (PreparedStatement stmt = connection.prepareStatement(updateLastLoginQuery)) {
			stmt.setString(1, username);
			stmt.executeUpdate();
		}
	}

}
