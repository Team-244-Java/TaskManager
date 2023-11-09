package edu.cs244.taskpulse.controller;

import edu.cs244.taskpulse.utils.Verification;
import edu.cs244.taskpulse.loader.VerificationLoader;
import edu.cs244.taskpulse.loader.LandingLoader;
import edu.cs244.taskpulse.loader.RegisterLoader;
import edu.cs244.taskpulse.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private Button loginButton;

	@FXML
	private Label loginErrorAnnouncement;

	@FXML
	private Button goToRegistrationForm;

	@FXML
	public void handleLoginButtonAction() {
		String userName = username.getText();
		String passWord = password.getText();
		
		// Call the login method from the User class
		boolean loginSuccessful = User.login(userName, passWord);
		boolean active = Verification.checkStatus(userName);
		if (loginSuccessful) {
			// Successful login
			loginErrorAnnouncement.setText(""); // Clear any previous error messages
			
			//check if active
			if (active) {
				//send to main page
				Stage currentStage = (Stage) loginButton.getScene().getWindow();
				LandingLoader landingLoader = new LandingLoader();
				landingLoader.start(currentStage);
			}else {
				//send to verify page
				Stage currentStage = (Stage) loginButton.getScene().getWindow();
				VerificationLoader VerificationLoader = new VerificationLoader();
				VerificationLoader.start(currentStage);
			}
		} else {
			loginErrorAnnouncement.setText("Username and/or password are incorrect.");
		}
	}

	// Event handler for the Register button
	@FXML
	private void handleRegisterButtonAction(ActionEvent event) {
		Stage currentStage = (Stage) loginButton.getScene().getWindow();

		// Create a new instance of RegisterLoader
		RegisterLoader registerLoader = new RegisterLoader();

		// Start the registration screen
		registerLoader.start(currentStage);
	}
	
	@FXML
	private void handleKeyPress(KeyEvent event) {
	    if (event.getCode() == KeyCode.ENTER) {
	        // If Enter key is pressed, perform the same action as clicking the login button
	    	handleLoginButtonAction();
	    }
	}
}