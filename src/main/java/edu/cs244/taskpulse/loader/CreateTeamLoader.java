package edu.cs244.taskpulse.loader;

import edu.cs244.taskpulse.controller.CreateTeamController;
import edu.cs244.taskpulse.controller.DashboardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreateTeamLoader {

	private DashboardController dashboardController;

	public CreateTeamLoader(DashboardController dashboardController) {
		this.dashboardController = dashboardController;
	}
	public void newWindow() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CreateTeam.fxml"));
			Parent root = fxmlLoader.load();
			CreateTeamController createTeamController = fxmlLoader.getController();
			createTeamController.setDashboardController(dashboardController);
			Stage stage = new Stage();
			stage.setTitle("Create Team");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
