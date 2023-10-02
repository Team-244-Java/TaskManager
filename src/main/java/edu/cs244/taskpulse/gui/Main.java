package edu.cs244.taskpulse.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		LoginScreen loginScreen = new LoginScreen();
		loginScreen.start(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
