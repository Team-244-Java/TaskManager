package edu.cs244.taskpulse.controller;

import edu.cs244.taskpulse.models.Task;
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
import java.time.LocalDate;

public class TaskEditController {

    @FXML
    private AnchorPane TaskAssignWindowAnchorPane;

    @FXML
    private ComboBox<?> TaskEditAssignedToComboBox;

    @FXML
    private Button TaskEditDeleteButton;

    @FXML
    private static Label TaskEditLabelTwo;

    @FXML
    private Button TaskEditWindowCancelTaskButton;

    @FXML
    private Button TaskEditWindowCreateTaskButton;

    @FXML
    private DatePicker TaskEditWindowDatePicker;

    @FXML
   private HTMLEditor TaskEditWindowHTMLEditor;

    @FXML
    private Text TaskEditWindowLabelOne;

    @FXML
    private Text TaskEditWindowLabelTwo;

    @FXML
    private TextField TaskEditWindowTaskNameTextField;

	private Task taskItem;
    
    @FXML
    void CancelTaskAction(ActionEvent event) {

    }

    @FXML
    void CreateTaskAction(ActionEvent event) {

    }

    @FXML
    void DeleteTaskAction(ActionEvent event) {

    }

    public void setTaskItem(Task taskItem) {
    	this.taskItem = taskItem;
    	
    	this.TaskEditWindowTaskNameTextField.setText(this.taskItem.getTitle());
    	LocalDate dueDate = LocalDate.parse(this.taskItem.getDueDate());
    	this.TaskEditWindowDatePicker.setValue(dueDate);
    	this.TaskEditWindowHTMLEditor.setHtmlText("<h3 color='blue;'>" + this.taskItem.getDescription() + "</h3>" + "<p>" + "editing task id #:" + this.taskItem.getTaskId());
    	
    }
    
    @FXML
	public static void setInputs() {
		//this.taskItem = taskItem;
		TaskEditLabelTwo.setText("hello");
		//description.setText(taskItem.getDescription());
		//TaskEditWindowDatePicker.setPromptText(taskItem.getDueDate());
	}
    
    
	
}
