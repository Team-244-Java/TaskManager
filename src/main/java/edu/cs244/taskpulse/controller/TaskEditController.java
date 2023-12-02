package edu.cs244.taskpulse.controller;

import edu.cs244.taskpulse.models.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TaskEditController implements Initializable{

    @FXML
    private AnchorPane TaskAssignWindowAnchorPane;

    @FXML
    private ComboBox<?> TaskEditAssignedToComboBox;

    @FXML
    private Button TaskEditDeleteButton;

    @FXML
    private Label TaskEditLabelTwo;

    @FXML
    private ComboBox<String> TaskEditStatusBox;

    @FXML
    private Button TaskEditWindowCancelTaskButton;

    @FXML
    private DatePicker TaskEditWindowDatePicker;

    @FXML
    private HTMLEditor TaskEditWindowHTMLEditor;

    @FXML
    private Text TaskEditWindowLabelOne;

    @FXML
    private Text TaskEditWindowLabelTwo;

    @FXML
    private Button TaskEditWindowSaveTaskButton;

    @FXML
    private TextField TaskEditWindowTaskNameTextField;

    @FXML
    private Text TaskStatusLabel;

	private Task taskItem;
    
	private String[] status = {"To Do", "In Progress", "Done"};
	
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	TaskEditStatusBox.getItems().addAll(status);
    }
	
    @FXML
    void CancelTaskAction(ActionEvent event) {
		Stage currentStage = (Stage) TaskEditWindowCancelTaskButton.getScene().getWindow();
		currentStage.close();
    }

    @FXML
    void SaveTaskAction(ActionEvent event) {
    	//get task data
		String title = TaskEditWindowTaskNameTextField.getText();
		String description =  TaskEditWindowHTMLEditor.getHtmlText();
		String strippedText = description.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", "");
		//convert date to string
		LocalDate dueDate = TaskEditWindowDatePicker.getValue();
		String sDate = dueDate.toString();
		String status = TaskEditStatusBox.getValue();
		int taskId = this.taskItem.getTaskId();
		
		Task.updateTask(title, strippedText, sDate, status, taskId);
    	Stage currentStage = (Stage) TaskEditWindowCancelTaskButton.getScene().getWindow();
		currentStage.close();
    }

    @FXML
    void DeleteTaskAction(ActionEvent event) {
    	int taskId = this.taskItem.getTaskId();
    	Task.deleteTask(taskId);
       	Stage currentStage = (Stage) TaskEditDeleteButton.getScene().getWindow();
    	currentStage.close();
    }

    public void setTaskItem(Task taskItem) {
    	this.taskItem = taskItem;
    	
    	this.TaskEditWindowTaskNameTextField.setText(this.taskItem.getTitle());
    	LocalDate dueDate = LocalDate.parse(this.taskItem.getDueDate());
    	this.TaskEditWindowDatePicker.setValue(dueDate);
    	this.TaskEditWindowHTMLEditor.setHtmlText(this.taskItem.getDescription());
    	this.TaskStatusLabel.setText(this.taskItem.getStatus());
    	
    }
    
    
	
}
