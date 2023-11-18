package edu.cs244.taskpulse.controller;


import edu.cs244.taskpulse.models.Task;

import java.time.LocalDate;

import edu.cs244.taskpulse.loader.DashboardLoader;

import edu.cs244.taskpulse.utils.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
//import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class TaskCreationController {

    @FXML
    private AnchorPane TaskAssignWindowAnchorPane;

    @FXML
    private ComboBox<?> TaskCreationAssignedToComboBox;

    @FXML
    private Label TaskCreationLabelTwo;

    @FXML
    private Button TaskCreationWindowCancelTaskButton;

    @FXML
    private Button TaskCreationWindowCreateTaskButton;

    @FXML
    private DatePicker TaskCreationWindowDatePicker;

    @FXML
    private HTMLEditor TaskCreationWindowHTMLEditor;

    @FXML
    private Text TaskCreationWindowLabelOne;

    @FXML
    private Text TaskCreationWindowLabelTwo;

    @FXML
    private TextField TaskCreationWindowTaskNameTextField;


    @FXML
    void CreateTaskAction(ActionEvent event) {
    	//get task data and user ID
		String title = TaskCreationWindowTaskNameTextField.getText();
		String description =  TaskCreationWindowHTMLEditor.getHtmlText();
		String strippedText = description.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", "");
		//convert date to string
		LocalDate dueDate = TaskCreationWindowDatePicker.getValue();
		String sDate = dueDate.toString();
		int userId = UserSession.getCurrentUser().getUserId();

		//send to database
		Task.addTask(title, strippedText, sDate, userId);
		
		//load changes and close previous page
		Stage currentStage = (Stage) TaskCreationWindowCreateTaskButton.getScene().getWindow();
		currentStage.close();
		DashboardLoader dashboard = new DashboardLoader();
		dashboard.start(currentStage);
    }

    
    @FXML
    void CancelTaskAction(ActionEvent event) {
		Stage currentStage = (Stage) TaskCreationWindowCancelTaskButton.getScene().getWindow();
		currentStage.close();
    }
    

}
