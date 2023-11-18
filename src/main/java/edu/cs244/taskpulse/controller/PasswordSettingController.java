package edu.cs244.taskpulse.controller;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import edu.cs244.taskpulse.loader.ProfileSettingsLoader;
import edu.cs244.taskpulse.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.cs244.taskpulse.utils.DatabaseHandler;
import edu.cs244.taskpulse.utils.Hasher;


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
    private Label ProfileSettingsUsernameLabel;

    @FXML
    private TextField currentPasswordTextField;

    @FXML
    private TextField newPasswordTextField;
    
    @FXML
    private Label passwordChangeErrorLabel;
    
    @FXML
    private TextField confirmPasswordTextField;
   


    @FXML
    void handleEditPhoto(ActionEvent event) {

    }

    @FXML
    void handleSaveChanges(ActionEvent event) {

    }
    
  
    
    @FXML
    private void savePasswordChanges() {
//    	get current and new password inputs 
    	String currentPassword = currentPasswordTextField.getText();
    	String newPassword = newPasswordTextField.getText();
    	String username = ProfileSettingsUsernameLabel.getText();
    	String confirmPassword = confirmPasswordTextField.getText();
    	
    	
    	if (newPassword.equals(confirmPassword)) {
//    		add code to save the changes to the database here
    		boolean isPasswordChanged = updateUserPassword(username, currentPassword, newPassword);
    		
    		if (isPasswordChanged) {
    			passwordChangeErrorLabel.setText("Password updated successfully!");
        		passwordChangeErrorLabel.setVisible(true);
        		passwordChangeErrorLabel.setTextFill(Color.GREEN);
        		currentPasswordTextField.clear();
        		newPasswordTextField.clear();
    		} else {
    			passwordChangeErrorLabel.setText("Failed to update password!");
        		passwordChangeErrorLabel.setVisible(true);
    		}
    		
    		
    	} else {
    		passwordChangeErrorLabel.setText("New password and confirm password don't match!");
    		passwordChangeErrorLabel.setVisible(true);
    	}
    	
//    	sets the user name label based on the input from unsernameField
    	ProfileSettingsUsernameLabel.setText(username);
    	
//    	prints out the inputs
    	System.out.println(currentPassword);
    	System.out.println(newPassword);
    	
    }
    
    
//    function to update Password to the database 
    private boolean updateUserPassword(String username, String currentPassword, String newPassword) {
    	User user = User.login2(username, currentPassword);
    	if (user != null) {
    		user.setPassword(newPassword);
//    		return user.save();
    	} else {
    		return false;
    	}
		return false;
        
    
    }
    
    
    @FXML
    void actionChangePassword(MouseEvent event) {
//    	set the current state  
    	Stage currentStage = (Stage) ProfileSettingPage.getScene().getWindow();
    	
//    	create a new instance of PasswordSettingsLoader 
    	PasswordSettingLoader passwordLoader = new PasswordSettingLoader(); 
    	
//    	start the password settings screen 
    	passwordLoader.start(currentStage);
    	
    	System.out.println("The password Icon was clicked!!");
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
    	System.out.println("The profile Icon was clicked!!");
    }
    

}
