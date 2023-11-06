package edu.cs244.taskpulse.controller;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class ProfileSettingController {

	 	@FXML
	    private TextField nameTextField; // Assuming you have TextField components in your FXML for first name, last name, etc.

	    @FXML
	    private TextField usernameTextField;

	    @FXML
	    private TextField birthdateTextField;
	    
	    @FXML
	    private TextField phoneTextField;
	    
	    @FXML
	    private TextField emailTextField;
	    
	    @FXML
	    private Button ProfileSettingsSaveChangesBtn;
	    
	    @FXML
	    private Button ProfileSettingsEditProfileButton;
	    
	    @FXML
	    private ImageView ProfileSettingsMainProfileAvatar;
	    
	    @FXML
	    private Label ProfileSettingsUsernameLabel;
	    
	    
	    @FXML
	    private void handleSaveChanges() {
	    	String nameField = nameTextField.getText();
	    	String usernameField = usernameTextField.getText();
	    	String birthdateField = birthdateTextField.getText();
	    	String phonenumberField = phoneTextField.getText();
	    	
//	    	sets the user name label based on the input from unsernameField
	    	ProfileSettingsUsernameLabel.setText(usernameField);
	    	
//	    	prints out the inputs
	    	System.out.println(nameField);
	    	System.out.println(usernameField);
	    	System.out.println(birthdateField);
	    	System.out.println(phonenumberField);
	    	
//	    	add code to save the changes to the database here
	    }
	    
	    
	    @FXML
	    private void handleEditPhoto() {
	    	FileChooser fileChooser = new FileChooser();
	    	fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.gif"));
	    	
//	    	show the file dialog
	    	Stage stage = (Stage) ProfileSettingsMainProfileAvatar.getScene().getWindow();
	        File selectedFile = fileChooser.showOpenDialog(stage);
	    	
	        if (selectedFile != null) {
	        	Image image = new Image(selectedFile.toURI().toString());
	        	ProfileSettingsMainProfileAvatar.setImage(image);
	        	ProfileSettingsMainProfileAvatar.setFitWidth(200); // Set width
	        	ProfileSettingsMainProfileAvatar.setFitHeight(200); // Set height

	        }
	    	
//	    	add code to save the image to the database here
	    }

}
