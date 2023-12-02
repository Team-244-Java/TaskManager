package edu.cs244.taskpulse.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.cs244.taskpulse.loader.PasswordSettingLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import edu.cs244.taskpulse.loader.ProfileSettingsLoader;
import edu.cs244.taskpulse.utils.DatabaseHandler;
import edu.cs244.taskpulse.utils.HasherAndEncrypt;
import edu.cs244.taskpulse.utils.UserSession;
import javafx.scene.control.Alert;

public class PasswordSettingController {

    @FXML
    private ImageView PasswordSettingMainProfileAvatar;

    @FXML
    private HBox PasswordSettingPage;

    @FXML
    private Button PasswordSettingSaveChangesBtn;

    @FXML
    private BorderPane ProfileSettingMAINPane;

    @FXML
    private HBox ProfileSettingPage;

    @FXML
    private Button ProfileSettingsEditProfileButton;


    @FXML
    private TextField currentPasswordTextField;

    @FXML
    private TextField newPasswordTextField;
    
    
    @FXML
    private TextField confirmPasswordTextField;
    

   @FXML
   private Label errorLabel;


    @FXML
    void handleEditPhoto(ActionEvent event) {

    }

    @FXML
    void handleSaveChanges(ActionEvent event) {

    }
    
    
  
    @FXML
    void actionChangeProfile(MouseEvent event) {
//    	set the current state
    	Stage currentStage = (Stage) ProfileSettingPage.getScene().getWindow();
    	
//    	create a new instance of PasswordSettingsLoader 
    	ProfileSettingsLoader profileLoader = new ProfileSettingsLoader(); 
    	
    	
//    	start the registration screen 
    	profileLoader.start(currentStage);
//    	print statement to check event listener works

    }
    
    
    
    
    
    @FXML
    private void savePasswordChanges() {
//    	get current and new password inputs 
    	String currentPasswordField = currentPasswordTextField.getText();
    	String newPasswordField = newPasswordTextField.getText();
    	String confirmPasswordField = confirmPasswordTextField.getText();
    	Integer passwordLength = newPasswordField.length();
    	Boolean checkCurrentPasswordStatus = checkCurrentPassword(currentPasswordField);
    	
    	
//    	TODO: implement a way to show error if password is invalid
    	if (currentPasswordField.isEmpty()) {
    		errorLabel.setText("Current Password field is empty");
    		return;
    	}
    	
    	if (checkCurrentPasswordStatus == false) {
    		errorLabel.setText("Current Password does not match");
    		return;
    	}
    	
    	if (passwordLength <= 7) {
    		errorLabel.setText("Password must be at least 8 characters long");
    		return;
    	}
    	
    	if (newPasswordField.isEmpty()) {
    		errorLabel.setText("New Password field is empty");
    		return;
    	}
    	
    	if (confirmPasswordField.isEmpty()) {
    		errorLabel.setText("Confirm Password field is empty");
    		return;
    	}
    	
    	if (!newPasswordField.equals(confirmPasswordField)) {
    		errorLabel.setText("Passwords are not matching");
    		return;
    	}
    	

    	
//    	TODO: add code to save the changes to the database here
    	updatePassword(newPasswordField);
    	showPasswordChangeSuccessAlert();
    }
    
    
    private void showPasswordChangeSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Password changed successfully!");

        alert.showAndWait();
    }

    
    @FXML
    void actionChangePassword(MouseEvent event) {
//    	set the current state  
    	Stage currentStage = (Stage) ProfileSettingPage.getScene().getWindow();
    	
//    	create a new instance of PasswordSettingsLoader 
    	PasswordSettingLoader passwordLoader = new PasswordSettingLoader(); 
    	
//    	start the password settings screen 
    	passwordLoader.open(currentStage);
    	

    }
    
    public void updatePassword(String password) {
    	int userId = UserSession.getCurrentUser().getUserId();
    	
    	Connection connection = null;
		String hashedPassword = HasherAndEncrypt.getSHA(password);
		try {
			connection = DatabaseHandler.getConnection();
			String updatePassword = "UPDATE users SET hashed_password = ? WHERE id = ?";
			PreparedStatement pStmt = connection.prepareStatement(updatePassword);
			pStmt.setString(1, hashedPassword);
			pStmt.setInt(2, userId);
			pStmt.executeUpdate();
			connection.commit();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
    }
    
    public boolean checkCurrentPassword(String currentPassword) {
    	//get user data
    	int userId = UserSession.getCurrentUser().getUserId();
    	
		Connection connection = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		String hashedPasswordCurrentPassword = HasherAndEncrypt.getSHA(currentPassword);
		try {
			connection = DatabaseHandler.getConnection();
			String checkPasswordCurrentPassword = "SELECT hashed_password FROM users WHERE id = ?";
			pStmt = connection.prepareStatement(checkPasswordCurrentPassword);
			pStmt.setInt(1, userId);
			
			//checking if current password matches  
			rs = pStmt.executeQuery();
			if (rs.next()) {
				String currentPasswordFromDatabase = rs.getString("hashed_password");

				if (hashedPasswordCurrentPassword.equals(currentPasswordFromDatabase)) {
					return true;
				}
			}

			return false; // incorrect current password 


		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
    }

}
