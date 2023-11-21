package edu.cs244.taskpulse.gui;

import edu.cs244.taskpulse.loader.LoginLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		LoginLoader login = new LoginLoader();

		login.start(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
