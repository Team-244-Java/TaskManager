package edu.cs244.taskpulse.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class ProfileSettings {
	
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("profile_settings.fxml"));
			primaryStage.setTitle("Profile Settings");
			primaryStage.setScene(new Scene(root, 1220, 740));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
