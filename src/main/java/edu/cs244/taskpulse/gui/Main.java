package edu.cs244.taskpulse.gui;

import edu.cs244.taskpulse.loader.DashboardLoader;
import edu.cs244.taskpulse.loader.LoginLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import edu.cs244.taskpulse.loader.ProfileSettingsLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		LoginLoader login = new LoginLoader();
		ProfileSettingsLoader profile = new ProfileSettingsLoader();
		//DashboardLoader dashboard = new DashboardLoader();

		login.start(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
