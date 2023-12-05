package edu.cs244.taskpulse.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import edu.cs244.taskpulse.loader.TaskEditLoader;
import edu.cs244.taskpulse.models.Task;
import edu.cs244.taskpulse.utils.HasherAndEncrypt;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TaskController {

	@FXML
	private Label description;

	@FXML
	private Label dueDate;

	@FXML
	private FontAwesomeIcon editBtn;

	@FXML
	private Label status;

	@FXML
	private ImageView task;

	@FXML
	private Label taskTitle;

	private Task taskItem;

	private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }
    
	public void setData(Task taskItem) {
		this.taskItem = taskItem;
		this.taskTitle.setText(taskItem.getTitle());
		this.description.setText(taskItem.getDescription());
		this.dueDate.setText(taskItem.getDueDate());
		this.status.setText(taskItem.getStatus());

	}

	@FXML
	void onActionEdit() {
		TaskEditLoader taskEditUi = new TaskEditLoader(dashboardController,taskItem);
		taskEditUi.newWindow();

	}
}
