package edu.cs244.taskpulse.loader;


import edu.cs244.taskpulse.controller.DashboardController;
import edu.cs244.taskpulse.controller.TaskEditController;
import edu.cs244.taskpulse.models.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TaskEditLoader {

	private DashboardController dashboardController;
	
	private Task taskItem;
	
	public TaskEditLoader(DashboardController dashboardController,Task taskItem) {
		this.dashboardController = dashboardController;
		this.taskItem = taskItem;
	}

	
	public void newWindow() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TaskEdit.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			TaskEditController taskEditController = fxmlLoader.getController();
			taskEditController.setDashboardController(dashboardController);
			taskEditController.setTaskItem(taskItem);
			Stage stage = new Stage();
			stage.setTitle("Task Edit");
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
