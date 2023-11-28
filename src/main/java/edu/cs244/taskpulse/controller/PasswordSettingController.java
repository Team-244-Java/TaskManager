package edu.cs244.taskpulse.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
    	Integer passwordLength = currentPasswordField.length();
    	
    	
    	
//    	TODO: implement a way to show error if password is invalid
    	if (currentPasswordField.isEmpty()) {
    		errorLabel.setText("Current Password field is empty");
    		return;
    	}
    	
    	if (passwordLength < 8) {
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
    	
//    	prints out the inputs
//    	System.out.println(currentPasswordField);
//    	System.out.println(newPasswordField);
    	
    	
    	
//    	TODO: add code to save the changes to the database here after all checks passed
//    	Get user data
    	int userId = UserSession.getCurrentUser().getUserId();

    	
//    	TODO: add code to save the changes to the database here
    	Connection connection = null;
    	try {
    		String sql = "UPDATE users "
    				+ "SET password = ? WHERE id = ?";
    			
    		connection = DatabaseHandler.getConnection();
    		PreparedStatement pStmt = connection.prepareStatement(sql);
    		
    		// Set Parameters
			pStmt.setString(1, newPasswordField);
			pStmt.setInt(2, userId);
    		
    		//execute update
			pStmt.executeUpdate();
			connection.commit();
    	}catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	
//    	add code to show password change was successful 
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

}
