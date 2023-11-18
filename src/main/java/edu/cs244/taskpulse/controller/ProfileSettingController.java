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
import edu.cs244.taskpulse.loader.PasswordSettingLoader;
import edu.cs244.taskpulse.models.User;
import edu.cs244.taskpulse.utils.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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
	    private HBox ProfileSettingsPasswordIcon;
	    
	    @FXML
	    private BorderPane ProfileSettingsBorderPane;
	    
	    private User userId;
	    
	    @FXML
	    private Label errorLabel;
	   
	    
	    
	    @FXML
	    private void handleSaveChanges() {
	    	String newName = nameTextField.getText();
	    	String newUsername = usernameTextField.getText();
	    	String newBirthday = birthdateTextField.getText();
	    	String newPhoneNumber = phoneTextField.getText();
	    	
//	    	used isUsernameAvailable method from User Class to check if the username is available
	    	boolean isUsernameAvailable = User.isUsernameAvailable(newUsername);
	    	
//	    	if it's available, then update the information on the database
	    	if(isUsernameAvailable) { 
	    		boolean isUpdateSuccessful = updateUserProfile(newUsername, newName, newBirthday, newPhoneNumber);
//	    		
//	    		if update is successful, display username next to the avatar
	    		if (isUpdateSuccessful) {
	    			ProfileSettingsUsernameLabel.setText(newUsername);
	    			
//	    		if there was an error with updating the database, display error message
	    		} else {
	    			errorLabel.setText("Failed to save changes to the database!");
	    			errorLabel.setVisible(true);
	    		}
	    		
//	    	if user name is not available then display the error 
	    	} else {
	    		// Show error message if the user name is taken
	            errorLabel.setText("Username is already taken!");
	            errorLabel.setVisible(true);
	    	}
	    	
	    
//	    	prints out the inputs
	    	System.out.println(newName);
	    	System.out.println(newUsername);
	    	System.out.println(newBirthday);
	    	System.out.println(newPhoneNumber);
	    	

	    }
	    
//	    a function that updates the profile changes to the database 
	    private boolean updateUserProfile(String newUsername, String newName, String newBirthday, String newPhoneNumber) {
	    	Connection connection = null;
	    	PreparedStatement updateStmt = null; 
	    	
	    	try {
	    		connection = DatabaseHandler.getConnection();
	    		
//	    		implement your update query here
	    		String updateQuery = "UPDATE users SET username = ?, name = ?, birthday = ?, phone_numer = ? WHERE user_id = ? ";
	    		updateStmt = connection.prepareStatement(updateQuery);
	    		updateStmt.setString(1, newUsername);
	    		updateStmt.setString(2, newName);
	    		updateStmt.setString(3, newBirthday);
	    		updateStmt.setString(4, newPhoneNumber);
	    		
	    		
	    		int rowsAffected = updateStmt.executeUpdate();
	    		return rowsAffected > 0;
	    		
	    	} catch (SQLException ex) {
	    		ex.printStackTrace();
	    		return false;
	    	} finally {
	    		// Close resources
				try {
					if (updateStmt != null)
						updateStmt.close();
					if (connection != null)
						connection.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
	    	}
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
	    	
//	    	TODO: add code to save the image to the database here
	        
//	    
	     
	    }
	    @FXML
	    void actionChangePassword(MouseEvent event) {
//	    	set the current state  
	    	Stage currentStage = (Stage) ProfileSettingsPasswordIcon.getScene().getWindow();
	    	
//	    	create a new instance of PasswordSettingsLoader 
	    	PasswordSettingLoader passwordLoader = new PasswordSettingLoader(); 
	    	
//	    	start the password settings screen 
	    	passwordLoader.start(currentStage);
	    	
	    	System.out.println("The password Icon was clicked!!");
	    }

}
