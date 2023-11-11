package edu.cs244.taskpulse.controller;

import edu.cs244.taskpulse.loader.DashboardLoader;
import edu.cs244.taskpulse.utils.Verification;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class VerificationController {
	
	

    @FXML
    private TextField accountInput;

    @FXML
    private PasswordField codeInput;
    
    
    public Button mailButton;

    @FXML
    private Label warningLabel;
    
    @FXML
    public Button verifiyButton;


    @FXML
	public void handleVerifiyButtonAction() {
	    String email = accountInput.getText();
    	String code = codeInput.getText();
		boolean codeSucessful = Verification.checkCode(email, code);
		if (codeSucessful) {
			warningLabel.setText(""); // Clear any previous error messages
			Stage currentStage = (Stage) verifiyButton.getScene().getWindow();
			DashboardLoader dashboardLoader = new DashboardLoader();
			dashboardLoader.start(currentStage);
		} else {
			warningLabel.setText("Code is incorrect.");
		}
	}

    	@FXML
       void handleMailButtonAction() throws Exception {
    	String email = accountInput.getText();
		Verification.sendMail(email);
    }
    
}