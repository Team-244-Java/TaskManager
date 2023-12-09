package edu.cs244.taskpulse.controller;

import java.io.File;
import java.net.URI;
import java.net.MalformedURLException;
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
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;



public class ProfileSettingController {

    @FXML
    private VBox ProfileSettingVBoxIcons;

    @FXML
    private BorderPane ProfileSettingsBorderPaneMAIN;

    @FXML
    private Button ProfileSettingsEditProfileButton;

    @FXML
    private ImageView ProfileSettingsMainProfileAvatar;

    @FXML
    private Button ProfileSettingsSaveChangesBtn;

    @FXML
    private Label ProfileSettingsUsernameLabel;

    @FXML
    private VBox ProfileSettingsVBoxLEFT;

    @FXML
    private VBox ProfileSettingsVBoxLeftSidebarEditProfile;

    @FXML
    private VBox ProfileSettingsVBoxMAIN;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField usernameTextField;
	    
    private DashboardController dashboardController;

	public void setDashboardController(DashboardController dashboardController) {
		this.dashboardController = dashboardController;
	}
	
	    @FXML
	    private void handleSaveChanges() {
	    	//get info from text fields and user data
	    	String first_name = firstNameTextField.getText();
	    	String last_name = lastNameTextField.getText();
	    	String username = usernameTextField.getText();
	    	String phonenumber= phoneTextField.getText();
	    	String email = emailTextField.getText();
	    	int userId = UserSession.getCurrentUser().getUserId();
	    	
//	    	used the method from registration, but i had to change the method to public and static - Note from Jen
	    	boolean isUsernameAvailable = RegisterController.checkUsernameAvailability(username);
	    	
	    	
	    	if (first_name.isEmpty()) {
	    		errorLabel.setText("First name field is empty");
				return;
	    	}
	    	
	    	if (last_name.isEmpty()) {
	    		errorLabel.setText("Last name field is empty");
				return;
	    	}
	    	
	    	if (username.isEmpty()) {
	    		errorLabel.setText("Username field is empty");
				return;
	    	}
	    	
	    	//check if username is available
	    	if(isUsernameAvailable) {
//	    		sets the user name label based on the input from unsernameField
	    		ProfileSettingsUsernameLabel.setText(username);
	    	} else {
	    		errorLabel.setText("Username is already taken!");
	    		errorLabel.setVisible(true);
	    		return;
	    	}
	    	
	    	if (phonenumber.isEmpty()) {
	    		errorLabel.setText("Phone number field is empty");
				return;
	    	}
	    	
	    	if (email.isEmpty()) {
	    		errorLabel.setText("Email field is empty");
				return;
	    	}
	    	
	    	// Save to database
	    	updateProfile(first_name, last_name, username, phonenumber, email, userId);
	    	errorLabel.setText("You updated your Profile!");
	    }
	    
	    
	    @FXML
	    private void handleEditPhoto() throws MalformedURLException {
	    	FileChooser fileChooser = new FileChooser();
	    	fileChooser.setTitle("Select Image File");
	    	fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.gif"));
//	    	show the file dialog
	        File selectedFile = fileChooser.showOpenDialog(ProfileSettingsMainProfileAvatar.getScene().getWindow());

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
	    	refresh();
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
	    
	    public void updateProfile(String first_name, String last_name, String username, String phone, String email, int id) {
	    	Connection connection = null;
	    	try {
	    		String sql = "UPDATE users "
	    				+ "SET first_name = ?, last_name = ?, username = ?, phone = ?, email = ? WHERE id = ?";
	    			
	    		connection = DatabaseHandler.getConnection();
	    		PreparedStatement pStmt = connection.prepareStatement(sql);
	    		
	    		// Set Parameters
	    		pStmt.setString(1, first_name);
	    		pStmt.setString(2, last_name);
				pStmt.setString(3, username);
				pStmt.setString(4, phone);
				pStmt.setString(5, email);
				pStmt.setInt(6, id);
	    		
	    		//execute update
				pStmt.executeUpdate();
				connection.commit();
	    	}catch (Exception ex) {
	    		ex.printStackTrace();
	    	}
	    }
	    
	    public void updateUsernameLabel() {
	    	ProfileSettingsUsernameLabel.setText(UserSession.getCurrentUser().getUsername());
	    }
	    
	    private void refresh() {
	    	dashboardController.updateAvatar(getPicture());
		}
}
