package edu.cs244.taskpulse.loader;

import edu.cs244.taskpulse.controller.CreateReminderController;
import edu.cs244.taskpulse.controller.DashboardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreateReminderLoader {

	private DashboardController dashboardController;

	public CreateReminderLoader(DashboardController dashboardController) {
		this.dashboardController = dashboardController;
	}

	public void newWindow() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CreateReminder.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			CreateReminderController createReminderController = fxmlLoader.getController();
			createReminderController.setDashboardController(dashboardController);
			Stage stage = new Stage();
			stage.setTitle("Create Reminder");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
