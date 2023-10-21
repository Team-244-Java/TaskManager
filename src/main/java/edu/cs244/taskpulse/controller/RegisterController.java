package edu.cs244.taskpulse.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs244.taskpulse.loader.LandingLoader;
import edu.cs244.taskpulse.loader.LoginLoader;
import edu.cs244.taskpulse.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private Label registerErrorAnnouncement;

	@FXML
	private Button RegisterButtonFinalize;

	@FXML
	private TextField userEmail;

	@FXML
	private TextField zipcode;

	@FXML
	private Label EmailLabel;

	@FXML
	private Label PasswordLabel;

	@FXML
	private Label UsernameLabel;

	@FXML
	private Label userZipCodeLabel;

	@FXML
	private Button LoginButton;

	// Define a regular expression pattern for a valid email address
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

	// You can add your controller logic and methods here

	// For example, you can create a method for handling the registration process

	public void handleRegistration() {
		String userName = username.getText();
		String passWord = password.getText();
		String email = userEmail.getText();

		Matcher matcher = EMAIL_PATTERN.matcher(email);

		if (!matcher.matches()) {
			// The email format is valid, proceed with registration or other actions
			userEmail.setStyle("-fx-border-color: red;");
			registerErrorAnnouncement.setText("Invalid email format");

		} else {
			// The email format is not valid, show an error message or take appropriate
			// action
			userEmail.setStyle(""); // Reset style

			User newUser = new User(userName, passWord, email);

			boolean registrationSuccess = newUser.register();

			if (registrationSuccess) {

				registerErrorAnnouncement.setText(""); // Clear any previous error messages
				LandingLoader landingLoader = new LandingLoader();
				landingLoader.start((Stage) RegisterButtonFinalize.getScene().getWindow());
				// Registration was successful, you can navigate to another page
				// or display a success message
			} else {
				// Registration failed, you can show an error message
				registerErrorAnnouncement.setText("Registration failed. Please try again.");
			}
			// Implement the registration logic here
			// You can access the UI components like username, password, etc., here
		}
	}

	public void handleLoginButton() {
		Stage currentStage = (Stage) RegisterButtonFinalize.getScene().getWindow();

		// Create a new instance of RegisterLoader
		LoginLoader loginLoader = new LoginLoader();

		// Start the registration screen
		loginLoader.start(currentStage);
	}
}
