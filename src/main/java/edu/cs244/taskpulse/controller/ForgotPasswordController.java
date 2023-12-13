package edu.cs244.taskpulse.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import edu.cs244.taskpulse.loader.LoginLoader;
import edu.cs244.taskpulse.utils.DatabaseHandler;
import edu.cs244.taskpulse.utils.HasherAndEncrypt;
import edu.cs244.taskpulse.utils.VerificationAndForgotPassword;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ForgotPasswordController {

	@FXML
	private Label NewPasswordLabel;

	@FXML
	private Label VerfiyLabel;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Pane content_area;

	@FXML
	private FontAwesomeIcon copyright;

	@FXML
	private Label emailLabel;

	@FXML
	private Text errorLabel;

	@FXML
	private PasswordField newPassword;

	@FXML
	private PasswordField password;

	@FXML
	private Label passwordLabel;

	@FXML
	private Button returnLoginBtn;

	@FXML
	private Button sendCodeBtn;

	@FXML
	private Button updateBtn;

	@FXML
	private TextField userCode;

	@FXML
	private TextField userEmail;

	@FXML
	private Label welcomeLabel;

	// Define a regular expression pattern for a valid email address
	 private static final Pattern EMAIL_PATTERN =
	 Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

	@FXML
	void actionCodeBtn() throws Exception {
		String email = userEmail.getText();
		VerificationAndForgotPassword.sendMail(email, "Forgot Password", "Your code for resting your password is: " );
		
		errorLabel.setText("A code been sent");
		sendCodeBtn.setDisable(true);
	}

	@FXML
	void actionReturnLogin() {
		Stage currentStage = (Stage) updateBtn.getScene().getWindow();

		// Create a new instance of RegisterLoader
		LoginLoader loginLoader = new LoginLoader();

		// Start the registration screen
		loginLoader.start(currentStage);
	}

	@FXML
	void actionUpdateBtn() {
		String email = userEmail.getText();
		String code = userCode.getText();
		String userPassword = password.getText();
		String newUserPassword = newPassword.getText();
		boolean codeSucessful = VerificationAndForgotPassword.checkCodes(email, code);
		// Check if Empty
		if (email.isEmpty()) {
			errorLabel.setText("Email field is empty");
			return;
		}
		Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
		if (!emailMatcher.matches()) {
			errorLabel.setText("Email format is incorrect");
			return;
		}
		if (code.isEmpty()) {
			errorLabel.setText("Your code field is empty");
			return;
		}
		if (userPassword.isEmpty()) {
			errorLabel.setText("Password field is empty");
			return;
		}
		if (newUserPassword.isEmpty()) {
			errorLabel.setText("New Password field is empty");
			return;
		}
	    if (!userPassword.equals(newUserPassword)) {
	    	errorLabel.setText("New Password is not matching");
	        return;
	    }
	    if (userPassword.length() <= 7) {
	    	errorLabel.setText("Password must be at least 8 characters long");
	        return;
	    }
		userEmail.setStyle("");
		
		if (codeSucessful) {
			updateUserPassword(email, userPassword);
			errorLabel.setText("Your have updated your password");
		} else {
			errorLabel.setText("Your code is incorrect");
		}
	}

	@FXML
	void setOnMouseDragged(MouseEvent event) {

	}

	@FXML
	void setOnMousePressed(MouseEvent event) {

	}
	public static void updateUserPassword(String email, String password) {
		Connection connection = null;
		String hashedPassword = HasherAndEncrypt.getSHA(password);
		try {
			connection = DatabaseHandler.getConnection();
			String updatePassword = "UPDATE users SET hashed_password = ? WHERE email = ?";
			PreparedStatement pStmt = connection.prepareStatement(updatePassword);
			pStmt.setString(1, hashedPassword);
			pStmt.setString(2, email);
			pStmt.executeUpdate();
			connection.commit();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}