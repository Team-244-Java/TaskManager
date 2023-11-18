package edu.cs244.taskpulse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
//import javafx.scene.web.HTMLEditor;

public class TaskCreationController {

    @FXML
    private AnchorPane TaskAssignWindowAnchorPane;

    @FXML
    private ComboBox<?> TaskCreationAssignedToComboBox;

    @FXML
    private Button TaskCreationDeleteButton;

    @FXML
    private Label TaskCreationLabelTwo;

    @FXML
    private Button TaskCreationWindowCancelTaskButton;

    @FXML
    private Button TaskCreationWindowCreateTaskButton;

    @FXML
    private DatePicker TaskCreationWindowDatePicker;

    //@FXML
   // private HTMLEditor TaskCreationWindowHTMLEditor;

    @FXML
    private Text TaskCreationWindowLabelOne;

    @FXML
    private Text TaskCreationWindowLabelTwo;

    @FXML
    private TextField TaskCreationWindowTaskNameTextField;

    @FXML
    void CancelTaskAction(ActionEvent event) {

    }

    @FXML
    void CreateTaskAction(ActionEvent event) {

    }

    @FXML
    void DeleteTaskAction(ActionEvent event) {

    }

}
