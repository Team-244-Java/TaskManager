package edu.cs244.taskpulse.loader;


import edu.cs244.taskpulse.controller.DashboardController;
import edu.cs244.taskpulse.controller.TaskCreationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreateTaskLoader {

	private DashboardController dashboardController;

	public CreateTaskLoader(DashboardController dashboardController) {
		this.dashboardController = dashboardController;
	}

	public void newWindow() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TaskCreation.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			TaskCreationController createTaskController = fxmlLoader.getController();
			createTaskController.setDashboardController(dashboardController);
			Stage stage = new Stage();
			stage.setTitle("Task Creation");
			stage.setScene(new Scene(root1));
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
