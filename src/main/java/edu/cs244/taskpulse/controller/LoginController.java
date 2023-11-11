package edu.cs244.taskpulse.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import edu.cs244.taskpulse.loader.DashboardLoader;
import edu.cs244.taskpulse.loader.RegisterLoader;
import edu.cs244.taskpulse.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private FontAwesomeIcon closeBtn;

	@FXML
	private Pane content_area;

	@FXML
	private Label forgetPassword;

	@FXML
	private Button loginBtn;

	@FXML
	private Label loginErrorAnnouncement;

	@FXML
	private TextField password;

	@FXML
	private Label passwordLabel;

	@FXML
	private ImageView profileIcon;

	@FXML
	private Button signUpBtn;

	@FXML
	private TextField username;

	@FXML
	private Label usernameLabel;

	@FXML
	private Label welcomeLabel;

	@FXML
	void actionForgetPassword(MouseEvent event) {

	}

	@FXML
	void actionLoginBtn() {
		String userName = username.getText();
		String passWord = password.getText();
		boolean loginSuccessful = User.login(userName, passWord);
		if (userName.isEmpty()) {
			loginErrorAnnouncement.setText("Please enter Username");
		} else if (passWord.isEmpty()) {
			loginErrorAnnouncement.setText("Please enter Password");
		} else {
			if (loginSuccessful) {
				// Successful login
				loginErrorAnnouncement.setText(""); // Clear any previous error messages
				Stage currentStage = (Stage) loginBtn.getScene().getWindow();
				DashboardLoader dashboardLoader = new DashboardLoader();
				dashboardLoader.start(currentStage);
			} else {
				loginErrorAnnouncement.setText("Incorrect Username or Password");
			}
		}
	}

	@FXML
	void actionSignUpBtn(ActionEvent event) {
		Stage currentStage = (Stage) loginBtn.getScene().getWindow();

		// Create a new instance of RegisterLoader
		RegisterLoader registerLoader = new RegisterLoader();

		// Start the registration screen
		registerLoader.start(currentStage);
	}

	@FXML
	void actioncloseBtn() {

	}

	@FXML
	void setOnMousePressed() {

	}

	@FXML
	void setOnMouseDragged() {

	}
	
	@FXML
	private void handleKeyPress(KeyEvent event) {
	    if (event.getCode() == KeyCode.ENTER) {
	        // If Enter key is pressed, perform the same action as clicking the login button
	    	actionLoginBtn();
	    }
	}

}
