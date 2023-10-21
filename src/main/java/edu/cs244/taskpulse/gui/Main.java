package edu.cs244.taskpulse.gui;

import edu.cs244.taskpulse.loader.LoginLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		//LoginScreen loginScreen = new LoginScreen();
		// Profile Settings Window GUI
		//ProfileSettingsLoader profileSettings = new ProfileSettingsLoader();
		
		//Registration registration = new Registration();
		
		LoginLoader login = new LoginLoader();
		login.start(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
