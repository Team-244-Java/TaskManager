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
import javafx.stage.Stage;
import edu.cs244.taskpulse.loader.ProfileSettingsLoader;

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
    	System.out.println("The profile Icon was clicked!!");
    }
    
    
    @FXML
    private void savePasswordChanges() {
//    	get current and new password inputs 
    	String currentPasswordField = currentPasswordTextField.getText();
    	String newPasswordField = newPasswordTextField.getText();
    	String usernameField = ProfileSettingsUsernameLabel.getText();
    	
//    	TODO: implement a way to show error if password is invalid
    	
//    	sets the user name label based on the input from unsernameField
    	ProfileSettingsUsernameLabel.setText(usernameField);
    	
//    	prints out the inputs
    	System.out.println(currentPasswordField);
    	System.out.println(newPasswordField);
    	
//    	TODO: add code to save the changes to the database here
    }
    
    @FXML
    void actionChangePassword(MouseEvent event) {
//    	set the current state  
    	Stage currentStage = (Stage) ProfileSettingsPasswordIcon.getScene().getWindow();
    	
//    	create a new instance of PasswordSettingsLoader 
    	PasswordSettingLoader passwordLoader = new PasswordSettingLoader(); 
    	
//    	start the password settings screen 
    	passwordLoader.start(currentStage);
    	
    	System.out.println("The password Icon was clicked!!");
    }

}
