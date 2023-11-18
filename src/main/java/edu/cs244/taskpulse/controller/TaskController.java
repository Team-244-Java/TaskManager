package edu.cs244.taskpulse.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import edu.cs244.taskpulse.models.Task;
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

	public void setData(Task taskItem) {
		this.taskItem = taskItem;
		this.taskTitle.setText(taskItem.getTitle());
		this.description.setText(taskItem.getDescription());
		this.dueDate.setText(taskItem.getDueDate());
		this.status.setText(taskItem.getStatus());

	}

	@FXML
	void onActionEdit() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TaskEdit.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			
			TaskEditController taskEditController = (TaskEditController)fxmlLoader.getController();
			
			taskEditController.setTaskItem(taskItem);
			
			stage.setTitle("Task Edit");
			stage.setScene(new Scene(root1));
			stage.show();
			// TaskEditController.setInputs("hello");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
