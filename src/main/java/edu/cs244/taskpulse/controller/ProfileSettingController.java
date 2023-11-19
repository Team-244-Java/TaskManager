package edu.cs244.taskpulse.controller;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.cs244.taskpulse.utils.DatabaseHandler;
import edu.cs244.taskpulse.utils.UserSession;
import edu.cs244.taskpulse.controller.RegisterController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import edu.cs244.taskpulse.loader.PasswordSettingLoader;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;



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
	    
	    @FXML
	    private Label errorLabel;
	    

	    
	    @FXML
	    private void handleSaveChanges() {
	    	//String nameField = nameTextField.getText();
	    	String usernameField = usernameTextField.getText();
	    	
//	    	TODO: implement a way to show error if user name is taken
//	    	used the method from registration, but i had to change the method to public and static - Note from Jen
	    	boolean isUsernameAvailable = RegisterController.checkUsernameAvailability(usernameField);
	    	
//	    	if(isUsernameAvailable) {
////	    		sets the user name label based on the input from unsernameField
//		    	ProfileSettingsUsernameLabel.setText(usernameField);
//	    	} else {
//	    		errorLabel.setText("Username is already taken!");
//	    		errorLabel.setVisible(true);
//	    	}
	    	
	    	//String birthdateField = birthdateTextField.getText();
	    	String phonenumberField = phoneTextField.getText();
	    	String emailField = emailTextField.getText();
	    	
	    		    	
//	    	Get user data
	    	int userId = UserSession.getCurrentUser().getUserId();

	    	
//	    	TODO: add code to save the changes to the database here
	    	Connection connection = null;
	    	try {
	    		String sql = "UPDATE users "
	    				+ "SET username = ?, phone = ?, email = ? WHERE id = ?";
	    			
	    		connection = DatabaseHandler.getConnection();
	    		PreparedStatement pStmt = connection.prepareStatement(sql);
	    		
	    		// Set Parameters
				pStmt.setString(1, usernameField);
				pStmt.setString(2, phonenumberField);
				pStmt.setString(3, emailField);
				pStmt.setInt(4, userId);
	    		
	    		//execute update
				pStmt.executeUpdate();
				connection.commit();
	    	}catch (Exception ex) {
	    		ex.printStackTrace();
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
	        //Get user info
	    	int userId = UserSession.getCurrentUser().getUserId();

	    	
	    	//Get the URl and save to database
	        Connection connection = null;
	    	try {
	    		String sql = "UPDATE users "
	    				+ "SET profile_picture = ? WHERE id = ?";
	    			
	    		connection = DatabaseHandler.getConnection();
	    		PreparedStatement pStmt = connection.prepareStatement(sql);
	    		
	    		// Set Parameters
	    		String imageUrl = (selectedFile.toURI().toString());
	    		pStmt.setString(1, imageUrl);
				pStmt.setInt(2, userId);
	    		
	    		//execute update
				pStmt.executeUpdate();
				connection.commit();
	    	}catch (Exception ex) {
	    		ex.printStackTrace();
	    	}
	    }
    
	    
	    @FXML
	    void actionChangePassword(MouseEvent event) {
//	    	set the current state  
	    	Stage currentStage = (Stage) ProfileSettingsPasswordIcon.getScene().getWindow();
	    	
//	    	create a new instance of PasswordSettingsLoader 
	    	PasswordSettingLoader passwordLoader = new PasswordSettingLoader(); 
	    	
//	    	start the password settings screen 
	    	passwordLoader.open(currentStage);
	    	
	    }
	    
	    static public String getPicture() {
	    	//Get user info
	    	int userId = UserSession.getCurrentUser().getUserId();
	    	
	    	//Check if user have profile picture
	    	String url = "";
	        Connection connection = null;
	    	try {
	    		String sql = "SELECT profile_picture FROM users WHERE id = ?";
	    			
	    		connection = DatabaseHandler.getConnection();
	    		PreparedStatement pStmt = connection.prepareStatement(sql);
	    		
	    		// Set Parameters
				pStmt.setInt(1, userId);
				
				try (ResultSet rs = pStmt.executeQuery()) {
					if (rs.next()) {
						url = rs.getString("profile_picture");
						
			        	if (url == null) {
			        		url = "images/PageIcon.png";
			        	}
						return url;
					} 
				}
	    		

	    	}catch (Exception ex) {
	    		ex.printStackTrace();
	    	}
			return url;
	    }
	    
	    public void updatePicture(String url) {
        	Image image = new Image(url);
        	ProfileSettingsMainProfileAvatar.setImage(image);
        	ProfileSettingsMainProfileAvatar.setFitWidth(200); // Set width
        	ProfileSettingsMainProfileAvatar.setFitHeight(200);
	    }
}
