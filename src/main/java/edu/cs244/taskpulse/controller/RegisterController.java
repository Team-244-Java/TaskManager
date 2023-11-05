package edu.cs244.taskpulse.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import edu.cs244.taskpulse.loader.DashboardLoader;
import edu.cs244.taskpulse.loader.LoginLoader;
import edu.cs244.taskpulse.models.User;
import edu.cs244.taskpulse.utils.DatabaseHandler;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RegisterController {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private FontAwesomeIcon closeBtn;

	@FXML
	private PasswordField confirmPassword;

	@FXML
	private Label confirmPasswordLabel;

	@FXML
	private Pane content_area;

	@FXML
	private FontAwesomeIcon copyright;

	@FXML
	private Label emailLabel;

	@FXML
	private FontAwesomeIcon greenCheckIcon;

	@FXML
	private Label loginErrorAnnouncement;

	@FXML
	private PasswordField password;

	@FXML
	private Label passwordLabel;

	@FXML
	private ImageView profileIcon;

	@FXML
	private FontAwesomeIcon redFailIcon;

	@FXML
	private Button registerBtn;

	@FXML
	private FontAwesomeIcon returnLoginArrow;

	@FXML
	private Button returnLoginBtn;

	@FXML
	private TextField userEmail;

	@FXML
	private TextField username;

	@FXML
	private Label usernameLabel;

	@FXML
	private Label welcomeLabel;

	// Define a regular expression pattern for a valid email address
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

	private final PauseTransition pause = new PauseTransition(Duration.millis(500));

	public void initialize() {
		pause.setOnFinished(e -> {
			String newValue = username.getText();
			if (newValue.isEmpty()) {
				greenCheckIcon.setVisible(false);
				redFailIcon.setVisible(false);
			} else {
				boolean isUsernameAvailable = checkUsernameAvailability(newValue);
				if (isUsernameAvailable) {
					greenCheckIcon.setVisible(true);
					redFailIcon.setVisible(false);
				} else {
					greenCheckIcon.setVisible(false);
					redFailIcon.setVisible(true);
				}
			}
		});

		username.textProperty().addListener((observable, oldValue, newValue) -> {
			// Reset the delay timer whenever the user types
			pause.stop();
			pause.play();
		});
	}

	@FXML
	void actionClose(MouseEvent event) {

	}

	@FXML
	void actionRegisterBtn() {
		  String userName = username.getText();
		    String email = userEmail.getText();
		    String passWord = password.getText();
		    String confirmpassword = confirmPassword.getText();

		    // Check each field for validity and return if invalid
		    
		    if (userName.isEmpty()) {
		        loginErrorAnnouncement.setText("Username field is empty");
		        return;
		    }
		    if (!checkUsernameAvailability(userName)) {
		        loginErrorAnnouncement.setText("Username has already been taken");
		        return;
		    }
		    if (email.isEmpty()) {
		        loginErrorAnnouncement.setText("Email field is empty");
		        return;
		    }
		    Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
		    if (!emailMatcher.matches()) {
		        loginErrorAnnouncement.setText("Email format is incorrect");
		        return;
		    }
		    if (!checkEmailAvailability(email)) {
		        loginErrorAnnouncement.setText("Email has already been in use");
		        return;
		    }
		    if (passWord.isEmpty()) {
		        loginErrorAnnouncement.setText("Password field is empty");
		        return;
		    }
		    if (confirmpassword.isEmpty()) {
		        loginErrorAnnouncement.setText("Confirm Password field is empty");
		        return;
		    }
		    if (!passWord.equals(confirmpassword)) {
		        loginErrorAnnouncement.setText("Confirm Password is not matching");
		        return;
		    }

		    // All checks passed, proceed with registration
		    userEmail.setStyle(""); // Reset style

		    User newUser = new User(userName, passWord, email);

		    boolean registrationSuccess = newUser.register();
		    if (registrationSuccess) {
		        loginErrorAnnouncement.setText(""); // Clear any previous error messages
		        DashboardLoader dashboardLocader = new DashboardLoader();
		        dashboardLocader.start((Stage) registerBtn.getScene().getWindow());
		        // Registration was successful, you can navigate to another page
		        // or display a success message
		    } else {
		        // Registration failed, you can show an error message
		        loginErrorAnnouncement.setText("Registration failed. Please try again.");
		    }

	}

	@FXML
	void actionReturnLogin() {
		Stage currentStage = (Stage) registerBtn.getScene().getWindow();

		// Create a new instance of RegisterLoader
		LoginLoader loginLoader = new LoginLoader();

		// Start the registration screen
		loginLoader.start(currentStage);
	}

	@FXML
	void setOnMouseDragged(MouseEvent event) {

	}

	@FXML
	void setOnMousePressed(MouseEvent event) {

	}

	private boolean checkUsernameAvailability(String username) {
		boolean isUsernameAvailable = true;

		Connection connection = null;
		String checkUserQuery = "SELECT COUNT(*) FROM users WHERE username = ?";

		try {
			connection = DatabaseHandler.getConnection();

			// Using try-with-resources for PreparedStatement
			try (PreparedStatement checkStmt = connection.prepareStatement(checkUserQuery)) {
				checkStmt.setString(1, username);

				try (ResultSet resultSet = checkStmt.executeQuery()) {
					if (resultSet.next()) {
						int count = resultSet.getInt(1);
						isUsernameAvailable = count == 0;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Handle database connection errors
		}

		return isUsernameAvailable;
	}

	private boolean checkEmailAvailability(String email) {
		boolean isEmailAvailable = true;

		Connection connection = null;
		String checkUserQuery = "SELECT COUNT(*) FROM users WHERE email = ?";

		try {
			connection = DatabaseHandler.getConnection();

			// Using try-with-resources for PreparedStatement
			try (PreparedStatement checkStmt = connection.prepareStatement(checkUserQuery)) {
				checkStmt.setString(1, email);

				try (ResultSet resultSet = checkStmt.executeQuery()) {
					if (resultSet.next()) {
						int count = resultSet.getInt(1);
						isEmailAvailable = count == 0;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Handle database connection errors
		}

		return isEmailAvailable;
	}
}
