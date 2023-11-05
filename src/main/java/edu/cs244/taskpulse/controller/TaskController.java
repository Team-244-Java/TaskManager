package edu.cs244.taskpulse.controller;

import edu.cs244.taskpulse.models.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class TaskController {
    @FXML
    private ImageView task;

    @FXML
    private Label taskTitle;
    
    private Task taskItem;
    
    public void setData(Task taskItem) {
    	this.taskItem=taskItem;
    	taskTitle.setText(taskItem.getTitle());
    }
}
