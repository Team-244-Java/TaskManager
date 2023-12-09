package edu.cs244.taskpulse.controller;

import edu.cs244.taskpulse.loader.DashboardLoader;
import edu.cs244.taskpulse.utils.VerificationAndForgotPassword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class VerificationController {
	
	@FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane content_area;

    @FXML
    private Text errorLabel;

    @FXML
    private Button sendCodeBtn;

    @FXML
    private TextField userCode;

    @FXML
    private TextField userEmail;

    @FXML
    private Button verifyBtn;


    @FXML
	void actionVerifyBtn(ActionEvent event) {
    	String email = userEmail.getText();
    	String code = userCode.getText();
		boolean codeSucessful = VerificationAndForgotPassword.checkCodeAndUpdateStatus(email, code);
		if (codeSucessful) {
			errorLabel.setText(""); // Clear any previous error messages
			Stage currentStage = (Stage) verifyBtn.getScene().getWindow();
			DashboardLoader dashboardLoader = new DashboardLoader();
			dashboardLoader.start(currentStage);
		} else {
			errorLabel.setText("Code is incorrect.");
		}
	}

    	@FXML
       void actionCodeBtn() throws Exception {
    	String email = userEmail.getText();
		VerificationAndForgotPassword.sendMail(email, "Verification", "Your verification code is: ");
		errorLabel.setText("A code been sent");
		sendCodeBtn.setDisable(true);
		
    }
    	
        @FXML
        void setOnMouseDragged(MouseEvent event) {

        }

        @FXML
        void setOnMousePressed(MouseEvent event) {

        }

}