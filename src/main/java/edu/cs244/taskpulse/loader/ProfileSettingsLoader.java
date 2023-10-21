package edu.cs244.taskpulse.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class ProfileSettingsLoader {
	
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/ProfileSetting.fxml"));
			primaryStage.setTitle("Profile Settings");
			primaryStage.setScene(new Scene(root, 1220, 740));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
